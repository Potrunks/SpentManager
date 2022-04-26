package fr.potrunks.gestiondepensebackend.entity;

import lombok.Data;

import javax.persistence.*;
import java.time.LocalDate;

@Entity
@Data
@Table(name = "salary")
public class SalaryEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_salary")
    private Long idSalary;
    @Column(name = "value_salary")
    private Float valueSalary;
    @Column(name = "date_salary")
    private LocalDate dateSalary;
    @ManyToOne
    @JoinColumn(name = "id_user")
    private UserEntity userEntity;
    @ManyToOne
    @JoinColumn(name = "id_period_spent")
    private PeriodSpentEntity periodSpentEntity;
}
