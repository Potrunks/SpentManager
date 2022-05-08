package fr.potrunks.gestiondepensebackend.business;

import fr.potrunks.gestiondepensebackend.entity.SpentCategoryEntity;

public interface SpentCategoryIBusiness {
    SpentCategoryEntity findById(Long idSpentCategorySelected);
}
