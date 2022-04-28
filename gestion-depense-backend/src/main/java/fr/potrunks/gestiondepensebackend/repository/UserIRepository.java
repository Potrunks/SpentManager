package fr.potrunks.gestiondepensebackend.repository;

import fr.potrunks.gestiondepensebackend.entity.UserEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserIRepository extends JpaRepository<UserEntity, Long> {

    UserEntity findByAdministratorTrue();
    UserEntity findByMailUser(String mailUser);
}