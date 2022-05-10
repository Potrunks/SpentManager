package fr.potrunks.gestiondepensebackend.business;

import fr.potrunks.gestiondepensebackend.entity.UserEntity;
import fr.potrunks.gestiondepensebackend.model.User;

import java.util.List;

public interface UserIBusiness {
    UserEntity findById(Long idUserConnected);

    List<User> getAllByPeriodSpentInProgress();
}
