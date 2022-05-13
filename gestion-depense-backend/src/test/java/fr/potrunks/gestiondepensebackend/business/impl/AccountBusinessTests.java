package fr.potrunks.gestiondepensebackend.business.impl;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
public class AccountBusinessTests {

    @Autowired
    private AccountBusiness accountBusiness;

    /**
     * When first name with the first char no uppercase, return a first name with first char is uppercase
     */
    @Test
    public void withoutFirstCharUppercase_returnFirstCharUppercase() {
        String woFirstCharUppercase = "pIcCoLo";
        AccountBusiness accountBusiness = new AccountBusiness();
        String result = accountBusiness.formatFirstName(woFirstCharUppercase);
        assertTrue(Character.isUpperCase(result.charAt(0)));
    }

    /**
     * When first name with the first char is uppercase, return a first name with first char is uppercase
     */
    @Test
    public void withFirstCharUppercase_returnFirstCharUppercase() {
        String wFirstCharUppercase = "PIcCoLo";
        AccountBusiness accountBusiness = new AccountBusiness();
        String result = accountBusiness.formatFirstName(wFirstCharUppercase);
        assertTrue(Character.isUpperCase(result.charAt(0)));
    }

    /**
     * When first name with random char is uppercase, return a first name with first char is uppercase only
     */
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

    /**
     * When last name with random char is lowercase, return a last name full uppercase
     */
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

    /**
     * When string is not hashed, return a string hashed
     */
    @Test
    public void stringNotHashed_returnStringHashed() {
        String result = accountBusiness.hashedPassword("Trunks92!uqk");
        assertEquals("��P��3���i���e\u0002���*�\u001A\u0019�+��ғ2\u0018�", result);
    }

    /**
     * When the 2 string are the same, return true
     */
    @Test
    public void twoStringSame_returnTrue() {
        Boolean result = accountBusiness.verifyPassword("same", "same");
        assertTrue(result);
    }

    /**
     * When the 2 string are not the same, return false
     */
    @Test
    public void twoPasswordNotSame_returnFalse() {
        Boolean result = accountBusiness.verifyPassword("same", "notsame");
        assertFalse(result);
    }

    /**
     * When one string is null, return false
     */
    @Test
    public void oneStringIsNull_returnFalse() {
        Boolean result = accountBusiness.verifyPassword(null, "alone");
        assertFalse(result);
    }

    /**
     * When two string is null, return false
     */
    @Test
    public void twoPasswordIsNull_returnFalse() {
        Boolean result = accountBusiness.verifyPassword(null, null);
        assertFalse(result);
    }

    /**
     * When salt is generated, return 3 char
     */
    @Test
    public void generateSalt_returnStringWithThreeChar() {
        assertEquals(3, accountBusiness.saltGenerator().length());
    }

    /**
     * When salt is generated, return a string without digit
     */
    @Test
    public void generateSalt_returnStringWithoutDigit() {
        String regex = "^([^0-9]*)$";
        Boolean result = accountBusiness.saltGenerator().matches(regex);
        assertTrue(result);
    }

    /**
     * When salt is generated, return a string without special char
     */
    @Test
    public void generateSalt_returnStringWithoutSpecialChar() {
        String regex = "^[A-Za-z0-9 ]+$";
        Boolean result = accountBusiness.saltGenerator().matches(regex);
        assertTrue(result);
    }

    /**
     * When salt is generated, return a string with only lowercase
     */
    @Test
    public void generateSalt_returnStringWithOnlyLowerCase() {
        Boolean result = true;
        char[] arrayCharToTest = accountBusiness.saltGenerator().toCharArray();
        for (char charToTest : arrayCharToTest) {
            if (!Character.isLowerCase(charToTest)) {
                result = false;
            }
        }
        assertTrue(result);
    }
}
