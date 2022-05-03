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
import java.util.Locale;
import java.util.Map;
import java.util.Random;

@Slf4j
@Service
public class AccountBusiness implements AccountIBusiness {

    @Autowired
    private UserIRepository userRepository;

    @Override
    public Map<String, Boolean> addNewAccount(User user, Map<String, Boolean> response) {
        log.info("Start to add new account from addNewAccount of AccountBusiness");
        Boolean newAccountAdded = false;
        UserEntity userEntity = new UserEntity();
        Boolean adminPasswordOK = verifyAdminPassword(user);
        // Boolean adminPasswordOK = true;
        response.put("adminPasswordOK", adminPasswordOK);
        Boolean mailAlreadyExist = verifyMailExist(user.getMailUser());
        response.put("mailAlreadyExist", mailAlreadyExist);
        if (adminPasswordOK && !mailAlreadyExist) {
            BeanUtils.copyProperties(user, userEntity);
            userEntity.setFirstNameUser(formatFirstName(user.getFirstNameUser()));
            userEntity.setLastNameUser(formatLastName(user.getLastNameUser()));
            userEntity.setSaltUser(saltGenerator());
            userEntity.setPasswordUser(hashedPassword(userEntity.getPasswordUser() + userEntity.getSaltUser()));
            userEntity.setAdministrator(false);
            // userEntity.setAdministrator(true);
            userEntity = userRepository.save(userEntity);
            log.info("New account User id {} successfully added", userEntity.getIdUser());
            newAccountAdded = true;
            response.put("newAccountAdded", newAccountAdded);
            return response;
        }
        log.warn("Fail to add new account");
        response.put("newAccountAdded", newAccountAdded);
        return response;
    }

    @Override
    public Map<String, Object> authentication(User user, Map<String, Object> response) {
        log.info("Start authentication from AccountBusiness for user {}", user.getMailUser());
        Boolean authenticated = false;
        Boolean mailExisted = verifyMailExist(user.getMailUser());
        response.put("mailExisted", mailExisted);
        if (mailExisted) {
            log.info("Start to get user from database corresponding to {}", user.getMailUser());
            UserEntity userEntity = userRepository.findByMailUser(user.getMailUser());
            String inputPassword = hashedPassword(user.getPasswordUser() + userEntity.getSaltUser());
            authenticated = verifyPassword(inputPassword, userEntity.getPasswordUser());
            response.put("authenticated", authenticated);
            log.info("{} is authenticated successfully", userEntity.getMailUser());
            return response;
        }
        response.put("authenticated", authenticated);
        log.warn("{} fail to authenticate", user.getMailUser());
        return response;
    }

    @Override
    public UserEntity getUserByMailUser(User user) {
        log.info("Start to get user {}", user.getMailUser());
        UserEntity userEntity = userRepository.findByMailUser(user.getMailUser());
        log.info("User {} find successfully", userEntity.getMailUser());
        return userEntity;
    }

    private Boolean verifyMailExist(String mailUser) {
        log.info("Start to verify if mail already exist in database");
        if (userRepository.findByMailUser(mailUser) != null) {
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

    public String formatFirstName(String firstNameToFormat) {
        String firstNameFormatted = firstNameToFormat.substring(0, 1).toUpperCase() + firstNameToFormat.substring(1).toLowerCase();
        return firstNameFormatted;
    }

    public String formatLastName(String lastNameToFormat) {
        String lastNameFormatted = lastNameToFormat.toUpperCase();
        return lastNameFormatted;
    }
}
