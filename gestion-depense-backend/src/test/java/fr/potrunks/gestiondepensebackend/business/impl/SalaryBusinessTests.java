package fr.potrunks.gestiondepensebackend.business.impl;

import fr.potrunks.gestiondepensebackend.entity.PeriodSpentEntity;
import fr.potrunks.gestiondepensebackend.entity.UserEntity;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;

@SpringBootTest
public class SalaryBusinessTests {

    @Autowired
    private SalaryBusiness salaryBusiness;

    @Test
    public void setNewSalary_returnSalaryValueNotNull() {
        UserEntity userEntity = new UserEntity();
        userEntity.setIdUser(42L);
        PeriodSpentEntity periodSpentEntity = new PeriodSpentEntity();
        periodSpentEntity.setIdPeriodSpent(42L);
        Float salaryValue = 42f;
        assertNotNull(salaryBusiness.setNewSalary(userEntity, periodSpentEntity, salaryValue).getValueSalary());
    }

    @Test
    public void setNewSalary_returnUserNotNull() {
        UserEntity userEntity = new UserEntity();
        userEntity.setIdUser(42L);
        PeriodSpentEntity periodSpentEntity = new PeriodSpentEntity();
        periodSpentEntity.setIdPeriodSpent(42L);
        Float salaryValue = 42f;
        assertNotNull(salaryBusiness.setNewSalary(userEntity, periodSpentEntity, salaryValue).getUserEntity());
    }

    @Test
    public void setNewSalary_returnPeriodSpentNotNull() {
        UserEntity userEntity = new UserEntity();
        userEntity.setIdUser(42L);
        PeriodSpentEntity periodSpentEntity = new PeriodSpentEntity();
        periodSpentEntity.setIdPeriodSpent(42L);
        Float salaryValue = 42f;
        assertNotNull(salaryBusiness.setNewSalary(userEntity, periodSpentEntity, salaryValue).getPeriodSpentEntity());
    }

    @Test
    public void setNewSalary_returnCreationDateNotNull() {
        UserEntity userEntity = new UserEntity();
        userEntity.setIdUser(42L);
        PeriodSpentEntity periodSpentEntity = new PeriodSpentEntity();
        periodSpentEntity.setIdPeriodSpent(42L);
        Float salaryValue = 42f;
        assertNotNull(salaryBusiness.setNewSalary(userEntity, periodSpentEntity, salaryValue).getDateSalary());
    }

    @Test
    public void setNewSalary_returnIdSalaryNull() {
        UserEntity userEntity = new UserEntity();
        userEntity.setIdUser(42L);
        PeriodSpentEntity periodSpentEntity = new PeriodSpentEntity();
        periodSpentEntity.setIdPeriodSpent(42L);
        Float salaryValue = 42f;
        assertNull(salaryBusiness.setNewSalary(userEntity, periodSpentEntity, salaryValue).getIdSalary());
    }
}
