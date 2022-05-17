package fr.potrunks.gestiondepensebackend.business;

import fr.potrunks.gestiondepensebackend.entity.PeriodSpentEntity;
import fr.potrunks.gestiondepensebackend.entity.SpentCategoryEntity;
import fr.potrunks.gestiondepensebackend.entity.SpentEntity;
import fr.potrunks.gestiondepensebackend.entity.UserEntity;
import fr.potrunks.gestiondepensebackend.model.Spent;

import java.util.List;

public interface SpentIBusiness {

    /**
     * Get all spent during the period spent in progress
     * @return Return a List of Spent model
     */
    List<Spent> getSpentsByPeriodSpentInProgress();

    /**
     * Delete Spent by ID in the database
     * @param idSpent ID of the spent who want delete
     * @return Return a Boolean with the result of the deletion
     */
    Boolean deleteSpent(Long idSpent);

    /**
     * Get spent by ID in database
     * @param idSpent ID of the spent wanted
     * @return Return a Spent model
     */
    Spent getSpent(Long idSpent);

    // A sup
    Spent updateSpent(Long id, Spent spent);
    // A sup

    /**
     * Set a new spent and add it to the database
     * @param userConnected User who wants to add the spent
     * @param spentCategorySelected Category of the spent
     * @param periodSpentInProgress Period spent in progress to attach the spent
     * @param spent Spent with value, name and comment (this last is optional)
     * @return Return a Spent Entity after added in database
     */
    SpentEntity create(UserEntity userConnected, SpentCategoryEntity spentCategorySelected, PeriodSpentEntity periodSpentInProgress, Spent spent);

    /**
     * Get all spents in period spent id wanted
     * @param idPeriodSpent ID of the period spent wanted
     * @return Return a list of spent model in the period spent id wanted
     */
    List<Spent> getSpentsByIdPeriodSpent(Long idPeriodSpent);
}
