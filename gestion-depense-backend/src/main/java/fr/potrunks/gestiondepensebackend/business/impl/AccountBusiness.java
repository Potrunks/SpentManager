package fr.potrunks.gestiondepensebackend.business.impl;

import fr.potrunks.gestiondepensebackend.business.AccountIBusiness;
import fr.potrunks.gestiondepensebackend.entity.UserEntity;
import fr.potrunks.gestiondepensebackend.model.User;
import fr.potrunks.gestiondepensebackend.repository.UserIRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Random;

@Slf4j
@Service
public class AccountBusiness implements AccountIBusiness {

    @Autowired
    private UserIRepository userRepository;

    @Override
    public Boolean addNewAccount(User user) {
        log.info("Start to add new account from addNewAccount of AccountBusiness");
        UserEntity userEntity = new UserEntity();
        if(verifyAdminPassword(user) && !verifyMailExist(user.getMailUser())) {
            BeanUtils.copyProperties(user, userEntity);
            userEntity.setSaltUser(saltGenerator());
            userEntity.setPasswordUser(hashedPassword(userEntity.getPasswordUser() + userEntity.getSaltUser()));
            userEntity.setAdministrator(false);
            userEntity = userRepository.save(userEntity);
            log.info("New account User id {} successfully added", userEntity.getIdUser());
            return true;
        }
        log.warn("Fail to add new account");
        return false;
    }

    private Boolean verifyMailExist(String mailUser) {
        log.info("Start to verify if mail already exist in database");
        if(userRepository.findByMailUser(mailUser) != null){
            log.warn("The mail {} already exist", mailUser);
            return true;
        }
        log.info("The mail {} don't exist yet", mailUser);
        return false;
    }

    private Boolean verifyAdminPassword(User user) {
        log.info("Start verification of administrator password in database against administrator password from UI");
        UserEntity userEntity = userRepository.findByAdministratorTrue();
        String passwordToVerify = hashedPassword(user.getAdminPassword() + userEntity.getSaltUser());
        if (verifyPassword(userEntity.getPasswordUser(), passwordToVerify)) {
            log.info("The administrator password in database and from the UI are the same");
            return true;
        }
        log.warn("The administrator password in database and from the UI are not the same");
        return false;
    }

    private Boolean verifyPassword(String password1, String password2) {
        log.info("Start to compare 2 password together");
        if (password1.equals(password2)) {
            log.info("The 2 passwords are equals");
            return true;
        }
        log.warn("The 2 passwords are not the same");
        return false;
    }

    private String saltGenerator() {
        log.info("Start to generate a salt");
        Random random = new Random();
        String salt = "";
        for (int i = 0; i < 3; i++) {
            char c = (char) (random.nextInt(26) + 97);
            salt += c;
        }
        log.info("The salt is generated");
        return salt;
    }

    private String hashedPassword(String passwordAndSalt) {
        log.info("Start to hash password and salt together");
        try {
            log.info("Initialization of the hashing");
            MessageDigest digest = MessageDigest.getInstance("SHA-256");
            byte[] encodedHash = digest.digest(passwordAndSalt.getBytes(StandardCharsets.UTF_8));
            passwordAndSalt = new String(encodedHash, StandardCharsets.UTF_8);
        } catch (NoSuchAlgorithmException e) {
            log.info("Error during the hashing");
            e.printStackTrace();
        }
        log.info("The hashing is successfully finished");
        return passwordAndSalt;
    }
}
