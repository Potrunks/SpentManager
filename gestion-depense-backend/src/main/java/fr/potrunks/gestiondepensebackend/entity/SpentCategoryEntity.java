package fr.potrunks.gestiondepensebackend.entity;

import lombok.Data;

import javax.persistence.*;
import java.util.List;

@Entity
@Data
@Table(name = "spent_category")
public class SpentCategoryEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_spent_category")
    private Long idSpentCategory;
    @Column(name = "name_spent_category")
    private String nameSpentCategory;
    @OneToMany(mappedBy = "spentCategoryEntity", cascade = CascadeType.PERSIST)
    private List<SpentEntity> spentEntityList;
    @OneToMany(mappedBy = "spentCategoryEntity", cascade = CascadeType.PERSIST)
    private List<MonthlySpentEntity> monthlySpentEntityList;
}
