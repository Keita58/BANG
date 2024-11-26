package DAO;

import entity.Cartes;
import entity.Jugadors;

import java.util.ArrayList;
import java.util.List;

public class CartaDAO extends GenericDAO<Cartes, Integer> implements ICartaDAO {

    public List<Cartes> getCartesSenseJugador(){
        List<Cartes> cartesList = (List<Cartes>) this.findAll();
        List<Cartes> cartesListFinal = new ArrayList<>();
        for(Object o : cartesList){
            Cartes c = (Cartes) o;
            if (c.getCartesJugador() != null)
                continue;
            cartesListFinal.add(c);
        }
        return cartesListFinal;
    }

    public List<Cartes> getCartesBangSenseJugador(){
        List<Cartes> cartesList = (List<Cartes>) this.findAll();
        List<Cartes> cartesListFinal = new ArrayList<>();
        for(Object o : cartesList){
            Cartes c = (Cartes) o;
            if (!c.getCartaTipusCarta().getNom().equals("Bang") && c.getCartesJugador() != null)
                continue;
            cartesListFinal.add(c);
        }
        return cartesListFinal;
    }

    public List<Cartes> getCartesJugador(Jugadors jugador){
        List<Cartes> cartesList = (List<Cartes>) this.findAll();
        List<Cartes> cartesListFinal = new ArrayList<>();
        for(Object o : cartesList){
            Cartes c = (Cartes) o;
            if (c.getCartesJugador() != null && c.getCartesJugador().getNom().equals(jugador.getNom()))
                cartesListFinal.add(c);
        }
        return cartesListFinal;
    }
    public List<Cartes> getCartesFallasteJugador(Jugadors jugador){
        List<Cartes> cartesList = (List<Cartes>) this.findAll();
        List<Cartes> cartesListFinal = new ArrayList<>();
        for(Object o : cartesList){
            Cartes c = (Cartes) o;
            if (!c.getCartesJugador().getNom().equals(jugador.getNom()) && !c.getCartaTipusCarta().getNom().equals("Has fallat!"))
                continue;
            cartesListFinal.add(c);
        }
        return cartesListFinal;
    }
}
