package fr.potrunks.gestiondepensebackend.controller;

import fr.potrunks.gestiondepensebackend.business.AccountIBusiness;
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
    private AccountIBusiness accountBusiness;

    @PostMapping("/new")
    public ResponseEntity<Map<String, Boolean>> createNewAccount(@RequestBody User user) {
        log.info("Start create new account from createNewAccount of AccountController");
        Map<String, Boolean> response = new HashMap<>();
        Boolean newAccountAdded = accountBusiness.addNewAccount(user);
        response.put("newAccountAdded", newAccountAdded);
        log.info("Return response to the front app");
        return ResponseEntity.ok(response);
    }

    @PostMapping("/connect")
    public ResponseEntity<Map<String, Boolean>> connectAccount(@RequestBody User user) {
        log.info("Start to attempt the account connection for user : {}", user.getMailUser());
        Map<String, Boolean> response = new HashMap<>();
        response = accountBusiness.authentication(user, response);
        log.info("Return response to the front app");
        return ResponseEntity.ok(response);
    }
}
