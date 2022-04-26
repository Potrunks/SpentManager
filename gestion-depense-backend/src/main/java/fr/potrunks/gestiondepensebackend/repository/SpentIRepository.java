package fr.potrunks.gestiondepensebackend.repository;

import fr.potrunks.gestiondepensebackend.entity.SpentEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface SpentIRepository extends JpaRepository<SpentEntity, Long> {

}
