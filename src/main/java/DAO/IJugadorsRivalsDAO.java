package DAO;

import entity.JugadorsRivals;
import java.io.Serializable;


public interface IJugadorsRivalsDAO extends IGenericDAO<JugadorsRivals, Integer>, Serializable {
    public void borrarTots();
}
