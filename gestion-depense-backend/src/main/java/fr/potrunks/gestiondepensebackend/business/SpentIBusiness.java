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

    // A sup
    Boolean deleteSpent(Long id);

    Spent getSpent(Long id);

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
}
