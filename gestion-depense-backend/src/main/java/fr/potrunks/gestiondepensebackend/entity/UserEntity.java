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
    @Column(name = "administrator_user")
    private Boolean administrator;
    @OneToMany(mappedBy = "userEntity", cascade = CascadeType.PERSIST)
    private List<SalaryEntity> salaryEntityList;
    @ManyToMany(cascade = CascadeType.PERSIST)
    @JoinTable(name = "association_user_periodspent",
            joinColumns = @JoinColumn(name = "id_user"),
            inverseJoinColumns = @JoinColumn(name = "id_period_spent"))
    private List<PeriodSpentEntity> periodSpentEntityList;
    @OneToMany(mappedBy = "userEntity", cascade = CascadeType.PERSIST)
    private List<SpentEntity> spentEntityList;
    @OneToMany(mappedBy = "userEntity", cascade = CascadeType.PERSIST)
    private List<MonthlySpentEntity> monthlySpentEntityList;
}
