package entity;

import jakarta.persistence.*;

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
    TipusCartes CartaTipusCarta;

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

    public int getIdCarta() {
        return idCarta;
    }

    public void setIdCarta(int idCarta) {
        this.idCarta = idCarta;
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

    public TipusCartes getCartaTipusCarta() {
        return CartaTipusCarta;
    }

    public void setCartaTipusCarta(TipusCartes CartaTipusCarta) {
        this.CartaTipusCarta = CartaTipusCarta;
    }

    public Jugadors getCartesJugador() {
        return jugadorQueTeLesCartes;
    }

    public void setCartesJugador(Jugadors cartesJugador) {
        this.jugadorQueTeLesCartes = cartesJugador;
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
