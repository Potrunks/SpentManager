package fr.potrunks.gestiondepensebackend.controller;

import fr.potrunks.gestiondepensebackend.business.AccountIBusiness;
import fr.potrunks.gestiondepensebackend.entity.UserEntity;
import fr.potrunks.gestiondepensebackend.model.User;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
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

    /**
     * Allow to create a new account in the database after verification if administrator password is OK and if the mail doesn't exist yet
     * @param user User will be add to the database
     * @return Return a Response Entity with a Map of String (key) and Boolean (value) for each step of the creation
     */
    @PostMapping("/new")
    public ResponseEntity<Map<String, Boolean>> createNewAccount(@RequestBody User user) {
        log.info("Start create new account from createNewAccount of AccountController");
        Map<String, Boolean> response = new HashMap<>();
        response = accountBusiness.addNewAccount(user, response);
        log.info("Return response to the front app");
        return ResponseEntity.ok(response);
    }

    /**
     * Allow to verify authentification then return to the UI the user authenticate
     * @param user User who want to authenticate
     * @return Return User successfully authenticate to the UI
     */
    @PostMapping("/connect")
    public ResponseEntity<Map<String, Object>> connectAccount(@RequestBody User user) {
        log.info("Start to attempt the account connection for user : {}", user.getMailUser());
        Map<String, Object> response = new HashMap<>();
        response = accountBusiness.authentication(user, response);
        if ((Boolean) response.get("authenticated") == true) {
            UserEntity userEntity = accountBusiness.getUserByMailUser(user);
            BeanUtils.copyProperties(userEntity, user);
            response.put("idUserConnected", user.getIdUser());
            response.put("firstNameUserConnected", user.getFirstNameUser());
        }
        log.info("Return response to the front app");
        return ResponseEntity.ok(response);
    }
}
