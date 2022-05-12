package fr.potrunks.gestiondepensebackend.repository;

import fr.potrunks.gestiondepensebackend.entity.UserEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface UserIRepository extends JpaRepository<UserEntity, Long> {

    UserEntity findByAdministratorTrue();

    UserEntity findByMailUser(String mailUser);

    @Query(value = "select u.* " +
            "from user u " +
            "inner join association_user_periodspent aup on u.id_user = aup.id_user " +
            "inner join period_spent ps on aup.id_period_spent = ps.id_period_spent where ps.id_period_spent = :idPeriodSpent", nativeQuery = true)
    List<UserEntity> findByIdPeriodSpentInProgress(@Param("idPeriodSpent") Long idPeriodSpent);
}