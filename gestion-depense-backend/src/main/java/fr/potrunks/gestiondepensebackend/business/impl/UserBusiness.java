package fr.potrunks.gestiondepensebackend.business.impl;

import fr.potrunks.gestiondepensebackend.business.UserIBusiness;
import fr.potrunks.gestiondepensebackend.entity.*;
import fr.potrunks.gestiondepensebackend.model.User;
import fr.potrunks.gestiondepensebackend.repository.*;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
@Slf4j
public class UserBusiness implements UserIBusiness {

    @Autowired
    private UserIRepository userIRepository;
    @Autowired
    private PeriodSpentIRepository periodSpentIRepository;
    @Autowired
    private SalaryIRepository salaryIRepository;
    @Autowired
    private SpentIRepository spentIRepository;
    @Autowired
    private SpentCategoryIRepository spentCategoryIRepository;

    @Override
    public UserEntity findById(Long idUserConnected) {
        log.info("Find user with id {}", idUserConnected);
        UserEntity userEntity = userIRepository.getById(idUserConnected);
        return userEntity;
    }

    @Override
    public List<User> getAllByPeriodSpentInProgress() {
        log.info("Start to get all users in period spent in progress");
        log.info("Start to find the period spent in progress");
        PeriodSpentEntity periodSpentInProgress = periodSpentIRepository.findByEndDatePeriodSpentIsNull();
        if (periodSpentInProgress == null) {
            log.warn("No period spent in progress in the database");
            return null;
        }
        log.info("Start to get all users in period spent in progress");
        List<UserEntity> userEntityList = userIRepository.findByIdPeriodSpentInProgress(periodSpentInProgress.getIdPeriodSpent());
        if (userEntityList == null) {
            log.warn("No user in period spent in progress");
            return null;
        }
        List<User> userList = copyUserEntityListToUserList(userEntityList, periodSpentInProgress);
        return userList;
    }

    @Override
    public List<User> getAllByIdPeriodSpent(Long idPeriodSpent) {
        log.info("Start to get all users in period spent id {}", idPeriodSpent);
        log.info("Start to find the period spent id {}", idPeriodSpent);
        PeriodSpentEntity periodSpentById = periodSpentIRepository.getById(idPeriodSpent);
        if (periodSpentById == null) {
            log.warn("No period spent id {} in the database", idPeriodSpent);
            return null;
        }
        log.info("Start to get all users in period spent id {}", idPeriodSpent);
        List<UserEntity> userEntityList = userIRepository.findByIdPeriodSpentInProgress(periodSpentById.getIdPeriodSpent());
        if (userEntityList == null) {
            log.warn("No user in period spent id {}", idPeriodSpent);
            return null;
        }
        List<User> userList = copyUserEntityListToUserList(userEntityList, periodSpentById);
        return userList;
    }

    /**
     * Copy the User Entity list to the User model list. During the copy, the salary attach to the period spent,
     * all the spents and the debt of the user for this period spent is add to the user model
     *
     * @param userEntityList    User Entity list source for the copy
     * @param periodSpentEntity Period Spent concerned
     * @return Return a list of User model with all informations
     */
    private List<User> copyUserEntityListToUserList(List<UserEntity> userEntityList, PeriodSpentEntity periodSpentEntity) {
        log.info("Start to copy all properties of userEntity object to user object");
        List<User> userList = new ArrayList<>();
        for (UserEntity userEntity : userEntityList
        ) {
            User user = new User();
            BeanUtils.copyProperties(userEntity, user);
            log.info("Search the salary of the user id {} during the period spent id {}", userEntity.getIdUser(), periodSpentEntity.getIdPeriodSpent());
            user.setValueSalary(salaryIRepository.findByPeriodSpentEntityAndUserEntity(periodSpentEntity, userEntity).getValueSalary());
            user.setValueSpents(sumValueSpentsByUserAndPeriodSpent(userEntity, periodSpentEntity));
            user.setRateSpent(calculateRateSpent(user.getValueSalary(), user.getValueSpents()));
            Float householdShare = calculateHouseholdShare(sumSalaryHousehold(periodSpentEntity), user.getValueSalary());
            Float shareSpent = calculateShareSpent(sumSpentsDuringPeriodSpent(periodSpentEntity), householdShare);
            user.setValueDebt(calculateDebt(shareSpent, user.getValueSpents(), calculateUserDepositDuringPeriodSpent(periodSpentEntity, userEntity), sumDepositsDuringPeriodSpent(periodSpentEntity)));
            userList.add(user);
        }
        return userList;
    }

