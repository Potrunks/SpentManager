package fr.potrunks.gestiondepensebackend.business;

import fr.potrunks.gestiondepensebackend.model.User;
import org.springframework.http.ResponseEntity;

import java.util.Map;

public interface AccountIBusiness {
    Boolean addNewAccount(User user);

    Map<String, Boolean> authentication(User user, Map<String, Boolean> response);
}
