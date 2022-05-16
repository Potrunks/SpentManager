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
import java.util.List;
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

    /**
     * Initiate the creation of a new spent after verify some things like the user is connected, the spent category is found and the period spent in progress is found.
     * @param spent Spent who want to add in database
     * @return Return a Response Entity contain a Map of String (key) and Object (value) who contain all information during each step of this method
     */
    @PostMapping("/new")
    public ResponseEntity<Map<String, Object>> newSpent(@RequestBody Spent spent) {
        log.info("Creating new spent...");
        Map<String, Object> response = new HashMap<>();
        UserEntity userExpenser;
        SpentCategoryEntity spentCategorySelected;
        PeriodSpentEntity periodSpentInProgress;
        SpentEntity newSpent;
        Boolean newSpentAdded = false;
        Boolean periodSpentInProgressExist = true;
        userExpenser = userIBusiness.findById(spent.getIdUserExpenser());
        if (userExpenser.getIdUser() != null) {
            spentCategorySelected = spentCategoryIBusiness.findById(spent.getIdSpentCategorySelected());
            if (spentCategorySelected.getIdSpentCategory() != null) {
                periodSpentInProgress = periodSpentIBusiness.findInProgress();
                if (periodSpentInProgress != null) {
                    newSpent = spentBusiness.create(userExpenser, spentCategorySelected, periodSpentInProgress, spent);
                    if (newSpent.getIdSpent() != null) {
                        newSpentAdded = true;
                        log.info("New spent created successfully");
                    } else {
                        log.warn("Error during creation of the new spent");
                    }
                } else {
                    periodSpentInProgressExist = false;
                    log.warn("Error during searching period spent in progress");
                }
            } else {
                log.warn("Error during searching spent category with id {}", spent.getIdSpentCategorySelected());
            }
        } else {
            log.warn("Error during searching user with id {}", spent.getIdUserConnected());
        }
        response.put("newSpentAdded", newSpentAdded);
        response.put("periodSpentInProgressExist", periodSpentInProgressExist);
        return ResponseEntity.ok(response);
    }

    /**
     * Initiate the fetching of all spent during the period spent in progress
     * @return Return a List of Spent model for the UI
     */
    @GetMapping("/getAllByPeriodSpentInProgress")
    public List<Spent> fetchSpentsByPeriodSpentInProgress() {
        log.info("Start to get all spents in period spent in progress...");
        return spentBusiness.getSpentsByPeriodSpentInProgress();
    }

    /**
     * Fetch all spents in period spent id wanted
     * @param idPeriodSpent ID of the period spent wanted
     * @return Return a list of Spent model in the period spent id wanted
     */
    @GetMapping("/getAllByPeriodSpent/{idPeriodSpent}")
    public List<Spent> fetchSpentsByPeriodSpent(@PathVariable Long idPeriodSpent) {
        log.info("Start to get all spents by period spent id {}", idPeriodSpent);
        return spentBusiness.getSpentsByIdPeriodSpent(idPeriodSpent);
    }

    /**
     * Delete a Spent Entity by ID
     * @param idSpent ID of the spent to delete
     * @return Return a Map of String (key) and Boolean (value) with the result of the deletion
     */
    @DeleteMapping("/delete/{idSpent}")
    public ResponseEntity<Map<String, Boolean>> deleteSpent(@PathVariable Long idSpent) {
        Boolean deleted = false;
        deleted = spentBusiness.deleteSpent(idSpent);
        Map<String, Boolean> response = new HashMap<>();
        response.put("deleted", deleted);
        return ResponseEntity.ok(response);
    }

    // A supprimer
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
    // A supprimer
}
