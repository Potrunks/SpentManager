package fr.potrunks.gestiondepensebackend.business.impl;

import fr.potrunks.gestiondepensebackend.business.SpentIBusiness;
import fr.potrunks.gestiondepensebackend.entity.*;
import fr.potrunks.gestiondepensebackend.model.Spent;
import fr.potrunks.gestiondepensebackend.repository.PeriodSpentIRepository;
import fr.potrunks.gestiondepensebackend.repository.SalaryIRepository;
import fr.potrunks.gestiondepensebackend.repository.SpentIRepository;
import fr.potrunks.gestiondepensebackend.repository.UserIRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
@Slf4j
public class SpentBusiness implements SpentIBusiness {

    @Autowired
    private SpentIRepository spentRepository;
    @Autowired
    private PeriodSpentIRepository periodSpentIRepository;
    @Autowired
    private SalaryIRepository salaryIRepository;
    @Autowired
    private UserIRepository userIRepository;

    public SpentBusiness(SpentIRepository spentRepository) {
        this.spentRepository = spentRepository;
    }

    @Override
    public List<Spent> getSpentsByPeriodSpentInProgress() {
        log.info("Start to search all spents by period spent in progress");
        PeriodSpentEntity periodSpentEntityInProgress = periodSpentIRepository.findByEndDatePeriodSpentIsNull();
        List<SpentEntity> spentEntities = spentRepository.findByPeriodSpentEntity(periodSpentEntityInProgress);
        List<Spent> spents = spentEntities
                .stream()
                .map(spent -> new Spent(
                        spent.getIdSpent(),
                        spent.getValueSpent(),
                        spent.getDateSpent(),
                        spent.getNameSpent(),
                        spent.getCommentSpent(),
                        null,
                        spent.getUserEntity().getFirstNameUser(),
                        null,
                        spent.getSpentCategoryEntity().getNameSpentCategory()))
                .collect(Collectors.toList());
        return spents;
    }

    @Override
    public Boolean deleteSpent(Long id) {
        SpentEntity spentEntity = spentRepository.findById(id).get();
        spentRepository.delete(spentEntity);
        return true;
    }

    @Override
    public Spent getSpent(Long id) {
        SpentEntity spentEntity = spentRepository.findById(id).get();
        Spent spent = new Spent();
        BeanUtils.copyProperties(spentEntity, spent);
        return spent;
    }

    @Override
    public Spent updateSpent(Long id, Spent spent) {
        SpentEntity spentEntity = spentRepository.findById(id).get();
        spentEntity.setDateSpent(spent.getDateSpent());
        spentEntity.setValueSpent(spent.getValueSpent());
        spentRepository.save(spentEntity);
        return spent;
    }

    @Override
    public SpentEntity create(UserEntity userConnected, SpentCategoryEntity spentCategorySelected, PeriodSpentEntity periodSpentInProgress, Spent spent) {
        if (!userInPeriodSpentIsPresent(userConnected, periodSpentInProgress)) {
            log.warn("The user id {} is not present in period spent id {}", userConnected.getIdUser(), periodSpentInProgress.getIdPeriodSpent());
            saveSalary(createSalaryZeroValue(userConnected, periodSpentInProgress));
            addUserToPeriodSpent(userConnected, periodSpentInProgress);
        }
        log.info("Set up new spent in progress...");
        SpentEntity spentEntity = new SpentEntity();
        spentEntity.setValueSpent(spent.getValueSpent());
        spentEntity.setDateSpent(LocalDate.now());
        spentEntity.setNameSpent(formatSpentName(spent));
        spentEntity.setCommentSpent(spent.getCommentSpent());
        spentEntity.setSpentCategoryEntity(spentCategorySelected);
        spentEntity.setUserEntity(userConnected);
        spentEntity.setPeriodSpentEntity(periodSpentInProgress);
        log.info("Add new spent in database");
        spentEntity = spentRepository.save(spentEntity);
        return spentEntity;
    }

