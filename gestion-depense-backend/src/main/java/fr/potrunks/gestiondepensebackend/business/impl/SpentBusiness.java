package fr.potrunks.gestiondepensebackend.business.impl;

import fr.potrunks.gestiondepensebackend.business.SpentIBusiness;
import fr.potrunks.gestiondepensebackend.entity.PeriodSpentEntity;
import fr.potrunks.gestiondepensebackend.entity.SpentCategoryEntity;
import fr.potrunks.gestiondepensebackend.entity.SpentEntity;
import fr.potrunks.gestiondepensebackend.entity.UserEntity;
import fr.potrunks.gestiondepensebackend.model.Spent;
import fr.potrunks.gestiondepensebackend.repository.PeriodSpentIRepository;
import fr.potrunks.gestiondepensebackend.repository.SpentIRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;
import java.util.stream.Collectors;

@Service
@Slf4j
public class SpentBusiness implements SpentIBusiness {

    @Autowired
    private SpentIRepository spentRepository;
    @Autowired
    private PeriodSpentIRepository periodSpentIRepository;

    public SpentBusiness(SpentIRepository spentRepository) {
        this.spentRepository = spentRepository;
    }

    @Override
    public Spent addSpent(Spent spent) {
        SpentEntity spentEntity = new SpentEntity();
        BeanUtils.copyProperties(spent, spentEntity);
        spentRepository.save(spentEntity);
        return spent;
    }

    @Override
    public List<Spent> getSpentsByPeriodSpentInProgress() {
        log.info("Start to search all spents by period spent in progress");
        PeriodSpentEntity periodSpentEntityInProgress = periodSpentIRepository.findByEndDatePeriodSpentIsNull();
        List<SpentEntity> spentEntities = spentRepository.findByPeriodSpentEntity(periodSpentEntityInProgress);
        List<Spent> spents = spentEntities
                .stream()
                .map(spent -> new Spent(
                        spent.getIdSpent(),
                        spent.getValueSpent(),
                        spent.getDateSpent(),
                        spent.getNameSpent(),
                        spent.getCommentSpent(),
                        null,
                        null,
                        spent.getSpentCategoryEntity().getNameSpentCategory()))
                .collect(Collectors.toList());
        return spents;
    }

    @Override
    public Boolean deleteSpent(Long id) {
        SpentEntity spentEntity = spentRepository.findById(id).get();
        spentRepository.delete(spentEntity);
        return true;
    }

    @Override
    public Spent getSpent(Long id) {
        SpentEntity spentEntity = spentRepository.findById(id).get();
        Spent spent = new Spent();
        BeanUtils.copyProperties(spentEntity, spent);
        return spent;
    }

    @Override
    public Spent updateSpent(Long id, Spent spent) {
        SpentEntity spentEntity = spentRepository.findById(id).get();
        spentEntity.setDateSpent(spent.getDateSpent());
        spentEntity.setValueSpent(spent.getValueSpent());
        spentRepository.save(spentEntity);
        return spent;
    }

    @Override
    public SpentEntity create(UserEntity userConnected, SpentCategoryEntity spentCategorySelected, PeriodSpentEntity periodSpentInProgress, Spent spent) {
        log.info("Set up new spent in progress...");
        SpentEntity spentEntity = new SpentEntity();
        spentEntity.setValueSpent(spent.getValueSpent());
        spentEntity.setDateSpent(LocalDate.now());
        spentEntity.setNameSpent(spent.getNameSpent());
        spentEntity.setCommentSpent(spent.getCommentSpent());
        spentEntity.setSpentCategoryEntity(spentCategorySelected);
        spentEntity.setUserEntity(userConnected);
        spentEntity.setPeriodSpentEntity(periodSpentInProgress);
        log.info("Add new spent in database");
        spentEntity = spentRepository.save(spentEntity);
        return spentEntity;
    }
}
