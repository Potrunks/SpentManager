package fr.potrunks.gestiondepensebackend.entity;

import lombok.Data;

import javax.persistence.*;
import java.util.List;

@Entity
@Data
@Table(name = "user")
public class UserEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_user")
    private Long idUser;
    @Column(name = "last_name_user")
    private String lastNameUser;
    @Column(name = "first_name_user")
    private String firstNameUser;
    @Column(name = "mail_user")
    private String mailUser;
    @Column(name = "password_user")
    private String passwordUser;
    @Column(name = "salt_user")
    private String saltUser;
    @OneToMany(mappedBy = "userEntity", cascade = CascadeType.PERSIST)
    private List<DebtEntity> debtEntityList;
    @OneToMany(mappedBy = "userEntity", cascade = CascadeType.PERSIST)
    private List<SalaryEntity> salaryEntityList;
    @OneToMany(mappedBy = "userEntity", cascade = CascadeType.PERSIST)
    private List<PeriodSpentEntity> periodSpentEntityList;
    @OneToMany(mappedBy = "userEntity", cascade = CascadeType.PERSIST)
    private List<SpentEntity> spentEntityList;
    @OneToMany(mappedBy = "userEntity", cascade = CascadeType.PERSIST)
    private List<MonthlySpentEntity> monthlySpentEntityList;
}
