package fr.potrunks.gestiondepensebackend.business;

import java.util.Map;

public interface PeriodSpentIBusiness {

    Map<String, Object> addNewPeriodSpent(Long idUserAddingPeriodSpent, Map<String, Object> responseForUI);

    Map<String, Object> closePeriodSpentInProgress(Map<String, Object> response);
}
