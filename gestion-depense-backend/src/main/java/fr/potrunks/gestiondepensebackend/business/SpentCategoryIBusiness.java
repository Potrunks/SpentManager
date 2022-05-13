package fr.potrunks.gestiondepensebackend.business;

import fr.potrunks.gestiondepensebackend.entity.SpentCategoryEntity;
import fr.potrunks.gestiondepensebackend.model.SpentCategory;

import java.util.List;

public interface SpentCategoryIBusiness {

    /**
     * Find a spent category with the ID
     * @param idSpentCategorySelected ID of the spent category wanted
     * @return Return a Spent Category Entity wanted
     */
    SpentCategoryEntity findById(Long idSpentCategorySelected);

    /**
     * Find all the spent category from the database
     * @return Return a List of Spent Category model
     */
    List<SpentCategory> getAll();
}
