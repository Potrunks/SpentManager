package fr.potrunks.gestiondepensebackend.business.impl;

import fr.potrunks.gestiondepensebackend.business.PeriodSpentIBusiness;
import fr.potrunks.gestiondepensebackend.entity.PeriodSpentEntity;
import fr.potrunks.gestiondepensebackend.entity.UserEntity;
import fr.potrunks.gestiondepensebackend.model.PeriodSpent;
import fr.potrunks.gestiondepensebackend.repository.PeriodSpentIRepository;
import fr.potrunks.gestiondepensebackend.repository.SalaryIRepository;
import fr.potrunks.gestiondepensebackend.repository.UserIRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@Service
@Slf4j
public class PeriodSpentBusiness implements PeriodSpentIBusiness {

    @Autowired
    private UserIRepository userRepository;
    @Autowired
    private PeriodSpentIRepository periodSpentRepository;
    @Autowired
    private SalaryIRepository salaryRepository;

    @Override
    public Map<String, Object> addNewPeriodSpent(Long idUserAddingPeriodSpent, Map<String, Object> response) {
        log.info("Start adding new spending period in database");
        Boolean periodSpentAdded = false;
        PeriodSpentEntity periodSpentEntity = new PeriodSpentEntity();
        log.info("Get user id : {}", idUserAddingPeriodSpent);
        UserEntity userEntity = userRepository.getById(idUserAddingPeriodSpent);
        if (userEntity.getIdUser() != null) {
            log.info("User found successfully");
            List<UserEntity> userEntityList = new ArrayList<>();
            userEntityList.add(userEntity);
            periodSpentEntity.setUserEntityList(userEntityList);
            periodSpentEntity.setStartDatePeriodSpent(LocalDate.now());
            log.info("Adding new spending period in database created by id user : {}", idUserAddingPeriodSpent);
            periodSpentEntity = periodSpentRepository.save(periodSpentEntity);
            if (periodSpentEntity.getIdPeriodSpent() != null) {
                periodSpentAdded = true;
                response.put("idPeriodSpentCreated", periodSpentEntity.getIdPeriodSpent());
                log.info("New spending period added successfully");
            } else {
                log.warn("Error during addition of new spending period");
            }
        } else {
            log.warn("The id user {} is not found in data base", idUserAddingPeriodSpent);
        }
        response.put("periodSpentAdded", periodSpentAdded);
        log.info("End of addition new spending period");
        return response;
    }

    @Override
    public Map<String, Object> closePeriodSpentInProgress(Map<String, Object> response) {
        log.info("Start to close the last period spent in progress");
        Boolean periodSpentInProgressClosed = false;
        log.info("Start to find the period spent in progress");
        PeriodSpentEntity periodSpentEntity = periodSpentRepository.findByEndDatePeriodSpentIsNull();
        if (periodSpentEntity != null) {
            log.info("Period spent id {} in progress found", periodSpentEntity.getIdPeriodSpent());
            response = verifyPeriodSpentInProgressIsClosable(periodSpentEntity, response);
            if ((Boolean) response.get(("periodSpentInProgressIsClosable")) == true) {
                log.info("Closing period spent in progress...");
                periodSpentEntity.setEndDatePeriodSpent(LocalDate.now());
                periodSpentRepository.save(periodSpentEntity);
                periodSpentInProgressClosed = true;
                log.info("Period spent in progress closed successfully");
            }
        } else {
            periodSpentInProgressClosed = true;
            log.warn("Period spent in progress not found in database");
        }
        response.put("periodSpentInProgressClosed", periodSpentInProgressClosed);
        return response;
    }

    @Override
    public PeriodSpentEntity findInProgress() {
        log.info("Searching period spent in progress");
        PeriodSpentEntity periodSpentEntity = periodSpentRepository.findByEndDatePeriodSpentIsNull();
        return periodSpentEntity;
    }

