package fr.potrunks.gestiondepensebackend.repository;

import fr.potrunks.gestiondepensebackend.entity.PeriodSpentEntity;
import fr.potrunks.gestiondepensebackend.entity.SpentCategoryEntity;
import fr.potrunks.gestiondepensebackend.entity.SpentEntity;
import fr.potrunks.gestiondepensebackend.entity.UserEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface SpentIRepository extends JpaRepository<SpentEntity, Long> {

    /**
     * Find a list of spent of a user during a period spent
     * @param periodSpentEntity Period concerned
     * @param userEntity User concerned
     * @return List of spent of a user during a period spent
     */
    List<SpentEntity> findByPeriodSpentEntityAndUserEntity(PeriodSpentEntity periodSpentEntity, UserEntity userEntity);

    /**
     * Find a list of spent of a user during a period of spent and match with a category of spent
     * @param userEntity User concerned
     * @param periodSpentEntity Period concerned
     * @param spentCategoryEntity Category concerned
     * @return List of spent of a user during a period of spent and match with a category of spent
     */
    List<SpentEntity> findByUserEntityAndPeriodSpentEntityAndSpentCategoryEntity(UserEntity userEntity, PeriodSpentEntity periodSpentEntity, SpentCategoryEntity spentCategoryEntity);

    /**
     * Find a list of spent during a period of spent BUT no match with the category given
     * @param periodSpentEntity Period concerned
     * @param spentCategoryEntity Category NONE wanted
     * @return a list of spent during a period of spent BUT no match with the category given
     */
    List<SpentEntity> findByPeriodSpentEntityAndSpentCategoryEntityNot(PeriodSpentEntity periodSpentEntity, SpentCategoryEntity spentCategoryEntity);

    /**
     * Find a list of spent during a period of spent and match with the category given
     * @param periodSpentEntity Period concerned
     * @param spentCategoryEntity Category concerned
     * @return List of spent during a period of spent and match with the category given
     */
    List<SpentEntity> findByPeriodSpentEntityAndSpentCategoryEntity(PeriodSpentEntity periodSpentEntity, SpentCategoryEntity spentCategoryEntity);

    /**
     * Find list of spent during a period of spent
     * @param periodSpentEntity Period concerned
     * @return List of spent during a period of spent
     */
    List<SpentEntity> findByPeriodSpentEntity(PeriodSpentEntity periodSpentEntity);
}
