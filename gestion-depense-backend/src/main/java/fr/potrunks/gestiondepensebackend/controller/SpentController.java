package fr.potrunks.gestiondepensebackend.controller;

import fr.potrunks.gestiondepensebackend.business.SpentIBusiness;
import fr.potrunks.gestiondepensebackend.model.Spent;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@CrossOrigin(origins = "http://localhost:3000")
@RestController
@RequestMapping("/spentmanager/spent/")
public class SpentController {

    @Autowired
    private SpentIBusiness spentBusiness;

    @PostMapping("/new")
    public Spent newSpent(@RequestBody Spent spent){
        return spentBusiness.addSpent(spent);
    }

    @GetMapping("/getall")
    public List<Spent> fetchSpents(){
        return spentBusiness.getSpents();
    }

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
