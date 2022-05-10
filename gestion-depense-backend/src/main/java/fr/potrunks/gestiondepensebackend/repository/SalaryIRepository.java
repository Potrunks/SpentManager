package fr.potrunks.gestiondepensebackend.repository;

import fr.potrunks.gestiondepensebackend.entity.PeriodSpentEntity;
import fr.potrunks.gestiondepensebackend.entity.SalaryEntity;
import fr.potrunks.gestiondepensebackend.entity.UserEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface SalaryIRepository extends JpaRepository<SalaryEntity, Long> {

    Integer countByPeriodSpentEntity(PeriodSpentEntity periodSpentEntity);

    SalaryEntity findByPeriodSpentEntityAndUserEntity(PeriodSpentEntity periodSpentEntity, UserEntity userEntity);
}
