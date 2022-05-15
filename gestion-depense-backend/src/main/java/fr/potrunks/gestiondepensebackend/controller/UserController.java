package fr.potrunks.gestiondepensebackend.controller;

import fr.potrunks.gestiondepensebackend.business.UserIBusiness;
import fr.potrunks.gestiondepensebackend.model.User;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@CrossOrigin(origins = "http://localhost:3000")
@RestController
@RequestMapping("/spentmanager/user/")
@Slf4j
public class UserController {

    @Autowired
    private UserIBusiness userIBusiness;

    /**
     * Fetch all users in the period spent in progress
     * @return List of User model
     */
    @GetMapping("/getusersbyperiodspentinprogress")
    public List<User> fetchUsersByPeriodSpentInProgress() {
        log.info("Start to fetch all users in period spent in progress");
        return userIBusiness.getAllByPeriodSpentInProgress();
    }

    /**
     * Fetch a list of user present in the period spent wanted
     * @param idPeriodSpent ID of the period spent wanted
     * @return Return a List of User model present in the period spent wanted
     */
    @GetMapping("/getUsersByPeriodSpent/{idPeriodSpent}")
    public List<User> fetchUsersByPeriodSpentByID(@PathVariable Long idPeriodSpent) {
        log.info("Start to fetch all users in period spent id {}", idPeriodSpent);
        return userIBusiness.getAllByIdPeriodSpent(idPeriodSpent);
    }

    /**
     * Fetch a list of users model in the database for the UI
     * @return Return a list of users model for the UI
     */
    @GetMapping("/getAll")
    public List<User> fetchAllUsers() {
        log.info("Start to fetch all users in database");
        return userIBusiness.getAllUsers();
    }
}
