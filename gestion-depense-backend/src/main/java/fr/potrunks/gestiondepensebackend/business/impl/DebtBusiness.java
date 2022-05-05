package fr.potrunks.gestiondepensebackend.business.impl;

import fr.potrunks.gestiondepensebackend.business.DebtIBusiness;
import fr.potrunks.gestiondepensebackend.entity.DebtEntity;
import fr.potrunks.gestiondepensebackend.entity.PeriodSpentEntity;
import fr.potrunks.gestiondepensebackend.entity.UserEntity;
import fr.potrunks.gestiondepensebackend.repository.PeriodSpentIRepository;
import fr.potrunks.gestiondepensebackend.repository.UserIRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Map;

@Slf4j
@Service
public class DebtBusiness implements DebtIBusiness {

    @Autowired
    private UserIRepository userRepository;
    @Autowired
    private PeriodSpentIRepository periodSpentRepository;

    @Override
    public Map<String, Object> addNewDebt(Long idUserConnected, Long idPeriodSpentCreated, Map<String, Object> response) {
        log.info("Start to add a new debt for user id {} and Period Spent id {}", idUserConnected, idPeriodSpentCreated);
        Boolean newDebtCreated = false;
        log.info("Start to get the user id : {}", idUserConnected);
        UserEntity userEntity = userRepository.getById(idUserConnected);
        if (userEntity != null) {
            log.info("User id {} found successfully", idUserConnected);
            log.info("Start to get period spent id {}", idPeriodSpentCreated);
            PeriodSpentEntity periodSpentEntity = periodSpentRepository.getById(idPeriodSpentCreated);
            if (periodSpentEntity != null) {
                log.info("Period Spent id {} found successfully", idPeriodSpentCreated);
                // set debt
                DebtEntity debtEntity = setNewDebt(userEntity, periodSpentEntity);
                // add debt to the DB
            } else {
                log.warn("Error during the search of period spent id {}", idPeriodSpentCreated);
            }
        } else {
            log.warn("Error during the search of user id {}", idUserConnected);
        }
        response.put("newDebtCreated", newDebtCreated);
        log.info("End to addition new debt");
        return response;
    }

    public DebtEntity setNewDebt(UserEntity userEntity, PeriodSpentEntity periodSpentEntity) {
        log.info("Start to set new debt");
        DebtEntity debtEntity = new DebtEntity();
        log.info("Set new debt finish");
        return debtEntity;
    }
}
