package fr.potrunks.gestiondepensebackend.business.impl;

import fr.potrunks.gestiondepensebackend.business.PeriodSpentIBusiness;
import fr.potrunks.gestiondepensebackend.entity.PeriodSpentEntity;
import fr.potrunks.gestiondepensebackend.entity.UserEntity;
import fr.potrunks.gestiondepensebackend.repository.PeriodSpentIRepository;
import fr.potrunks.gestiondepensebackend.repository.SalaryIRepository;
import fr.potrunks.gestiondepensebackend.repository.UserIRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
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
            periodSpentEntity.setUserEntity(userEntity);
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
