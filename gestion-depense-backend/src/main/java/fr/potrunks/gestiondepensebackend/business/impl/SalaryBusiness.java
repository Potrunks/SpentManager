package fr.potrunks.gestiondepensebackend.business.impl;

import fr.potrunks.gestiondepensebackend.business.SalaryIBusiness;
import fr.potrunks.gestiondepensebackend.entity.PeriodSpentEntity;
import fr.potrunks.gestiondepensebackend.entity.SalaryEntity;
import fr.potrunks.gestiondepensebackend.entity.UserEntity;
import fr.potrunks.gestiondepensebackend.repository.PeriodSpentIRepository;
import fr.potrunks.gestiondepensebackend.repository.SalaryIRepository;
import fr.potrunks.gestiondepensebackend.repository.UserIRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.Map;

@Slf4j
@Service
public class SalaryBusiness implements SalaryIBusiness {

    @Autowired
    private PeriodSpentIRepository periodSpentRepository;
    @Autowired
    private UserIRepository userRepository;
    @Autowired
    private SalaryIRepository salaryRepository;

    @Override
    public Map<String, Object> addNewSalary(Long idUserConnected, Long idPeriodSpentCreated, Float salaryInput, Map<String, Object> response) {
        log.info("Start to add a new salary for id user : {}", idUserConnected);
        Boolean newSalaryCreated = false;
        log.info("Try to get USer with id : {}", idUserConnected);
        UserEntity userEntity = userRepository.getById(idUserConnected);
        if (userEntity.getIdUser() != null) {
            log.info("User found successfully");
            log.info("Try to get Period Spent with id : {}", idPeriodSpentCreated);
            PeriodSpentEntity periodSpentEntity = periodSpentRepository.getById(idPeriodSpentCreated);
            if (periodSpentEntity.getIdPeriodSpent() != null) {
                log.info("Period Spent found successfully");
                SalaryEntity salaryEntity = setNewSalary(userEntity, periodSpentEntity, salaryInput);
                log.info("Start to add the new salary in the database");
                salaryEntity = salaryRepository.save(salaryEntity);
                if (salaryEntity.getIdSalary() != null) {
                    log.info("The new salary id {} is added in database successfully", salaryEntity.getIdSalary());
                    newSalaryCreated = true;
                    response.put("newSalaryCreated", newSalaryCreated);
                } else {
                    log.warn("Error during addition of the new salary in the database");
                }
            } else {
                log.warn("Error during the search of Period Spent with id : {}", idPeriodSpentCreated);
            }
        } else {
            log.warn("Error during the search of User with id : {}", idUserConnected);
        }
        log.info("End to addition new salary");
        response.put("newSalaryCreated", newSalaryCreated);
        return response;
    }

    /**
     * Set the new salary with all information in parameters
     * @param userEntity User concerning by the salary
     * @param periodSpentEntity Period spent concerning by the salary
     * @param salaryInput Salary value input by the user
     * @return Return a Salary Entity ready to be added at the database
     */
    public SalaryEntity setNewSalary(UserEntity userEntity, PeriodSpentEntity periodSpentEntity, Float salaryInput) {
        log.info("Start to set a new salary");
        SalaryEntity salaryEntity = new SalaryEntity();
        salaryEntity.setValueSalary(salaryInput);
        salaryEntity.setUserEntity(userEntity);
        salaryEntity.setPeriodSpentEntity(periodSpentEntity);
        salaryEntity.setDateSalary(LocalDate.now());
        log.info("The new salary is set");
        return salaryEntity;
    }
}
