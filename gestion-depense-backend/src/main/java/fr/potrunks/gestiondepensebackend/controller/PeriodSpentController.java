package fr.potrunks.gestiondepensebackend.controller;

import fr.potrunks.gestiondepensebackend.business.PeriodSpentIBusiness;
import fr.potrunks.gestiondepensebackend.business.SalaryIBusiness;
import fr.potrunks.gestiondepensebackend.model.PeriodSpent;
import fr.potrunks.gestiondepensebackend.model.Salary;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;

@Slf4j
@CrossOrigin(origins = "http://localhost:3000")
@RestController
@RequestMapping("/spentmanager/periodspent/")
public class PeriodSpentController {

    @Autowired
    private PeriodSpentIBusiness periodSpentBusiness;
    @Autowired
    private SalaryIBusiness salaryBusiness;

    /**
     * Allow to access all the process for create a new period of spent. There are 4 level of verification, if a new salary has been created or updated, if the period spent in progress is closable,
     * if the new period spent is correctly added in database and if the new salary associate to the period spent is correctly added
     *
     * @param idUserConnected ID of the user who want to create a new period spent
     * @param salary Salary of the user who want to create a new period spent
     * @return Return a Response Entity of Map of String (key) and Object (value) with each step of the creation of a new period spent
     */
    @PostMapping("/new/{idUserConnected}")
    public ResponseEntity<Map<String, Object>> createNewPeriodSpent(@PathVariable Long idUserConnected, @RequestBody Salary salary) {
        log.info("Starting creation of a new period spent");
        Map<String, Object> response = new HashMap<>();
        Boolean periodSpentCreated = false;
        response = salaryBusiness.addSalaryInPeriodSpentInProgress(response, idUserConnected, salary);
        if ((Boolean) response.get("salaryCreatedOrUpdated") == true) {
            response.put("periodSpentCreated", periodSpentCreated);
            return ResponseEntity.ok(response);
        }
        response = periodSpentBusiness.closePeriodSpentInProgress(response);
        if ((Boolean) response.get("periodSpentInProgressClosed") == true) {
            response = periodSpentBusiness.addNewPeriodSpent(idUserConnected, response);
            if ((Boolean) response.get("periodSpentAdded") == true) {
                response = salaryBusiness.addNewSalary(idUserConnected, (Long) response.get("idPeriodSpentCreated"), salary.getValueSalary(), response);
                if ((Boolean) response.get("newSalaryCreated") == true) {
                    periodSpentCreated = true;
                }
            }
        }
        if (periodSpentCreated == false) {
            log.warn("Error during the creation of a new period spent");
        }
        response.put("periodSpentCreated", periodSpentCreated);
        log.info("Return response to the front app");
        return ResponseEntity.ok(response);
    }

    /**
     * Allow to fetch the period spent in progress
     * @return Return a Response Entity with a period spent found
     */
    @GetMapping("/getInProgress")
    public ResponseEntity<PeriodSpent> fetchPeriodSpentInProgress() {
        log.info("Start to fetch period spent in progress");
        PeriodSpent periodSpentInProgress = periodSpentBusiness.getPeriodSpentInProgress();
        return ResponseEntity.ok(periodSpentInProgress);
    }

    /**
     * Controller fetch the period spent by id for the UI
     * @param idPeriodSpent ID Period Spent to fetch data
     * @return Return a Period Spent model to the UI
     */
    @GetMapping("/get/{idPeriodSpent}")
    public ResponseEntity<PeriodSpent> fetchPeriodSpentById(@PathVariable Long idPeriodSpent) {
        log.info("Start to fetch period spent id {}", idPeriodSpent);
        PeriodSpent periodSpentFetchById = periodSpentBusiness.getPeriodSpentById(idPeriodSpent);
        return ResponseEntity.ok(periodSpentFetchById);
    }

    /**
     * Check if period spent in progress exist
     * @return Return a boolean true if exist and false if is not exist
     */
    @GetMapping("/getInProgress/exist")
    public ResponseEntity<Boolean> checkPeriodSpentInProgressExist() {
        log.info("Start checking process for period spent in progress exist");
        Boolean exist = periodSpentBusiness.checkPeriodSpentInProgressExist();
        log.info("End checking process for period spent in progress exist");
        return ResponseEntity.ok(exist);
    }
}
