package entity;

import jakarta.persistence.*;

import java.io.Serializable;
import java.util.Objects;

@Entity
@Table(name="Armes")
public class Armes implements Serializable{

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "idArma", updatable = false, insertable = false)
    private int idArma;

    @Column(name = "nomArma", nullable = false, length = 70)
    private String nomArma;

    @Column(name = "descripcioArma", nullable = true, length = 255)
    private String descripcioArma;

    @Column(name = "Distancia", nullable = false ,columnDefinition = "INT DEFAULT 0")
    private int distanciaArma;

    @OneToOne(mappedBy = "armaJugador", cascade = CascadeType.ALL)
    private Jugadors jugador;

    public Armes() {
        super();
    }

    public Armes(String nomArma, int distanciaArma) {
        this.nomArma = nomArma;
        this.distanciaArma = distanciaArma;
    }

    public Armes(String nomArma, String descripcioArma, int distanciaArma) {
        this.nomArma = nomArma;
        this.descripcioArma = descripcioArma;
        this.distanciaArma = distanciaArma;
    }
    
    public int getIdArma() {
        return idArma;
    }

    public void setIdArma(int idArma) {
        this.idArma = idArma;
    }

    public String getNomArma() {
        return nomArma;
    }

    public void setNomArma(String nomArma) {
        this.nomArma = nomArma;
    }

    public String getDescripcioArma() {
        return descripcioArma;
    }

    public void setDescripcioArma(String descripcioArma) {
        this.descripcioArma = descripcioArma;
    }

    public int getDistanciaArma() {
        return distanciaArma;
    }

    public void setDistanciaArma(int distanciaArma) {
        this.distanciaArma = distanciaArma;
    }

    public Jugadors getJugador() {
        return jugador;
    }

    public void setJugador(Jugadors jugador) {
        this.jugador = jugador;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Armes armes = (Armes) o;
        return idArma == armes.idArma;
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(idArma);
    }

    @Override
    public String toString() {
        return "Armes{" +
                "armaId=" + idArma +
                ", nomArma='" + nomArma + '\'' +
                ", descripcioArma='" + descripcioArma + '\'' +
                ", distanciaArma=" + distanciaArma +
                '}';
    }
}
