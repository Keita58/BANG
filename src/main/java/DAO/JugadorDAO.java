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
        }
        return jugadorsAmbPersonatges;
    }

    public List<Jugadors> getJugadorsAmbPersonatgesVida() {

        List<Jugadors> jugadors = (List<Jugadors>) this.findAll();
        List<Jugadors> jugadorsAmbPersonatges = new ArrayList<>();
        for (Object ju : jugadors) {
            Jugadors j = (Jugadors) ju;
            if(j.getPersonatgeDelJugador().getBales() > 0)
                jugadorsAmbPersonatges.add(j);

        }
        return jugadorsAmbPersonatges;
    }


    public List<Jugadors> RetornarJugadorsOrdenats() {

        List<Jugadors> jugadors = (List<Jugadors>) this.findAll();
        List<Jugadors> jugadorsVida = new ArrayList<>();

        for (Object ju : jugadors) {
            Jugadors j = (Jugadors) ju;
            if(j.getPersonatgeDelJugador().getBales() > 0)
                jugadorsVida.add(j);
        }
        Comparator<Jugadors> comparadorPosicio = Comparator.comparingInt(Jugadors::getPosicio);
        jugadorsVida.sort(comparadorPosicio);
        return jugadorsVida;
    }

    public List<Jugadors> getJugadorsRolsVida(Rol rol) {
        
        List<Jugadors> jugadors = (List<Jugadors>) this.findAll();
        List<Jugadors> jugadorsAmbRol = new ArrayList<>();
        List<Jugadors> jugadorsVida = new ArrayList<>();

        for (Object ju : jugadors) {
            Jugadors j = (Jugadors) ju;
            if(j.getPersonatgeDelJugador().getBales() > 0)
                jugadorsVida.add(j);
        }
        for (Object j : jugadorsVida) {
            Jugadors ju = (Jugadors) j;
            if(ju.getRolJugador().getNomRol().equals(rol))
                jugadorsAmbRol.add(ju);
        }
        return jugadorsAmbRol;
    }

    public List<Jugadors> getNumJugadors(int numJugadors) {

        List<Jugadors> jugadors = (List<Jugadors>) this.findAll();
        List<Jugadors> jugadorsAmbPersonatges = new ArrayList<>();
        for (int i = 0; i < numJugadors; i++) {
            jugadorsAmbPersonatges.add(jugadors.get(i));
        }
        Collections.shuffle(jugadorsAmbPersonatges);
        return jugadorsAmbPersonatges;
    }

    public List<Jugadors> getNumJugadorsAmbVida(int numJugadors) {

        List<Jugadors> jugadors = (List<Jugadors>) this.getNumJugadors(numJugadors);
        List<Jugadors> jugadorsAmbPersonatges = new ArrayList<>();
        for (int i = 0; i < numJugadors; i++) {
            if(jugadors.get(i).getPersonatgeDelJugador().getBales() > 0)
                jugadorsAmbPersonatges.add(jugadors.get(i));
        }
        Collections.shuffle(jugadorsAmbPersonatges);
        return jugadorsAmbPersonatges;
    }
}
