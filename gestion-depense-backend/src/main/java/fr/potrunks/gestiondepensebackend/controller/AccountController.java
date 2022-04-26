package fr.potrunks.gestiondepensebackend.controller;

import fr.potrunks.gestiondepensebackend.business.UserIBusiness;
import fr.potrunks.gestiondepensebackend.model.User;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;

@Slf4j
@CrossOrigin(origins = "http://localhost:3000")
@RestController
@RequestMapping("/spentmanager/account/")
public class AccountController {

    @Autowired
    private UserIBusiness userBusiness;

    @PostMapping("/new")
    public String createNewAccount(@RequestBody User user) {
        log.info("Start to create a new account");
        userBusiness.addUser(user);
        log.info("Return the user to the front app");
        return "New account successfully created";
    }
}
