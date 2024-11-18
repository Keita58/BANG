package entity;

import jakarta.persistence.*;

import javax.annotation.processing.Generated;
import java.io.Serializable;
import java.util.Objects;

@Entity
@Table(name = "Cartes")
public class Cartes implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "idCarta", insertable = false, updatable = false)
    int idCarta;

    @Enumerated(EnumType.STRING)
    @Column(name = "palCarta", nullable = true)
    Pal palCarta;

    @Column(name = "numeroCarta", nullable = false, updatable = false)
    int numeroCarta;

    @ManyToOne
    @JoinColumn(name="idTipusCartes")
    TipusCartes CartatipusCarta;

    @ManyToOne
    @JoinColumn(name = "idJugador")
    Jugadors jugadorQueTeLesCartes;

    public Cartes() {
        super();
    }

    public Cartes(int numeroCarta) {
        this.numeroCarta = numeroCarta;
    }

    public Cartes(Pal palCarta, int numeroCarta) {
        this.palCarta = palCarta;
        this.numeroCarta = numeroCarta;
    }

    public Pal getPalCarta() {
        return palCarta;
    }

    public void setPalCarta(Pal palCarta) {
        this.palCarta = palCarta;
    }

    public int getNumeroCarta() {
        return numeroCarta;
    }

    public void setNumeroCarta(int numeroCarta) {
        this.numeroCarta = numeroCarta;
    }

    public TipusCartes getCartatipusCarta() {
        return CartatipusCarta;
    }

    public void setCartatipusCarta(TipusCartes cartatipusCarta) {
        CartatipusCarta = cartatipusCarta;
    }

    public Jugadors getCartesjugador() {
        return jugadorQueTeLesCartes;
    }

    public void setCartesjugador(Jugadors cartesjugador) {
        this.jugadorQueTeLesCartes = cartesjugador;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Cartes cartes = (Cartes) o;
        return idCarta == cartes.idCarta;
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(idCarta);
    }

    @Override
    public String toString() {
        return "Cartes{" +
                "idCarta=" + idCarta +
                ", palCarta=" + palCarta +
                ", numeroCarta=" + numeroCarta +
                '}';
    }
}
