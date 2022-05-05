package fr.potrunks.gestiondepensebackend.controller;

import fr.potrunks.gestiondepensebackend.business.PeriodSpentIBusiness;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.Map;

@Slf4j
@CrossOrigin(origins = "http://localhost:3000")
@RestController
@RequestMapping("/spentmanager/periodspent/")
public class PeriodSpentController {

    @Autowired
    private PeriodSpentIBusiness periodSpentBusiness;

    @PostMapping("/new")
    public ResponseEntity<Map<String, Object>> createNewPeriodSpent() {
        log.info("Starting creation of a new period spent");
        Map<String, Object> response = new HashMap<>();
        response = periodSpentBusiness.closePeriodSpentInProgress(response);
        // Creer une spending period
        // Ajouter un salaire
        // Creer une dette
        log.info("Return response to the front app");
        return ResponseEntity.ok(response);
    }
}
