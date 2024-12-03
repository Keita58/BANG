package DAO;

import jakarta.persistence.EntityManager;
import org.hibernate.HibernateException;

public class JugadorsRivalsDAO extends GenericDAO<entity.JugadorsRivals, Integer> implements IJugadorsRivalsDAO {

    EntityManager entity = factory.createEntityManager();

    public void borrarTots() {
        try{
            entity.getTransaction().begin();
            entity.createQuery("DELETE FROM JugadorsRivals").executeUpdate();
            entity.getTransaction().commit();
        }
        catch(HibernateException e) {
            if(entity != null && entity.getTransaction() != null) {
                System.out.println("Rollback");
                entity.getTransaction().rollback();
            }
        }
    }
}
