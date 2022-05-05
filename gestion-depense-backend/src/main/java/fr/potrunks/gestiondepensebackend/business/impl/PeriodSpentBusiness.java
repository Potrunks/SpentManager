package fr.potrunks.gestiondepensebackend.business.impl;

import fr.potrunks.gestiondepensebackend.business.PeriodSpentIBusiness;
import fr.potrunks.gestiondepensebackend.entity.PeriodSpentEntity;
import fr.potrunks.gestiondepensebackend.entity.UserEntity;
import fr.potrunks.gestiondepensebackend.repository.PeriodSpentIRepository;
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
        if (periodSpentEntity.getIdPeriodSpent() != null) {
            log.info("Period spent id {} in progress found", periodSpentEntity.getIdPeriodSpent());
            periodSpentEntity.setEndDatePeriodSpent(LocalDate.now());
            periodSpentRepository.save(periodSpentEntity);
            periodSpentInProgressClosed = true;
            log.info("Period spent in progress closed successfully");
        } else {
            log.warn("Period spent in progress not found in database");
        }
        response.put("periodSpentInProgressClosed", periodSpentInProgressClosed);
        return response;
    }
}
