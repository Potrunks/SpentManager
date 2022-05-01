package fr.potrunks.gestiondepensebackend.business;

import fr.potrunks.gestiondepensebackend.entity.UserEntity;
import fr.potrunks.gestiondepensebackend.model.User;

import java.util.Map;

public interface AccountIBusiness {
    Boolean addNewAccount(User user);

    Map<String, Object> authentication(User user, Map<String, Object> response);

    UserEntity getUserByMailUser(User user);
}
