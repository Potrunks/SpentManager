package fr.potrunks.gestiondepensebackend.repository;

import fr.potrunks.gestiondepensebackend.entity.PeriodSpentEntity;
import fr.potrunks.gestiondepensebackend.entity.UserEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface UserIRepository extends JpaRepository<UserEntity, Long> {

    /**
     * Find an administrator user
     * @return An administrator user
     */
    UserEntity findByAdministratorTrue();

    /**
     * Find a user by his mail
     * @param mailUser Mail concerned
     * @return A user with the mail wanted
     */
    UserEntity findByMailUser(String mailUser);

    /**
     * Find a list of user during the period spent in progress
     * PS : This method can be updated without using a native
     * query and without using the id of the period of spent
     * in progress but directly the period spent. This method
     * can be used for all period spent, not just in progress.
     * @param idPeriodSpent ID of the period spent concerned
     *                      (the method has been created just
     *                      for period spent in progress but it
     *                      can be used for any period spent)
     * @return A list of users during the period spent concerned
     */
    @Query(value = "select u.* " +
            "from user u " +
            "inner join association_user_periodspent aup on u.id_user = aup.id_user " +
            "inner join period_spent ps on aup.id_period_spent = ps.id_period_spent where ps.id_period_spent = :idPeriodSpent", nativeQuery = true)
    List<UserEntity> findByIdPeriodSpentInProgress(@Param("idPeriodSpent") Long idPeriodSpent);

    List<UserEntity> findByPeriodSpentEntityList(PeriodSpentEntity periodSpentEntityList);
}