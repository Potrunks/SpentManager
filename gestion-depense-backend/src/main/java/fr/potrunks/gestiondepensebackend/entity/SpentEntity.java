package fr.potrunks.gestiondepensebackend.entity;

import lombok.Data;

import javax.persistence.*;
import java.time.LocalDate;

@Entity
@Data
@Table(name = "spent")
public class SpentEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_spent")
    private Long idSpent;
    @Column(name = "value_spent")
    private Float valueSpent;
    @Column(name = "date_spent")
    private LocalDate dateSpent;
    @Column(name = "name_spent")
    private String nameSpent;
    @Column(name = "comment_spent")
    private String commentSpent;
    @ManyToOne
    @JoinColumn(name = "id_monthly_spent")
    private MonthlySpentEntity monthlySpentEntity;
    @ManyToOne
    @JoinColumn(name = "id_spent_category")
    private SpentCategoryEntity spentCategoryEntity;
    @ManyToOne
    @JoinColumn(name = "id_user")
    private UserEntity userEntity;
    @ManyToOne
    @JoinColumn(name = "id_period_spent")
    private PeriodSpentEntity periodSpentEntity;
}
