package entity;

import jakarta.persistence.*;

import java.io.Serializable;
import java.util.Arrays;
import java.util.Objects;

@Entity
@Table(name = "Personatges")
public class Personatges implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "idPersonatge")
    private int idPersonatge;

    @Column(name = "Nom", length = 20, nullable = false)
    private String nomPersonatge;

    @Column(name = "Descripcio", nullable = false)
    @Basic(optional = false, fetch = FetchType.EAGER)
    @Lob()
    private byte[] descripcioPersonatge;

    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "idJugador")
    private Jugadors personatgeAmbJugador;

    public Personatges() {
        super();
    }

    public Personatges(String nom, byte[] descripcio) {
        this.nomPersonatge = nom;
        this.descripcioPersonatge = descripcio;
    }

    public String getNom() {
        return nomPersonatge;
    }

    public void setNom(String nom) {
        this.nomPersonatge = nom;
    }

    public byte[] getDescripcio() {
        return descripcioPersonatge;
    }

    public void setDescripcio(byte[] descripcio) {
        this.descripcioPersonatge = descripcio;
    }

    public Jugadors getPersonatgeAmbJugador() {
        return personatgeAmbJugador;
    }

    public void setPersonatgeAmbJugador(Jugadors personatgeAmbJugador) {
        this.personatgeAmbJugador = personatgeAmbJugador;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Personatges that = (Personatges) o;
        return idPersonatge == that.idPersonatge;
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(idPersonatge);
    }

    @Override
    public String toString() {
        return "Personatges{" +
                "idPersonatge=" + idPersonatge +
                ", nom='" + nomPersonatge + '\'' +
                ", descripcio=" + Arrays.toString(descripcioPersonatge) +
                '}';
    }
}
