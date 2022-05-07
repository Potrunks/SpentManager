package fr.potrunks.gestiondepensebackend.business.impl;

import fr.potrunks.gestiondepensebackend.entity.PeriodSpentEntity;
import fr.potrunks.gestiondepensebackend.entity.UserEntity;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
public class DebtBusinessTests {

    @Autowired
    private DebtBusiness debtBusiness;

    @Test
    public void setNewDebt_returnIdDebtNull() {
        UserEntity userEntity = new UserEntity();
        PeriodSpentEntity periodSpentEntity = new PeriodSpentEntity();
        assertNull(debtBusiness.setNewDebt(userEntity, periodSpentEntity).getIdDebt());
    }

    @Test
    public void setNewDebt_returnDebtValueEqualsZero() {
        UserEntity userEntity = new UserEntity();
        PeriodSpentEntity periodSpentEntity = new PeriodSpentEntity();
        assertEquals(0f, debtBusiness.setNewDebt(userEntity, periodSpentEntity).getValueDebt());
    }

    @Test
    public void setNewDebt_returnDebtDateNotNull() {
        UserEntity userEntity = new UserEntity();
        PeriodSpentEntity periodSpentEntity = new PeriodSpentEntity();
        assertNotNull(debtBusiness.setNewDebt(userEntity, periodSpentEntity).getDateDebt());
    }

    @Test
    public void setNewDebt_returnPeriodSpentNotNull() {
        UserEntity userEntity = new UserEntity();
        PeriodSpentEntity periodSpentEntity = new PeriodSpentEntity();
        assertNotNull(debtBusiness.setNewDebt(userEntity, periodSpentEntity).getPeriodSpentEntity());
    }

    @Test
    public void setNewDebt_returnUserNotNull() {
        UserEntity userEntity = new UserEntity();
        PeriodSpentEntity periodSpentEntity = new PeriodSpentEntity();
        assertNotNull(debtBusiness.setNewDebt(userEntity, periodSpentEntity).getUserEntity());
    }
}
