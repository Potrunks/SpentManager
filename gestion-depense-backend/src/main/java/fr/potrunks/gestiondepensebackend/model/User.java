package fr.potrunks.gestiondepensebackend.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class User {
    // private Long idUser;
    private String lastNameUser;
    private String firstNameUser;
    private String mailUser;
    private String passwordUser;
    // private String secondPasswordUser;
    private String adminPassword;
    // private String saltUser;
}
