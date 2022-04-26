package fr.potrunks.gestiondepensebackend.business;

import fr.potrunks.gestiondepensebackend.model.Spent;

import java.util.List;

public interface SpentIBusiness {
    Spent addSpent(Spent spent);

    List<Spent> getSpents();

    Boolean deleteSpent(Long id);

    Spent getSpent(Long id);

    Spent updateSpent(Long id, Spent spent);
}
