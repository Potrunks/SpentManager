package fr.potrunks.gestiondepensebackend.business;

import fr.potrunks.gestiondepensebackend.model.Salary;

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

    /**
     * Add new salary or update if value is zero
     * @param response Map of String (Key) and Object (Value)
     * @param idUserConnected ID of the user connected
     * @param salary Salary to add
     * @return Return a Map String (Key) and Object (Value) contain informations of each step of this method
     */
    Map<String, Object> addSalaryInPeriodSpentInProgress(Map<String, Object> response, Long idUserConnected, Salary salary);

    /**
     * Update a Salary
     * @param salary Salary to update
     * @return Return a boolean true if the salary is updated and false if it's not
     */
    Boolean updateSalary(Long idUserConnected, Salary salary);

    /**
     * Get Salary by ID salary
     * @param idSalary ID of the salary wanted
     * @return Return a salary with the ID wanted
     */
    Salary getByIdSalary(Long idSalary);
}
