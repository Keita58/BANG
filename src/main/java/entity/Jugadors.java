package entity;

import jakarta.persistence.*;

import java.io.Serializable;
import java.util.HashSet;
import java.util.Objects;
import java.util.Set;

@Entity
@Table(name="Jugador")
public class Jugadors implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name="idJugador", insertable = false,updatable = false)
    private int idJugador;

    @Column(name="Nom", length = 30, nullable = false)
    private String nomJugador;

    @Column(name="Guanyats", columnDefinition = "INT DEFAULT 0")
    private int guanyats;

    @Column(name="Posicio",columnDefinition = "INT DEFAULT 0")
    private int posicioJugador;

    @OneToOne
    @JoinColumn(name="idArma")
    Armes armaJugador;

    @ManyToOne
    @JoinColumn(name = "idRol")
    Rols rolJugador;

    @OneToOne(mappedBy = "personatgeAmbJugador", cascade = CascadeType.ALL)
    private Personatges personatgeDelJugador;

    @OneToMany(mappedBy = "jugadorQueTeLesCartes")
    private Set<Cartes> cartes;

    @OneToMany(cascade = CascadeType.ALL, fetch = FetchType.EAGER, mappedBy = "id.idJugador")
    Set<JugadorsRivals> jugadorsRivals;

    @ManyToMany(cascade = CascadeType.ALL, mappedBy = "partidaJugador", fetch = FetchType.EAGER)
    private Set<Partides> partidesPropies;

    public Jugadors() {
        super();
        this.cartes = new HashSet<>();
    }

    public Jugadors(String nom) {
        this.nomJugador = nom;
        this.cartes = new HashSet<>();
    }

    public Jugadors(String nom, int guanyats, int posicio) {
        this.nomJugador = nom;
        this.guanyats = guanyats;
        this.posicioJugador = posicio;
        jugadorsRivals= new HashSet<>();
        partidesPropies= new HashSet<>();
        this.cartes = new HashSet<>();
    }

    public int getIdJugador() {
        return idJugador;
    }


    public String getNom() {
        return nomJugador;
    }

    public void setNom(String nom) {
        this.nomJugador = nom;
    }

    public int getGuanyats() {
        return guanyats;
    }

    public void setGuanyats(int guanyats) {
        this.guanyats = guanyats;
    }

    public int getPosicio() {
        return posicioJugador;
    }

    public void setPosicio(int posicio) {
        this.posicioJugador = posicio;
    }

    public Armes getArmaJugador() {
        return armaJugador;
    }

    public void setArmaJugador(Armes armaJugador) {
        this.armaJugador = armaJugador;
    }

    public Rols getRolJugador() {
        return rolJugador;
    }

    public void setRolJugador(Rols rolJugador) {
        this.rolJugador = rolJugador;
    }

    public Personatges getPersonatgeDelJugador() {
        return personatgeDelJugador;
    }

    public void setPersonatgeDelJugador(Personatges personatgeDelJugador) {
        this.personatgeDelJugador = personatgeDelJugador;
    }

    public Set<Cartes> getCartes() {
        return cartes;
    }

    public void setCartes(Set<Cartes> cartes) {
        this.cartes = cartes;
    }

    public Set<JugadorsRivals> getJugadorsRivals() {
        return jugadorsRivals;
    }

    public void setJugadorsRivals(Set<JugadorsRivals> jugadorsRivals) {
        this.jugadorsRivals = jugadorsRivals;
    }

    public Set<Partides> getPartidesPropies() {
        return partidesPropies;
    }

    public void setPartidesPropies(Set<Partides> partidesPropies) {
        this.partidesPropies = partidesPropies;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Jugadors jugadors = (Jugadors) o;
        return idJugador == jugadors.idJugador;
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(idJugador);
    }

    @Override
    public String toString() {
        return "Jugadors{" +
                "idJugador=" + idJugador +
                ", nom='" + nomJugador + '\'' +
                ", guanyats=" + guanyats +
                ", posicio=" + posicioJugador +
                '}';
    }
}