    /**
     * Calculate the sum of all spents of a user during a period spent
     *
     * @param userEntity        User concerned
     * @param periodSpentEntity Period Spent concerned
     * @return Return a Float corresponding to the sum of all spents
     */
    private Float sumValueSpentsByUserAndPeriodSpent(UserEntity userEntity, PeriodSpentEntity periodSpentEntity) {
        log.info("Start to calculate the sum of all spent for user id {} and during period spent id {}", userEntity.getIdUser(), periodSpentEntity.getIdPeriodSpent());
        Float sum = 0f;
        List<SpentEntity> spentEntityList = spentIRepository.findByPeriodSpentEntityAndUserEntity(periodSpentEntity, userEntity);
        if (spentEntityList == null) {
            return sum;
        }
        for (SpentEntity spentEntity : spentEntityList
        ) {
            sum += spentEntity.getValueSpent();
        }
        return sum;
    }

    /**
     * Calculate the rate of spent compared to the salary
     *
     * @param salary    Salary value
     * @param sumSpents Sum of spents
     * @return Return a Float with the rate spent value
     */
    private Float calculateRateSpent(Float salary, Float sumSpents) {
        log.info("Calculating the rate of spent for this period spent...");
        return calculateRate(salary, sumSpents);
    }

    /**
     * Calculate the sum of all salaries in the household during a period spent
     *
     * @param periodSpentEntity Period Spent who contain all the salaries
     * @return Return a Float with the sum of all salaries during the period spent
     */
    private Float sumSalaryHousehold(PeriodSpentEntity periodSpentEntity) {
        log.info("Calculating all the salary in the household for the period spent id {}", periodSpentEntity.getIdPeriodSpent());
        List<SalaryEntity> salaryEntityList = salaryIRepository.findByPeriodSpentEntity(periodSpentEntity);
        Float sumSalaryHousehold = 0f;
        for (SalaryEntity salary : salaryEntityList
        ) {
            sumSalaryHousehold += salary.getValueSalary();
        }
        return sumSalaryHousehold;
    }

    /**
     * Calculate the part of the household concerning the user
     *
     * @param sumSalaryHousehold Sum of all salaries in the household
     * @param salaryToEstimate   Salary of the user include in the sum of all salaries
     * @return Return a Flot with the Household Share value of the user concerned
     */
    private Float calculateHouseholdShare(Float sumSalaryHousehold, Float salaryToEstimate) {
        log.info("Calculating the household share...");
        return calculateRate(sumSalaryHousehold, salaryToEstimate);
    }

    /**
     * Calculate a rate of a value compared to the maximum value
     *
     * @param maxValue
     * @param valueToEstimate
     * @return Return a Float with the rate value
     */
    public Float calculateRate(Float maxValue, Float valueToEstimate) {
        Float rate = 0f;
        if (maxValue == null || maxValue == 0f) {
            return rate;
        }
        if (valueToEstimate == null) {
            valueToEstimate = 0f;
        }
        rate = valueToEstimate * 100 / maxValue;
        return rate;
    }

