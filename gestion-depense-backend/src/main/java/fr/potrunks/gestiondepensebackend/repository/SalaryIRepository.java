package fr.potrunks.gestiondepensebackend.repository;

import fr.potrunks.gestiondepensebackend.entity.PeriodSpentEntity;
import fr.potrunks.gestiondepensebackend.entity.SalaryEntity;
import fr.potrunks.gestiondepensebackend.entity.UserEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface SalaryIRepository extends JpaRepository<SalaryEntity, Long> {

    /**
     * Count the number of salary in a period spent
     * @param periodSpentEntity Period of spent that we want to know the number of salary
     * @return Integer value of the number of salary in a period spent
     */
    Integer countByPeriodSpentEntity(PeriodSpentEntity periodSpentEntity);

    /**
     * Find the salary of a user during a period spent
     * @param periodSpentEntity Period of spent that we want to find the salary
     * @param userEntity User that we want to find the salary
     * @return SalaryEntity of the user during a period spent
     */
    SalaryEntity findByPeriodSpentEntityAndUserEntity(PeriodSpentEntity periodSpentEntity, UserEntity userEntity);

    /**
     * Find a list of salary during a period spent
     * @param periodSpentEntity Period of spent that we want to find all the salary
     * @return List<SalaryEntity> during the period spent
     */
    List<SalaryEntity> findByPeriodSpentEntity(PeriodSpentEntity periodSpentEntity);
}
