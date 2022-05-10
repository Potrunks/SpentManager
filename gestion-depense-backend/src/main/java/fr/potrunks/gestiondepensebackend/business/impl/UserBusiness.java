package fr.potrunks.gestiondepensebackend.business.impl;

import fr.potrunks.gestiondepensebackend.business.UserIBusiness;
import fr.potrunks.gestiondepensebackend.entity.UserEntity;
import fr.potrunks.gestiondepensebackend.repository.UserIRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
@Slf4j
public class UserBusiness implements UserIBusiness {

    @Autowired
    private UserIRepository userIRepository;

    @Override
    public UserEntity findById(Long idUserConnected) {
        log.info("Find user with id {}", idUserConnected);
        UserEntity userEntity = userIRepository.getById(idUserConnected);
        return userEntity;
    }
}
