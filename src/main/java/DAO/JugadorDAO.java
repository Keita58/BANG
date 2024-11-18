package DAO;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

import entity.Jugadors;
import entity.Rol;

public class JugadorDAO extends GenericDAO<Jugadors, Integer> implements IJugadorDAO {

    public List<Jugadors> getJugadorsAmbPersonatges() {
        
        List<Jugadors> jugadors = (List<Jugadors>) this.findAll();
        List<Jugadors> jugadorsAmbPersonatges = new ArrayList<>();
        for (Object jugador : jugadors) {
            Jugadors j = (Jugadors) jugador;
            if(j.getPersonatgeDelJugador() != null)
                jugadorsAmbPersonatges.add(j);
            else
                continue;
        }
        return jugadorsAmbPersonatges;
    }

    public List<Jugadors> getJugadorsAmbPersonatgesVidaAltres(Jugadors jugador) {

        List<Jugadors> jugadors = (List<Jugadors>) this.findAll();
        List<Jugadors> jugadorsAmbPersonatges = new ArrayList<>();
        for (Object ju : jugadors) {
            Jugadors j = (Jugadors) ju;
            if(j.getPersonatgeDelJugador() != null && j.getIdJugador() != jugador.getIdJugador()) {
                if(j.getPersonatgeDelJugador().getBales() > 0)
                    jugadorsAmbPersonatges.add(j);
            }
            continue;
        }
        return jugadorsAmbPersonatges;
    }

    public List<Jugadors> RetornarJugadorsOrdenats() {

        List<Jugadors> jugadors = (List<Jugadors>) this.findAll();

        Comparator<Jugadors> comparadorPosicio = new Comparator<Jugadors>() {
            
            @Override
            public int compare(Jugadors j1, Jugadors j2) {
                return j1.getPosicio() - j2.getPosicio();
            }
        };
        Collections.sort(jugadors, comparadorPosicio);
        return jugadors;
    }

    public List<Jugadors> getJugadorsRolsVida(Rol rol) {
        
        List<Jugadors> jugadors = (List<Jugadors>) this.findAll();
        List<Jugadors> jugadorsAmbRol = new ArrayList<>();
        for (Object j : jugadors) {
            Jugadors ju = (Jugadors) j;
            if(ju.getRolJugador().getNomRol().equals(rol))
                jugadorsAmbRol.add(ju);
            continue;
        }
        return jugadorsAmbRol;
    }
}
