package entity;

import jakarta.persistence.*;

import java.io.Serializable;
import java.util.HashSet;
import java.util.Objects;
import java.util.Set;

@Entity
@Table(name="TipusCartes")
public class TipusCartes implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name="idTipusCarta", updatable = false, insertable = false)
    private int idTipusCartes;

    @Column(name="Nom", length = 30, nullable = false)
    private String nom;

    @Column(name="AgafarCarta", nullable = false)
    private boolean agafarCarta;

    @Column(name="DeixarCarta", nullable = false)
    private boolean deixarCarta;

    @Column(name = "PuntsVida", columnDefinition = "INT DEFAULT 0")
    private int puntsVida;

    @Column(name = "Distancia", columnDefinition = "INT DEFAULT 1")
    private int distancia;

    @Enumerated(EnumType.STRING)
    @Column(name = "Color", nullable = false)
    private Color color;

    @OneToMany(mappedBy = "CartaTipusCarta")
    Set<Cartes> cartes = new HashSet<>();

    public TipusCartes(){
        super();
    }

    public TipusCartes(boolean deixarCarta, boolean agafarCarta, String nom, Color color) {
        this.deixarCarta = deixarCarta;
        this.agafarCarta = agafarCarta;
        this.nom = nom;
        this.color = color;
    }

    public TipusCartes(String nom, boolean agafarCarta, boolean deixarCarta, int puntsVida, int distancia, Color color) {
        this.nom = nom;
        this.agafarCarta = agafarCarta;
        this.deixarCarta = deixarCarta;
        this.puntsVida = puntsVida;
        this.distancia = distancia;
        this.color = color;
    }

    public int getIdTipusCartes() {
        return idTipusCartes;
    }

    public void setIdTipusCartes(int idTipusCartes) {
        this.idTipusCartes = idTipusCartes;
    }

    public String getNom() {
        return nom;
    }

    public void setNom(String nom) {
        this.nom = nom;
    }

    public boolean isAgafarCarta() {
        return agafarCarta;
    }

    public void setAgafarCarta(boolean agafarCarta) {
        this.agafarCarta = agafarCarta;
    }

    public boolean isDeixarCarta() {
        return deixarCarta;
    }

    public void setDeixarCarta(boolean deixarCarta) {
        this.deixarCarta = deixarCarta;
    }

    public int getPuntsVida() {
        return puntsVida;
    }

    public void setPuntsVida(int puntsVida) {
        this.puntsVida = puntsVida;
    }

    public int getDistancia() {
        return distancia;
    }

    public void setDistancia(int distancia) {
        this.distancia = distancia;
    }

    public Color getColor() {
        return color;
    }

    public void setColor(Color color) {
        this.color = color;
    }

    public Set<Cartes> getCartes() {
        return cartes;
    }

    public void setCartes(Set<Cartes> cartes) {
        this.cartes = cartes;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        TipusCartes that = (TipusCartes) o;
        return idTipusCartes == that.idTipusCartes;
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(idTipusCartes);
    }

    @Override
    public String toString() {
        return "TipusCartes{" +
                "nom='" + nom + '\'' +
                ", agafarCarta=" + agafarCarta +
                ", deixarCarta=" + deixarCarta +
                ", puntsVida=" + puntsVida +
                ", distancia=" + distancia +
                ", color=" + color +
                ", idTipusCartes=" + idTipusCartes +
                '}';
    }
}
