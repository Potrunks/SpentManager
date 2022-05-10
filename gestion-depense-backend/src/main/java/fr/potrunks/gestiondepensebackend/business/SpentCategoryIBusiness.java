package fr.potrunks.gestiondepensebackend.business;

import fr.potrunks.gestiondepensebackend.entity.SpentCategoryEntity;
import fr.potrunks.gestiondepensebackend.model.SpentCategory;

import java.util.List;

public interface SpentCategoryIBusiness {
    SpentCategoryEntity findById(Long idSpentCategorySelected);

    List<SpentCategory> getAll();
}
