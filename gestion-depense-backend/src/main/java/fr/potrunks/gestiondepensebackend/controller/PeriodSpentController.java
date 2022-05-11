package fr.potrunks.gestiondepensebackend.controller;

import fr.potrunks.gestiondepensebackend.business.PeriodSpentIBusiness;
import fr.potrunks.gestiondepensebackend.business.SalaryIBusiness;
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

    @PostMapping("/new/{idUserConnected}")
    public ResponseEntity<Map<String, Object>> createNewPeriodSpent(@PathVariable Long idUserConnected, @RequestBody Salary salary) {
        log.info("Starting creation of a new period spent");
        Map<String, Object> response = new HashMap<>();
        Boolean periodSpentCreated = false;
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
}
