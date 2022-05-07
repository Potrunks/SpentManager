package fr.potrunks.gestiondepensebackend.business.impl;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
public class AccountBusinessTests {

    @Autowired
    private AccountBusiness accountBusiness;

    @Test
    public void withoutFirstCharUppercase_returnFirstCharUppercase() {
        String woFirstCharUppercase = "pIcCoLo";
        AccountBusiness accountBusiness = new AccountBusiness();
        String result = accountBusiness.formatFirstName(woFirstCharUppercase);
        assertTrue(Character.isUpperCase(result.charAt(0)));
    }

    @Test
    public void withFirstCharUppercase_returnFirstCharUppercase() {
        String wFirstCharUppercase = "PIcCoLo";
        AccountBusiness accountBusiness = new AccountBusiness();
        String result = accountBusiness.formatFirstName(wFirstCharUppercase);
        assertTrue(Character.isUpperCase(result.charAt(0)));
    }

    @Test
    public void randomCharUppercase_returnAllCharLowercaseExceptFirstChar() {
        String randomCharUppercase = "pIcCoLo";
        AccountBusiness accountBusiness = new AccountBusiness();
        char[] stringResult = accountBusiness.formatFirstName(randomCharUppercase).toCharArray();
        Boolean resultAttempted = true;
        for (int i = 1; i < stringResult.length; i++) {
            if (!Character.isLowerCase(stringResult[i])) {
                resultAttempted = false;
            }
        }
        assertTrue(resultAttempted);
    }

    @Test
    public void containLowercase_returnFullUppercase() {
        String containLowercase = "pIcCoLo";
        AccountBusiness accountBusiness = new AccountBusiness();
        char[] stringResult = accountBusiness.formatLastName(containLowercase).toCharArray();
        Boolean resultAttempted = true;
        for (int i = 0; i < stringResult.length; i++) {
            if (Character.isLowerCase(stringResult[i])) {
                resultAttempted = false;
            }
        }
        assertTrue(resultAttempted);
    }

    @Test
    public void saltGeneratorInvoke_returnStringWithLenghtEqualThree() {
        String result = accountBusiness.saltGenerator();
        assertEquals(3, result.length());
    }

    @Test
    public void stringNotHashed_returnStringHashed() {
        String result = accountBusiness.hashedPassword("Trunks92!uqk");
        assertEquals("��P��3���i���e\u0002���*�\u001A\u0019�+��ғ2\u0018�", result);
    }
}
