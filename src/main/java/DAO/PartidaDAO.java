package DAO;

import java.util.List;

import entity.Partides;

public class PartidaDAO extends GenericDAO<Partides, Integer> implements IPartidaDAO {

    public Partides getPartidaFinal() {
        
        List<Partides> partidesRealitzades = this.findAll();
        for (Object partides : partidesRealitzades) {
            Partides p = (Partides) partides;
            if(!p.getPartidaFinalitzada())
                return p;
        }
        
        return null;
    }


}