    @Override
    public List<Spent> getSpentsByIdPeriodSpent(Long idPeriodSpent) {
        log.info("Start to search all spents by period spent id {}", idPeriodSpent);
        List<SpentEntity> spentEntities = spentRepository.findByPeriodSpentEntity(periodSpentIRepository.getById(idPeriodSpent));
        List<Spent> spents = spentEntities
                .stream()
                .map(spent -> new Spent(
                        spent.getIdSpent(),
                        spent.getValueSpent(),
                        spent.getDateSpent(),
                        spent.getNameSpent(),
                        spent.getCommentSpent(),
                        null,
                        spent.getUserEntity().getFirstNameUser(),
                        null,
                        spent.getSpentCategoryEntity().getNameSpentCategory()))
                .collect(Collectors.toList());
        return spents;
    }

    /**
     * Verify if user is in the period of spent
     * @param userToTest User to verify
     * @param periodSpentConcerned Period spent concerned
     * @return Return a Boolean true if the user is in the period spent
     */
    private Boolean userInPeriodSpentIsPresent(UserEntity userToTest, PeriodSpentEntity periodSpentConcerned) {
        log.info("Verify if user id {} is present in the period spent id {}", userToTest.getIdUser(), periodSpentConcerned.getIdPeriodSpent());
        List<PeriodSpentEntity> periodSpentEntityList = new ArrayList<>();
        periodSpentEntityList.add(periodSpentConcerned);
        List<UserEntity> userEntityList = userIRepository.findByPeriodSpentEntityList(periodSpentConcerned);
        return userIsInListOfUsers(userEntityList, userToTest);
    }

    /**
     * Verify if the user is in the list of user
     * @param userEntityList List of user
     * @param userToTest User to test
     * @return Return a Boolean true if the user is in the list
     */
    public Boolean userIsInListOfUsers(List<UserEntity> userEntityList, UserEntity userToTest) {
        if (userToTest == null) {
            return false;
        }
        log.info("Start to verify if the user id {} is in the list of user", userToTest.getIdUser());
        if (userEntityList != null) {
            for (UserEntity userEntity: userEntityList
            ) {
                if (userEntity.getIdUser() == userToTest.getIdUser()) {
                    log.info("User id {} is in the list", userToTest.getIdUser());
                    return true;
                }
            }
        }
        log.warn("User id {} is not in the list", userToTest.getIdUser());
        return false;
    }

    /**
     * Create new salary with zero for value salary
     * @param userEntity User associate to the new salary
     * @param periodSpentEntity Period spent concerned
     * @return Return a Salary Entity with user, period spent and zero value salary
     */
    private SalaryEntity createSalaryZeroValue(UserEntity userEntity, PeriodSpentEntity periodSpentEntity) {
        log.info("Start to set the new salary with value is zero");
        SalaryEntity salaryEntity = new SalaryEntity();
        salaryEntity.setValueSalary(0f);
        salaryEntity.setDateSalary(LocalDate.now());
        salaryEntity.setUserEntity(userEntity);
        salaryEntity.setPeriodSpentEntity(periodSpentEntity);
        return salaryEntity;
    }

    /**
     * Save new salary
     * @param salaryEntity Salary to save
     */
    private void saveSalary(SalaryEntity salaryEntity) {
        log.info("Saving new salary in database");
        salaryIRepository.save(salaryEntity);
    }

    /**
     * Add a user to a period spent already created
     * @param userEntity User to add
     * @param periodSpentEntity Period spent concerned
     */
    private void addUserToPeriodSpent(UserEntity userEntity, PeriodSpentEntity periodSpentEntity) {
        log.info("Add user id {} to the period spent id {}", userEntity.getIdUser(), periodSpentEntity.getIdPeriodSpent());
        List<UserEntity> userEntityList = periodSpentEntity.getUserEntityList();
        userEntityList.add(userEntity);
        periodSpentEntity.setUserEntityList(userEntityList);
        periodSpentIRepository.save(periodSpentEntity);
    }

    /**
     * Format the first char in Uppercase of the spent name
     * @param spentToFormatTheName
     * @return Return a String with the first char is Uppercase
     */
    public String formatSpentName(Spent spentToFormatTheName) {
        String nameSpentFormatted = "No name";
        if (spentToFormatTheName == null || spentToFormatTheName.getNameSpent() == null) {
            return nameSpentFormatted;
        }
        nameSpentFormatted = spentToFormatTheName.getNameSpent().substring(0, 1).toUpperCase() + spentToFormatTheName.getNameSpent().substring(1);
        return nameSpentFormatted;
    }
}
