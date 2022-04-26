package fr.potrunks.gestiondepensebackend.entity;

import lombok.Data;

import javax.persistence.*;
import java.time.LocalDate;

@Entity
@Data
@Table(name = "debt")
public class DebtEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_debt")
    private Long idDebt;
    @Column(name = "value_debt")
    private Float valueDebt;
    @Column(name = "date_debt")
    private LocalDate dateDebt;
    @ManyToOne
    @JoinColumn(name = "id_period_spent")
    private PeriodSpentEntity periodSpentEntity;
    @ManyToOne
    @JoinColumn(name = "id_user")
    private UserEntity userEntity;
}
