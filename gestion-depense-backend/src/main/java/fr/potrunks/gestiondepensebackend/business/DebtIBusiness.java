package fr.potrunks.gestiondepensebackend.business;

import java.util.Map;

public interface DebtIBusiness {
    Map<String, Object> addNewDebt(Long idUserConnected, Long idPeriodSpentCreated, Map<String, Object> response);
}
