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

    /**
     * Verify if the mail already exist in data base
     * @param mailUser Mail concerned
     * @return Return true if the mail already exist else return false
     */
    public Boolean verifyMailExist(String mailUser) {
        log.info("Start to verify if mail already exist in database");
        if (userRepository.findByMailUser(mailUser) != null) {
            log.warn("The mail {} already exist", mailUser);
            return true;
        }
        log.info("The mail {} don't exist yet", mailUser);
        return false;
    }

    /**
     * Get the administrator password in database.
     * Hashed the input administrator password from the UI + the administrator salt from the database (fetched before).
     * Compare the both hashed administrator password
     * @param user User with the input administrator password
     * @return Return true if the input and the database are the same else return false
     */
    public Boolean verifyAdminPassword(User user) {
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

    /**
     * Compare 2 password and verify if are the same
     * @param password1 First password
     * @param password2 Second password
     * @return Return true if the 2 password are the same else return false;
     */
    public Boolean verifyPassword(String password1, String password2) {
        log.info("Start to compare 2 password together");
        if (password1 == null || password2 == null) {
            return false;
        }
        if (password1.equals(password2)) {
            log.info("The 2 passwords are equals");
            return true;
        }
        log.warn("The 2 passwords are not the same");
        return false;
    }

    /**
     * Generate salt for the password security
     * @return A String with the salt
     */
    public String saltGenerator() {
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

    /**
     * Hashed a password + a salt before to stock in database.
     * @param passwordAndSalt The password and the salt we need to hashed
     * @return A String who contain password and salt hashed
     */
    public String hashedPassword(String passwordAndSalt) {
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

    /**
     * Format the first name in order to contain the first letter in UpperCase
     * @param firstNameToFormat First name to format
     * @return A String with the first name formatted
     */
    public String formatFirstName(String firstNameToFormat) {
        String firstNameFormatted = firstNameToFormat.substring(0, 1).toUpperCase() + firstNameToFormat.substring(1).toLowerCase();
        return firstNameFormatted;
    }

    /**
     * Format the last name in order to full UpperCase
     * @param lastNameToFormat Last name to format
     * @return A String with the last name formatted
     */
    public String formatLastName(String lastNameToFormat) {
        String lastNameFormatted = lastNameToFormat.toUpperCase();
        return lastNameFormatted;
    }
}
