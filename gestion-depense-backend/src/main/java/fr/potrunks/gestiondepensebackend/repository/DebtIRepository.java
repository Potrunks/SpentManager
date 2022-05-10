package fr.potrunks.gestiondepensebackend.repository;

import fr.potrunks.gestiondepensebackend.entity.DebtEntity;
import fr.potrunks.gestiondepensebackend.entity.PeriodSpentEntity;
import fr.potrunks.gestiondepensebackend.entity.UserEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface DebtIRepository extends JpaRepository<DebtEntity, Long> {
    DebtEntity findByPeriodSpentEntityAndUserEntity(PeriodSpentEntity periodSpentEntity, UserEntity userEntity);
}
