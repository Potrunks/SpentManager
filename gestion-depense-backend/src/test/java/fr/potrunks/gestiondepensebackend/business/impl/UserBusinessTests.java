package fr.potrunks.gestiondepensebackend.business.impl;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class UserBusinessTests {

    @Autowired
    private UserBusiness userBusiness;

    /**
     * When 2 values is used, return the correct rate spent
     */
    @Test
    void twoValue_returnCorrectRate() {
        assertEquals(50f, userBusiness.calculateRate(2000f, 1000f));
    }

    @Test
    void valueToEstimateIsNull_returnZeroValue() {
        assertEquals(0f, userBusiness.calculateRate(2000f, null));
    }

    @Test
    void maxValueIsNull_returnZeroValue() {
        assertEquals(0f, userBusiness.calculateRate(null, 1000f));
    }

    @Test
    void valueToEstimateGreaterThanMaxValue_returnValueGreaterThanHundred() {
        Float result = userBusiness.calculateRate(2000f, 3000f);
        Boolean expected = false;
        if (result >= 100f) {
            expected = true;
        }
        assertTrue(expected);
    }

    @Test
    void valueToEstimateSmallerThanMaxValue_returnValueSmallerThanHundred() {
        Float result = userBusiness.calculateRate(2000f, 1000f);
        Boolean expected = false;
        if (result <= 100f) {
            expected = true;
        }
        assertTrue(expected);
    }

    @Test
    void twoValue_returnCorrectShareSpentValue() {
        assertEquals(1000f, userBusiness.calculateShareSpent(2000f, 50f));
    }

    @Test
    void sumSpentsValueIsNull_returnZeroShareSpentValue() {
        assertEquals(0f, userBusiness.calculateShareSpent(null, 50f));
    }

    @Test
    void householdShareValueIsNull_returnZeroShareSpentValue() {
        assertEquals(0f, userBusiness.calculateShareSpent(2000f, null));
    }

    @Test
    void withAllValue_returnCorrectDebtValue() {
        assertEquals(1100f, userBusiness.calculateDebt(2000f, 1000f, 100f, 200f));
    }

    @Test
    void spentAlreadyPaidValueGreaterThanShareSpent_returnDebtValueSmallerOrEqualThanZero() {
        Float result = userBusiness.calculateDebt(2000f, 3000f, 0f, 0f);
        Boolean expected = false;
        if (result <= 0f) {
            expected = true;
        }
        assertTrue(expected);
    }

    @Test
    void spentAlreadyPaidValueSmallerThanShareSpent_returnDebtValueGreaterThanZero() {
        Float result = userBusiness.calculateDebt(2000f, 1000f, 0f, 0f);
        Boolean expected = false;
        if (result > 0f) {
            expected = true;
        }
        assertTrue(expected);
    }

    @Test
    void allDepositsValueIsZero_returnCorrectDebtValue() {
        assertEquals(1000f, userBusiness.calculateDebt(2000f, 1000f, 100f, 0f));
    }

    @Test
    void shareSpentValueIsZero_returnCorrectDebtValue() {
        assertEquals(-1000f, userBusiness.calculateDebt(0f, 1000f, 0f, 0f));
    }

    @Test
    void oneOrMoreValueIsNull_returnCorrectDebtValue() {
        assertEquals(2000f, userBusiness.calculateDebt(2000f, null, 100f, null));
    }
}