    /**
     * Calculate the sum of all spents during a period spent
     *
     * @param periodSpentEntity
     * @return Float with the value of the sum of all spents
     */
    private Float sumSpentsDuringPeriodSpent(PeriodSpentEntity periodSpentEntity) {
        log.info("Calculating sum of all the spents during period spent id {}", periodSpentEntity.getIdPeriodSpent());
        SpentCategoryEntity spentCategoryEntity = spentCategoryIRepository.findByNameSpentCategory("Deposit");
        List<SpentEntity> spentEntityList = spentIRepository.findByPeriodSpentEntityAndSpentCategoryEntityNot(periodSpentEntity, spentCategoryEntity);
        Float sumSpentsDuringPeriodSpent = 0f;
        if (spentEntityList == null) {
            return sumSpentsDuringPeriodSpent;
        }
        for (SpentEntity spentEntity : spentEntityList
        ) {
            sumSpentsDuringPeriodSpent += spentEntity.getValueSpent();
        }
        return sumSpentsDuringPeriodSpent;
    }

    /**
     * Calculate the share spent for a user compared to own household share
     *
     * @param sumSpentsDuringPeriodSpent
     * @param householdShare
     * @return Return a float with the value of the spent share of a user with own household share
     */
    public Float calculateShareSpent(Float sumSpentsDuringPeriodSpent, Float householdShare) {
        log.info("Calculating share spent...");
        Float shareSpent = 0f;
        if (sumSpentsDuringPeriodSpent == null || householdShare == null) {
            return shareSpent;
        }
        shareSpent = householdShare * sumSpentsDuringPeriodSpent / 100;
        return shareSpent;
    }

    /**
     * Calculate sum of deposit of a user during a period spent
     *
     * @param periodSpentEntity
     * @param userEntity
     * @return Return a Float with the value of the sum of deposit of a user
     */
    private Float calculateUserDepositDuringPeriodSpent(PeriodSpentEntity periodSpentEntity, UserEntity userEntity) {
        log.info("Calculating deposit by user id {} during spent period id {}", userEntity.getIdUser(), periodSpentEntity.getIdPeriodSpent());
        SpentCategoryEntity spentCategoryEntity = spentCategoryIRepository.findByNameSpentCategory("Deposit");
        List<SpentEntity> spentEntityList = spentIRepository.findByUserEntityAndPeriodSpentEntityAndSpentCategoryEntity(userEntity, periodSpentEntity, spentCategoryEntity);
        Float sumDeposit = 0f;
        if (spentEntityList == null) {
            return sumDeposit;
        }
        for (SpentEntity spentEntity : spentEntityList
        ) {
            sumDeposit += spentEntity.getValueSpent();
        }
        return sumDeposit;
    }

    /**
     * Calculate the sum of all deposit during a period spent
     *
     * @param periodSpentEntity
     * @return Return a Float with the value of sum of all deposits
     */
    private Float sumDepositsDuringPeriodSpent(PeriodSpentEntity periodSpentEntity) {
        log.info("Calculate all deposits during a period spent (all users)");
        SpentCategoryEntity spentCategoryEntity = spentCategoryIRepository.findByNameSpentCategory("Deposit");
        List<SpentEntity> spentEntityList = spentIRepository.findByPeriodSpentEntityAndSpentCategoryEntity(periodSpentEntity, spentCategoryEntity);
        Float sumDeposits = 0f;
        if (spentEntityList == null) {
            return sumDeposits;
        }
        for (SpentEntity spentEntity : spentEntityList
        ) {
            sumDeposits += spentEntity.getValueSpent();
        }
        return sumDeposits;
    }

    /**
     * Calculate the debt
     *
     * @param shareSpent
     * @param spentAlreadyPaid
     * @param depositDone
     * @param allDeposits
     * @return Return a Float with debt value
     */
    public Float calculateDebt(Float shareSpent, Float spentAlreadyPaid, Float depositDone, Float allDeposits) {
        log.info("Calculating debt...");
        if(allDeposits == null) {
            allDeposits = 0f;
        }
        if (allDeposits == 0f) {
            depositDone = 0f;
        }
        if (depositDone == null) {
            depositDone = 0f;
        }
        if (spentAlreadyPaid == null) {
            spentAlreadyPaid = 0f;
        }
        if (shareSpent == null) {
            shareSpent = 0f;
        }
        Float debt = (shareSpent - (spentAlreadyPaid - depositDone)) - depositDone + (allDeposits - depositDone);
        return debt;
    }
}
