package DAO;

import entity.Jugadors;
import entity.Rol;

import java.io.Serializable;
import java.util.List;

public interface IJugadorDAO extends IGenericDAO<Jugadors, Integer>, Serializable {
    List<Jugadors> getJugadorsAmbPersonatges();
    List<Jugadors> getJugadorsAmbPersonatgesVidaAltres(Jugadors jugador);
    List<Jugadors> getJugadorsAmbPersonatgesVida();
    List<Jugadors> RetornarJugadorsOrdenats();
    List<Jugadors> getJugadorsRolsVida(Rol rol);
    List<Jugadors> getNumJugadors(int numJugadors);
    List<Jugadors> getNumJugadorsAmbVida(int numJugadors);
}
