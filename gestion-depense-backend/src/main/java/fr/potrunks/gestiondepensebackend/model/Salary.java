package fr.potrunks.gestiondepensebackend.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class Salary {
    private Long idSalary;
    private Float valueSalary;
    private LocalDate dateSalary;
}
