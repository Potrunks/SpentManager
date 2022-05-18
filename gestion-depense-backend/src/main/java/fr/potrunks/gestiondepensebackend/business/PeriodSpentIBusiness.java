package fr.potrunks.gestiondepensebackend.business;

import fr.potrunks.gestiondepensebackend.entity.PeriodSpentEntity;
import fr.potrunks.gestiondepensebackend.model.PeriodSpent;
import org.springframework.http.ResponseEntity;

import java.util.Map;

public interface PeriodSpentIBusiness {

    /**
     * Add in database a new period spent after find the user associate to the ID in parameter
     * @param idUserAddingPeriodSpent ID of the user who want to add a new period spent
     * @param responseForUI A map of String (key) and Object (value) for the UI
     * @return Return a Response Entity of Map of String (key) and Object (value) with each step of the creation of a new period spent
     */
    Map<String, Object> addNewPeriodSpent(Long idUserAddingPeriodSpent, Map<String, Object> responseForUI);

    /**
     * Close the period spent in progress after to verify if the period spent is closable
     * @param response A map of String (key) and Object (value) for the UI
     * @return Return a Response Entity of Map of String (key) and Object (value) with each step of the closing of a period spent
     */
    Map<String, Object> closePeriodSpentInProgress(Map<String, Object> response);

    /**
     * Find the period spent in progress in the database
     * @return Return a period spent in progress
     */
    PeriodSpentEntity findInProgress();

    /**
     * Find the period spent in progress and bring the IDs of the period spent created after and before the period spent in progress
     * @return Return a Period Spent model for the UI
     */
    PeriodSpent getPeriodSpentInProgress();

    /**
     * Get the period spent by ID in the database
     * @param idPeriodSpent ID Period spent to get in the database
     * @return Return a period spent with the ID wanted
     */
    PeriodSpent getPeriodSpentById(Long idPeriodSpent);

    /**
     * Check if period spent in progress in database exist
     * @return Return a boolean true if exist and false if is not exist
     */
    Boolean checkPeriodSpentInProgressExist();
}
