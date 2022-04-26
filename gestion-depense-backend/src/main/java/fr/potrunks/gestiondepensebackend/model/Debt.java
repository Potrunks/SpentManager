package fr.potrunks.gestiondepensebackend.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class Debt {
    private Long idDebt;
    private Float valueDebt;
    private LocalDate dateDebt;
}
