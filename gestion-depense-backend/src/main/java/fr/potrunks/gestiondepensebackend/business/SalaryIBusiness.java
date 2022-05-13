package fr.potrunks.gestiondepensebackend.business;

import java.util.Map;

public interface SalaryIBusiness {

    /**
     * Add a new salary in the database for the user and for a period spent
     * @param idUserConnected ID of the user who add a new salary
     * @param idPeriodSpentCreated ID of the period spent who concern by the new salary
     * @param salaryInput Salary input by the user from the UI
     * @param response Map of String (key) and Object (value) who contain information during all the previous step before this method
     * @return Map of String (key) and Object (value) who contain information during all the step of this method
     */
    Map<String, Object> addNewSalary(Long idUserConnected, Long idPeriodSpentCreated, Float salaryInput, Map<String, Object> response);
}
