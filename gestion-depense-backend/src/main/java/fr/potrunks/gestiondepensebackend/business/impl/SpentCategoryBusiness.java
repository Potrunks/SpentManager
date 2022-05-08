package fr.potrunks.gestiondepensebackend.business.impl;

import fr.potrunks.gestiondepensebackend.business.SpentCategoryIBusiness;
import fr.potrunks.gestiondepensebackend.entity.SpentCategoryEntity;
import fr.potrunks.gestiondepensebackend.repository.SpentCategoryIRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
@Slf4j
public class SpentCategoryBusiness implements SpentCategoryIBusiness {

    @Autowired
    private SpentCategoryIRepository spentCategoryIRepository;

    @Override
    public SpentCategoryEntity findById(Long idSpentCategorySelected) {
        log.info("Searching spent category id {}", idSpentCategorySelected);
        SpentCategoryEntity spentCategoryEntity = spentCategoryIRepository.getById(idSpentCategorySelected);
        return spentCategoryEntity;
    }
}
