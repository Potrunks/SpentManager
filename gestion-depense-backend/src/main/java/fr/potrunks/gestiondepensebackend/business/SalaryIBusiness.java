package fr.potrunks.gestiondepensebackend.business;

import java.util.Map;

public interface SalaryIBusiness {

    Map<String, Object> addNewSalary(Long idUserConnected, Long idPeriodSpentCreated, Float salaryInput, Map<String, Object> response);
}
