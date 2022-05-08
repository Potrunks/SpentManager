package fr.potrunks.gestiondepensebackend.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class Spent {
    private Long idSpent;
    private Float valueSpent;
    private LocalDate dateSpent;
    private String nameSpent;
    private String commentSpent;
    private Long idUserConnected;
    private Long idSpentCategorySelected;
}
