package fr.potrunks.gestiondepensebackend.repository;

import fr.potrunks.gestiondepensebackend.entity.SpentCategoryEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface SpentCategoryIRepository extends JpaRepository<SpentCategoryEntity, Long> {
    /**
     * Find a spent category by a name category
     * @param nameSpentCategory The category name that we want to find the category
     * @return SpentCategoryEntity with the name category wanted
     */
    SpentCategoryEntity findByNameSpentCategory(String nameSpentCategory);
}
