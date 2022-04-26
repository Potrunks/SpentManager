package fr.potrunks.gestiondepensebackend.entity;

import lombok.Data;

import javax.persistence.*;
import java.time.LocalDate;
import java.util.List;

@Entity
@Data
@Table(name = "period_spent")
public class PeriodSpentEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_period_spent")
    private Long idPeriodSpent;
    @Column(name = "start_date_period_spent")
    private LocalDate startDatePeriodSpent;
    @Column(name = "edn_date_period_spent")
    private LocalDate endDatePeriodSpent;
    @OneToMany(mappedBy = "periodSpentEntity", cascade = CascadeType.PERSIST)
    private List<SalaryEntity> salaryEntityList;
    @OneToMany(mappedBy = "periodSpentEntity", cascade = CascadeType.PERSIST)
    private List<DebtEntity> debtEntityList;
    @ManyToOne
    @JoinColumn(name = "id_user")
    private UserEntity userEntity;
    @OneToMany(mappedBy = "periodSpentEntity", cascade = CascadeType.PERSIST)
    private List<SpentEntity> spentEntityList;
}
