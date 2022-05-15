package fr.potrunks.gestiondepensebackend.business;

import fr.potrunks.gestiondepensebackend.entity.UserEntity;
import fr.potrunks.gestiondepensebackend.model.User;

import java.util.List;

public interface UserIBusiness {

    /**
     * Find a User by an ID
     * @param idUserConnected ID of the User Entity wanted
     * @return Return a User Entity with the ID wanted
     */
    UserEntity findById(Long idUserConnected);

    /**
     * Find all the Users in the period spent in progress
     * @return Return a list of User model in the period spent in progress if the period spent in progress exist
     */
    List<User> getAllByPeriodSpentInProgress();

    /**
     * Get all users by id perdiod spent
     * @param idPeriodSpent ID period spent wanted
     * @return Return a list of user model in the period spent id wanted
     */
    List<User> getAllByIdPeriodSpent(Long idPeriodSpent);
}
