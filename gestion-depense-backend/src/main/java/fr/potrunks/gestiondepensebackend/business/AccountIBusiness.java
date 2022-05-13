package fr.potrunks.gestiondepensebackend.business;

import fr.potrunks.gestiondepensebackend.entity.UserEntity;
import fr.potrunks.gestiondepensebackend.model.User;

import java.util.Map;

public interface AccountIBusiness {

    /**
     * Add a new account in database
     * @param user User who create the account
     * @param response Response to send at the UI
     * @return Map of String (Key) and Boolean (Value) who contain all information of each account creation step
     */
    Map<String, Boolean> addNewAccount(User user, Map<String, Boolean> response);

    /**
     * Authenticate the user who want to connect at the app
     * @param user User who want to connect
     * @param response Response to send at the UI
     * @return Map of String (Key) and Object (Value) who contain all information of each authentication step
     */
    Map<String, Object> authentication(User user, Map<String, Object> response);

    /**
     * Find a user by mail
     * @param user User from the UI who contain the mail wanted
     * @return A user with the mail wanted
     */
    UserEntity getUserByMailUser(User user);
}
