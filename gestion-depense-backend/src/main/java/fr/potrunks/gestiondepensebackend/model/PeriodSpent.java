package fr.potrunks.gestiondepensebackend.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class PeriodSpent {
    private Long idPeriodSpent;
    private LocalDate startDatePeriodSpent;
    private LocalDate endDatePeriodSpent;
    private Long idNextPeriodSpent;
    private Long idPreviousPeriodSpent;
}
