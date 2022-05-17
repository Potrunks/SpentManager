package fr.potrunks.gestiondepensebackend.controller;

import fr.potrunks.gestiondepensebackend.business.SalaryIBusiness;
import fr.potrunks.gestiondepensebackend.model.Salary;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@CrossOrigin(origins = "http://localhost:3000")
@RestController
@RequestMapping("/spentmanager/salary/")
@Slf4j
public class SalaryController {

    @Autowired
    private SalaryIBusiness salaryIBusiness;

    /**
     * Updated salary by ID
     *
     * @param salary Salary to update
     * @return Return a boolean true if the salary is updated and false if it's not
     */
    @PutMapping("/update/{idUserConnected}")
    public ResponseEntity<Boolean> updateSalary(@PathVariable Long idUserConnected, @RequestBody Salary salary) {
        log.info("Start updating process for salary id {}", salary.getIdSalary());
        Boolean salaryIsUpdated = salaryIBusiness.updateSalary(idUserConnected, salary);
        log.info("End of updating process for salary id {}", salary.getIdSalary());
        return ResponseEntity.ok(salaryIsUpdated);
    }

    /**
     * Get salary by ID
     * @param idSalary ID of the salary wanted
     * @return Return a Salary model to the UI
     */
    @GetMapping("/get/{idSalary}")
    public ResponseEntity<Salary> getSalarybyIdSalary(@PathVariable Long idSalary) {
        log.info("Start get process for salary id {}", idSalary);
        Salary salary = salaryIBusiness.getByIdSalary(idSalary);
        return ResponseEntity.ok(salary);
    }
}
