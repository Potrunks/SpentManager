package fr.potrunks.gestiondepensebackend.business.impl;

import fr.potrunks.gestiondepensebackend.business.UserIBusiness;
import fr.potrunks.gestiondepensebackend.entity.PeriodSpentEntity;
import fr.potrunks.gestiondepensebackend.entity.UserEntity;
import fr.potrunks.gestiondepensebackend.model.User;
import fr.potrunks.gestiondepensebackend.repository.PeriodSpentIRepository;
import fr.potrunks.gestiondepensebackend.repository.UserIRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
@Slf4j
public class UserBusiness implements UserIBusiness {

    @Autowired
    private UserIRepository userIRepository;
    @Autowired
    private PeriodSpentIRepository periodSpentIRepository;

    @Override
    public UserEntity findById(Long idUserConnected) {
        log.info("Find user with id {}", idUserConnected);
        UserEntity userEntity = userIRepository.getById(idUserConnected);
        return userEntity;
    }

    @Override
    public List<User> getAllByPeriodSpentInProgress() {
        log.info("Start to get all users in period spent in progress");
        log.info("Start to find the period spent in progress");
        PeriodSpentEntity periodSpentInProgress = periodSpentIRepository.findByEndDatePeriodSpentIsNull();
        if (periodSpentInProgress == null) {
            log.warn("No period spent in progress in the database");
            return null;
        }
        log.info("Start to get all users in period spent in progress");
        List<UserEntity> userEntityList = userIRepository.findByIdPeriodSpentInProgress(periodSpentInProgress.getIdPeriodSpent());
        if (userEntityList == null) {
            log.warn("No user in period spent in progress");
            return null;
        }
        List<User> userList = new ArrayList<>();
        log.info("Start to copy all propeties of userEntity object to user object");
        for (UserEntity userEntity: userEntityList
             ) {
            User user = new User();
            BeanUtils.copyProperties(userEntity, user);
            userList.add(user);
        }
        log.info("Get all users in period spent in progress finished");
        return userList;
    }
}
