package fr.potrunks.gestiondepensebackend.business.impl;

import fr.potrunks.gestiondepensebackend.business.UserIBusiness;
import fr.potrunks.gestiondepensebackend.entity.UserEntity;
import fr.potrunks.gestiondepensebackend.model.User;
import fr.potrunks.gestiondepensebackend.repository.UserIRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Slf4j
@Service
public class UserBusiness implements UserIBusiness {

    @Autowired
    private UserIRepository userRepository;

    @Override
    public User addUser(User user) {
        log.info("Start to add new User in the database");
        // Verify the 2 password is the same
        UserEntity userEntity = new UserEntity();
        BeanUtils.copyProperties(user, userEntity);
        userEntity = userRepository.save(userEntity);
        log.info("New User id {} succesfully added in the database", userEntity.getIdUser());
        return user;
    }
}