    @Override
    public PeriodSpent getPeriodSpentInProgress() {
        log.info("Start to get period spent in progress");
        PeriodSpentEntity periodSpentEntityInProgress = periodSpentRepository.findByEndDatePeriodSpentIsNull();
        return copyPeriodSpentEntityToPeriodSpent(periodSpentEntityInProgress);
    }

    @Override
    public PeriodSpent getPeriodSpentById(Long idPeriodSpent) {
        log.info("Start to get the period spent id {} in the data base", idPeriodSpent);
        PeriodSpentEntity periodSpentEntityGetById = periodSpentRepository.getById(idPeriodSpent);
        return copyPeriodSpentEntityToPeriodSpent(periodSpentEntityGetById);
    }

    /**
     * Copy properties of a Period Spent Entity to a Period Spent. Get the ID of the previous and before Period Spent Entity for the Period Spent
     * @param periodSpentEntityToCopy Period Spent source
     * @return Return a Period Spent copy from the Period Spent Entity in the parameter
     */
    public PeriodSpent copyPeriodSpentEntityToPeriodSpent(PeriodSpentEntity periodSpentEntityToCopy) {
        if (periodSpentEntityToCopy == null) {
            log.warn("No period spent found");
            return null;
        }
        PeriodSpent periodSpentClone = new PeriodSpent();
        BeanUtils.copyProperties(periodSpentEntityToCopy, periodSpentClone);
        periodSpentClone.setIdNextPeriodSpent(getIdNextPeriodSpent(periodSpentEntityToCopy));
        periodSpentClone.setIdPreviousPeriodSpent(getIdPreviousPeriodSpent(periodSpentEntityToCopy));
        return periodSpentClone;
    }

    /**
     * Get the ID of the period spent created before the period spent in parameter
     * @param periodSpentEntity The period spent reference
     * @return Return the ID of the previous period spent
     */
    private Long getIdPreviousPeriodSpent(PeriodSpentEntity periodSpentEntity) {
        log.info("Start to get the id of the previous period spent close to the period spent id {}", periodSpentEntity.getIdPeriodSpent());
        PeriodSpentEntity previousPeriodSpentEntity = periodSpentRepository.findFirstByStartDatePeriodSpentBefore(periodSpentEntity.getStartDatePeriodSpent());
        if (previousPeriodSpentEntity == null) {
            return null;
        }
        return previousPeriodSpentEntity.getIdPeriodSpent();
    }

    /**
     * Get the ID of the period spent created after the period spent in parameter
     * @param periodSpentEntity The period spent reference
     * @return Return the ID of the next period spent
     */
    private Long getIdNextPeriodSpent(PeriodSpentEntity periodSpentEntity) {
        log.info("Start to get the id of the next period spent close to the period spent id {}", periodSpentEntity.getIdPeriodSpent());
        PeriodSpentEntity nextPeriodSpentEntity = periodSpentRepository.findFirstByStartDatePeriodSpentAfter(periodSpentEntity.getStartDatePeriodSpent());
        if (nextPeriodSpentEntity == null) {
            return null;
        }
        return nextPeriodSpentEntity.getIdPeriodSpent();
    }

    /**
     * Verify if the period spent in progress is closable. For that, count the number of salary present in the period spent and if the number is greater than 1, the period spent is closable
     * @param periodSpentEntity Period spent who want to verify if is closable
     * @param response A map of String (key) and Object (value) for the UI
     * @return Return a Response Entity of Map of String (key) and Object (value) with each step of the verification
     */
    private Map<String, Object> verifyPeriodSpentInProgressIsClosable(PeriodSpentEntity periodSpentEntity, Map<String, Object> response) {
        log.info("Verifying if Period Spent id {} is closable", periodSpentEntity.getIdPeriodSpent());
        Boolean periodSpentInProgressIsClosable = false;
        if (salaryRepository.countByPeriodSpentEntity(periodSpentEntity) > 1) {
            log.info("Period Spent is closable");
            periodSpentInProgressIsClosable = true;
        } else {
            log.warn("Period Spent not closable");
        }
        response.put("periodSpentInProgressIsClosable", periodSpentInProgressIsClosable);
        return response;
    }
}
