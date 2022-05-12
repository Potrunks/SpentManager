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

    private Float calculateRateSpent(Float salary, Float sumSpents) {
        log.info("Calculating the rate of spent for this period spent...");
        Float rateSpent = sumSpents * 100 / salary;
        return rateSpent;
    }

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

    private Float calculateHouseholdShare(Float sumSalaryHousehold, Float salaryToEstimate) {
        log.info("Calculating the household share...");
        Float householdShare = salaryToEstimate * 100 / sumSalaryHousehold;
        return householdShare;
    }

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

    private Float calculateShareSpent(Float sumSpentsDuringPeriodSpent, Float householdShare) {
        log.info("Calculating share spent...");
        Float shareSpent = householdShare * sumSpentsDuringPeriodSpent / 100;
        return shareSpent;
    }

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

    private Float sumDepositsDuringPeriodSpent(PeriodSpentEntity periodSpentEntity) {
        log.info("Calculate all deposits during a period spent (all users)");
        SpentCategoryEntity spentCategoryEntity = spentCategoryIRepository.findByNameSpentCategory("Deposit");
        List<SpentEntity> spentEntityList = spentIRepository.findByPeriodSpentEntityAndSpentCategoryEntity(periodSpentEntity, spentCategoryEntity);
        Float sumDeposits = 0f;
        if (spentEntityList == null) {
            return sumDeposits;
        }
        for (SpentEntity spentEntity: spentEntityList
             ) {
            sumDeposits += spentEntity.getValueSpent();
        }
        return sumDeposits;
    }

    private Float calculateDebt(Float shareSpent, Float spentAlreadyPaid, Float depositDone, Float allDeposits) {
        log.info("Calculating debt...");
        Float debt = (shareSpent - (spentAlreadyPaid - depositDone)) - depositDone + (allDeposits - depositDone);
        return debt;
    }
}
