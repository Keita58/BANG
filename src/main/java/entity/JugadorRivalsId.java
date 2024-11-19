package entity;

import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;

import java.io.Serializable;
import java.util.Objects;

public class JugadorRivalsId implements Serializable {

    @ManyToOne
    @JoinColumn(name = "idJugador")
    Jugadors idJugador;

    @ManyToOne
    @JoinColumn(name = "idRival")
    Jugadors idRival;

     public JugadorRivalsId() {
        super();
    }

    public JugadorRivalsId(Jugadors idRival, Jugadors idJugador) {
        super();
        this.idRival = idRival;
        this.idJugador = idJugador;
    }

    public Jugadors getIdRival() {
        return idRival;
    }

    public void setIdRival(Jugadors idRival) {
        this.idRival = idRival;
    }

    public Jugadors getIdJugador() {
        return idJugador;
    }

    public void setIdJugador(Jugadors idJugador) {
        this.idJugador = idJugador;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        JugadorRivalsId that = (JugadorRivalsId) o;
        return Objects.equals(idRival, that.idRival) && Objects.equals(idJugador, that.idJugador);
    }

    @Override
    public int hashCode() {
        return Objects.hash(idRival, idJugador);
    }

    @Override
    public String toString() {
        return "JugadorRivalsId{" +
                "idRival=" + idRival +
                ", idJugador=" + idJugador +
                '}';
    }
}
