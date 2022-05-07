package fr.potrunks.gestiondepensebackend.repository;

import fr.potrunks.gestiondepensebackend.entity.PeriodSpentEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PeriodSpentIRepository extends JpaRepository<PeriodSpentEntity, Long> {

    PeriodSpentEntity findByEndDatePeriodSpentIsNull();
}
