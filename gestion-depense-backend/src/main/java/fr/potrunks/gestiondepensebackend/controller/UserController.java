package fr.potrunks.gestiondepensebackend.controller;

import fr.potrunks.gestiondepensebackend.business.UserIBusiness;
import fr.potrunks.gestiondepensebackend.model.User;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@CrossOrigin(origins = "http://localhost:3000")
@RestController
@RequestMapping("/spentmanager/user/")
@Slf4j
public class UserController {

    @Autowired
    private UserIBusiness userIBusiness;

    @GetMapping("/getusersbyperiodspentinprogress")
    public List<User> fetchUsersByPeriodSpentInProgress() {
        log.info("Start to fetch all users in period spent in progress");
        return userIBusiness.getAllByPeriodSpentInProgress();
    }
}
