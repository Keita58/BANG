package factory;

import DAO.*;

public class MySQLFactory implements DAOFactory{
    public IGenericDAO create(String daoType){
        if ("arma".equalsIgnoreCase(daoType)) {
            return new ArmaDAO();
        } else if ("personatge".equalsIgnoreCase(daoType)) {
            return new PersonatgeDAO();
        } else if ("rol".equalsIgnoreCase(daoType)) {
            return new RolDAO();
        }else if ("tipusCarta".equalsIgnoreCase(daoType)) {
            return new TipusCartaDAO();
        }else if ("carta".equalsIgnoreCase(daoType)) {
            return new CartaDAO();
        }else if ("partida".equalsIgnoreCase(daoType)) {
            return new PartidaDAO();
        }else if ("jugador".equalsIgnoreCase(daoType)) {
            return new JugadorDAO();
        }else if ("jugadorRival".equalsIgnoreCase(daoType)){
            return new JugadorsRivalsDAO();
        }
        return null;
    }
}
