package fr.potrunks.gestiondepensebackend.repository;

import fr.potrunks.gestiondepensebackend.entity.PeriodSpentEntity;
import fr.potrunks.gestiondepensebackend.entity.UserEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;

@Repository
public interface PeriodSpentIRepository extends JpaRepository<PeriodSpentEntity, Long> {

    /**
     * Find in database a period spent entity with an end date equals to null
     * @return PeriodSpentEntity with value end date set to null
     */
    PeriodSpentEntity findByEndDatePeriodSpentIsNull();

    /**
     * Find in database the period spent create at the date just after the date given in parameter
     * @param startDatePeriodSpent Creation date of the period spent that we want to find the period spent create after
     * @return PeriodSpentEntity with a start date just after the date given in parameter
     */
    PeriodSpentEntity findFirstByStartDatePeriodSpentAfter(LocalDate startDatePeriodSpent);

    /**
     * Find in database the period spent create at the date just before the date given in parameter
     * @param startDatePeriodSpent Creation date of the period spent that we want to find the period spent create before
     * @return PeriodSpentEntity with a start date just before the date given in parameter
     */
    PeriodSpentEntity findFirstByStartDatePeriodSpentBefore(LocalDate startDatePeriodSpent);
}
