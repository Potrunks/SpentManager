package fr.potrunks.gestiondepensebackend.controller;

import fr.potrunks.gestiondepensebackend.business.SpentCategoryIBusiness;
import fr.potrunks.gestiondepensebackend.model.SpentCategory;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@CrossOrigin(origins = "http://localhost:3000")
@RestController
@RequestMapping("/spentmanager/spentcategory/")
@Slf4j
public class SpentCategoryController {

    @Autowired
    private SpentCategoryIBusiness spentCategoryIBusiness;

    @GetMapping("/getall")
    public ResponseEntity<List<SpentCategory>> getAll() {
        List<SpentCategory> spentCategories = spentCategoryIBusiness.getAll();
        return ResponseEntity.ok(spentCategories);
    }
}
