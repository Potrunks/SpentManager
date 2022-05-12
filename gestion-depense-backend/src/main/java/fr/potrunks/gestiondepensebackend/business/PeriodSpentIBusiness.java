package fr.potrunks.gestiondepensebackend.business;

import fr.potrunks.gestiondepensebackend.entity.PeriodSpentEntity;
import fr.potrunks.gestiondepensebackend.model.PeriodSpent;

import java.util.Map;

public interface PeriodSpentIBusiness {

    Map<String, Object> addNewPeriodSpent(Long idUserAddingPeriodSpent, Map<String, Object> responseForUI);

    Map<String, Object> closePeriodSpentInProgress(Map<String, Object> response);

    PeriodSpentEntity findInProgress();

    PeriodSpent getPeriodSpentInProgress();
}
