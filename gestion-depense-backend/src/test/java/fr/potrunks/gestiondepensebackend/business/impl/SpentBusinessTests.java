package fr.potrunks.gestiondepensebackend.business.impl;

import fr.potrunks.gestiondepensebackend.entity.UserEntity;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class SpentBusinessTests {

    @Autowired
    private SpentBusiness spentBusiness;

    @Test
    void idUserIsInTheList_returnTrue() {
        UserEntity user1 = new UserEntity();
        user1.setIdUser(1L);
        UserEntity user2 = new UserEntity();
        user2.setIdUser(2L);
        List<UserEntity> userEntityList = new ArrayList<>();
        userEntityList.add(user1);
        userEntityList.add(user2);

        UserEntity userToTest = new UserEntity();
        userToTest.setIdUser(2L);

        assertTrue(spentBusiness.userIsInListOfUsers(userEntityList, userToTest));
    }

    @Test
    void idUserIsNotInTheList_returnFalse() {
        UserEntity user1 = new UserEntity();
        user1.setIdUser(1L);
        UserEntity user2 = new UserEntity();
        user2.setIdUser(2L);
        List<UserEntity> userEntityList = new ArrayList<>();
        userEntityList.add(user1);
        userEntityList.add(user2);

        UserEntity userToTest = new UserEntity();
        userToTest.setIdUser(3L);

        assertFalse(spentBusiness.userIsInListOfUsers(userEntityList, userToTest));
    }

    @Test
    void userListIsNull_returnFalse() {
        UserEntity userToTest = new UserEntity();
        userToTest.setIdUser(3L);

        assertFalse(spentBusiness.userIsInListOfUsers(null, userToTest));
    }

    @Test
    void userToTestIsNull_returnFalse() {
        UserEntity user1 = new UserEntity();
        user1.setIdUser(1L);
        UserEntity user2 = new UserEntity();
        user2.setIdUser(2L);
        List<UserEntity> userEntityList = new ArrayList<>();
        userEntityList.add(user1);
        userEntityList.add(user2);

        assertFalse(spentBusiness.userIsInListOfUsers(userEntityList, null));
    }
}