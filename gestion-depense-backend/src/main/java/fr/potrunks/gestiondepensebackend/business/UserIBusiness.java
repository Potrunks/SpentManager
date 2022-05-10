package fr.potrunks.gestiondepensebackend.business;

import fr.potrunks.gestiondepensebackend.entity.UserEntity;

public interface UserIBusiness {
    UserEntity findById(Long idUserConnected);
}
