package fr.potrunks.gestiondepensebackend.business.impl;

import fr.potrunks.gestiondepensebackend.business.SpentIBusiness;
import fr.potrunks.gestiondepensebackend.entity.SpentEntity;
import fr.potrunks.gestiondepensebackend.model.Spent;
import fr.potrunks.gestiondepensebackend.repository.SpentIRepository;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class SpentBusiness implements SpentIBusiness {

    @Autowired
    private SpentIRepository spentRepository;

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
    public List<Spent> getSpents() {
        List<SpentEntity> spentEntities = spentRepository.findAll();
        List<Spent> spents = spentEntities
                .stream()
                .map(spent -> new Spent(
                        spent.getIdSpent(),
                        spent.getValueSpent(),
                        spent.getDateSpent()))
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
}
