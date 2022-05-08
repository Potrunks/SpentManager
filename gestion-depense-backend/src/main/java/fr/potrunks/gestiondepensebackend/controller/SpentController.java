package fr.potrunks.gestiondepensebackend.controller;

import fr.potrunks.gestiondepensebackend.business.PeriodSpentIBusiness;
import fr.potrunks.gestiondepensebackend.business.SpentCategoryIBusiness;
import fr.potrunks.gestiondepensebackend.business.SpentIBusiness;
import fr.potrunks.gestiondepensebackend.business.UserIBusiness;
import fr.potrunks.gestiondepensebackend.entity.PeriodSpentEntity;
import fr.potrunks.gestiondepensebackend.entity.SpentCategoryEntity;
import fr.potrunks.gestiondepensebackend.entity.SpentEntity;
import fr.potrunks.gestiondepensebackend.entity.UserEntity;
import fr.potrunks.gestiondepensebackend.model.Spent;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;

@CrossOrigin(origins = "http://localhost:3000")
@RestController
@RequestMapping("/spentmanager/spent/")
@Slf4j
public class SpentController {

    @Autowired
    private SpentIBusiness spentBusiness;
    @Autowired
    private UserIBusiness userIBusiness;
    @Autowired
    private SpentCategoryIBusiness spentCategoryIBusiness;
    @Autowired
    private PeriodSpentIBusiness periodSpentIBusiness;

    @PostMapping("/new")
    public ResponseEntity<Map<String, Object>> newSpent(@RequestBody Spent spent) {
        /*
        Chercher le user connecté
        Chercher la spent category
        Chercher la period spent en cours
        Ajouter la nouvelle depense
         */
        log.info("Creating new spent...");
        Map<String, Object> response = new HashMap<>();
        UserEntity userConnected;
        SpentCategoryEntity spentCategorySelected;
        PeriodSpentEntity periodSpentInProgress;
        SpentEntity newSpent;
        Boolean newSpentAdded = false;
        userConnected = userIBusiness.findById(spent.getIdUserConnected());
        if (userConnected.getIdUser() != null) {
            spentCategorySelected = spentCategoryIBusiness.findById(spent.getIdSpentCategorySelected());
            if (spentCategorySelected.getIdSpentCategory() != null) {
                periodSpentInProgress = periodSpentIBusiness.findInProgress();
                if (periodSpentInProgress.getIdPeriodSpent() != null) {
                    newSpent = spentBusiness.create(userConnected, spentCategorySelected, periodSpentInProgress, spent);
                    if (newSpent.getIdSpent() != null) {
                        newSpentAdded = true;
                        log.info("New spent created successfully");
                    } else {
                        log.warn("Error during creation of the new spent");
                    }
                } else {
                    log.warn("Error during searching period spent in progress");
                }
            } else {
                log.warn("Error during searching spent category with id {}", spent.getIdSpentCategorySelected());
            }
        } else {
            log.warn("Error during searching user with id {}", spent.getIdUserConnected());
        }
        response.put("newSpentAdded", newSpentAdded);
        return ResponseEntity.ok(response);
    }

    /*
    @GetMapping("/getall")
    public List<Spent> fetchSpents() {
        return spentBusiness.getSpents();
    }
     */

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<Map<String, Boolean>> deleteSpent(@PathVariable Long id) {
        Boolean deleted = false;
        deleted = spentBusiness.deleteSpent(id);
        Map<String, Boolean> response = new HashMap<>();
        response.put("deleted", deleted);
        return ResponseEntity.ok(response);
    }

    @GetMapping("/get/{id}")
    public ResponseEntity<Spent> getSpent(@PathVariable Long id) {
        Spent spent = null;
        spent = spentBusiness.getSpent(id);
        return ResponseEntity.ok(spent);
    }

    @PutMapping("/update/{id}")
    public ResponseEntity<Spent> updateSpent(@PathVariable Long id, @RequestBody Spent spent) {
        spent = spentBusiness.updateSpent(id, spent);
        return ResponseEntity.ok(spent);
    }
}
