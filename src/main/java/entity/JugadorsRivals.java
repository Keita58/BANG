package entity;

import jakarta.persistence.Column;
import jakarta.persistence.EmbeddedId;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;

import java.io.Serializable;
import java.util.Objects;

@Entity
@Table(name = "JugadorsRivals")
public class JugadorsRivals implements Serializable {
    @EmbeddedId

    @Column(name = "id")
    JugadorRivalsId id;

    @Column(name = "Distancia", columnDefinition = "INT Default 1")
    int distanciaRival;

    public JugadorsRivals() {
        super();
    }

    public JugadorsRivals(JugadorRivalsId id, int DistanciaRival) {
        this.id = id;
        this.distanciaRival = DistanciaRival;
    }

    public int getDistanciaRival() {
        return distanciaRival;
    }

    public void setDistanciaRival(int distanciaRival) {
        this.distanciaRival = distanciaRival;
    }

    public JugadorRivalsId getIdRival() {
        return id;
    }

    public void setIdRival(JugadorRivalsId idRival) {
        this.id = idRival;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        JugadorsRivals that = (JugadorsRivals) o;
        return Objects.equals(id, that.id);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(id);
    }

    @Override
    public String toString() {
        return "JugadorsRivals{" +
                "idRival=" + id +
                ", distanciaRival=" + distanciaRival +
                '}';
    }
}
