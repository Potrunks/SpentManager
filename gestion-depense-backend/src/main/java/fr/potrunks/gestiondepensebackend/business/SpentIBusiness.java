package fr.potrunks.gestiondepensebackend.business;

import fr.potrunks.gestiondepensebackend.entity.PeriodSpentEntity;
import fr.potrunks.gestiondepensebackend.entity.SpentCategoryEntity;
import fr.potrunks.gestiondepensebackend.entity.SpentEntity;
import fr.potrunks.gestiondepensebackend.entity.UserEntity;
import fr.potrunks.gestiondepensebackend.model.Spent;

import java.util.List;

public interface SpentIBusiness {
    Spent addSpent(Spent spent);

    List<Spent> getSpentsByPeriodSpentInProgress();

    Boolean deleteSpent(Long id);

    Spent getSpent(Long id);

    Spent updateSpent(Long id, Spent spent);

    SpentEntity create(UserEntity userConnected, SpentCategoryEntity spentCategorySelected, PeriodSpentEntity periodSpentInProgress, Spent spent);
}
