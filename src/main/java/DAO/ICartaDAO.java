package DAO;

import entity.Cartes;
import entity.Jugadors;

import java.io.Serializable;
import java.util.List;

public interface ICartaDAO extends IGenericDAO<Cartes, Integer>, Serializable {
    List<Cartes> getCartesSenseJugador();
    List<Cartes> getCartesBangSenseJugador();
    List<Cartes> getCartesJugador(Jugadors jugador);
    List<Cartes> getCartesFallasteJugador(Jugadors jugador);

}
