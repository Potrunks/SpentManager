package fr.potrunks.gestiondepensebackend.business.impl;

import fr.potrunks.gestiondepensebackend.business.SpentCategoryIBusiness;
import fr.potrunks.gestiondepensebackend.entity.SpentCategoryEntity;
import fr.potrunks.gestiondepensebackend.model.SpentCategory;
import fr.potrunks.gestiondepensebackend.repository.SpentCategoryIRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@Slf4j
public class SpentCategoryBusiness implements SpentCategoryIBusiness {

    @Autowired
    private SpentCategoryIRepository spentCategoryIRepository;

    public SpentCategoryBusiness(SpentCategoryIRepository spentCategoryIRepository) {
        this.spentCategoryIRepository = spentCategoryIRepository;
    }

    @Override
    public SpentCategoryEntity findById(Long idSpentCategorySelected) {
        log.info("Searching spent category id {}", idSpentCategorySelected);
        SpentCategoryEntity spentCategoryEntity = spentCategoryIRepository.getById(idSpentCategorySelected);
        return spentCategoryEntity;
    }

    @Override
    public List<SpentCategory> getAll() {
        List<SpentCategoryEntity> spentCategoryEntities = spentCategoryIRepository.findAll(Sort.by("nameSpentCategory"));
        List<SpentCategory> spentCategories = spentCategoryEntities
                .stream()
                .map(spentCategory -> new SpentCategory(
                        spentCategory.getIdSpentCategory(),
                        spentCategory.getNameSpentCategory()))
                .collect(Collectors.toList());
        return spentCategories;
    }
}
