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

    List<SpentEntity> findByPeriodSpentEntityAndUserEntity(PeriodSpentEntity periodSpentEntity, UserEntity userEntity);

    List<SpentEntity> findByUserEntityAndPeriodSpentEntityAndSpentCategoryEntity(UserEntity userEntity, PeriodSpentEntity periodSpentEntity, SpentCategoryEntity spentCategoryEntity);

    List<SpentEntity> findByPeriodSpentEntityAndSpentCategoryEntityNot(PeriodSpentEntity periodSpentEntity, SpentCategoryEntity spentCategoryEntity);

    List<SpentEntity> findByPeriodSpentEntity(PeriodSpentEntity periodSpentEntityInProgress);
}
