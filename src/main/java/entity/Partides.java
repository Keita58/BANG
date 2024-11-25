package entity;

import jakarta.persistence.*;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.Objects;
import java.util.Set;

@Entity
@Table(name = "Partides")
public class Partides implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "idPartida", updatable = false, insertable = false)
    private int idPartida;

    @Column(name = "Finalitzada")
    private boolean partidaFinalitzada;

    @Column(name = "DataInici", nullable = false)
    private LocalDateTime dataIniciPartida;

    @Column(name = "DataFinal")
    private LocalDateTime dataFinalPartida;

    @ManyToMany(cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    @JoinTable(name = "partidesDelsJugadors", joinColumns = {@JoinColumn(name = "idPartida")}, inverseJoinColumns = {@JoinColumn(name = "idJugador")})
    private Set<Jugadors> partidaJugador;

    public Partides() {
        super();
        this.partidaJugador = new HashSet<>();
        this.dataIniciPartida = LocalDateTime.now();
    }

    public Partides(LocalDateTime dataInici) {
        super();
        this.dataIniciPartida = dataInici;
        this.partidaJugador = new HashSet<>();
    }

    public Partides(boolean finalitzada, LocalDateTime dataInici, LocalDateTime dataFinal) {
        super();
        this.partidaFinalitzada = finalitzada;
        this.dataIniciPartida = dataInici;
        this.dataFinalPartida = dataFinal;
        this.partidaJugador = new HashSet<>();
    }

    public int getIdPartida() {
        return idPartida;
    }

    public void setIdPartida(int idPartida) {
        this.idPartida = idPartida;
    }

    public LocalDateTime getDataInici() {
        return this.dataIniciPartida;
    }

    public void setDataInici(LocalDateTime dataInici) {
        this.dataIniciPartida = dataInici;
    }

    public boolean getPartidaFinalitzada() {
        return partidaFinalitzada;
    }

    public void setPartidaFinalitzada(boolean partidaFinalitzada) {
        this.partidaFinalitzada = partidaFinalitzada;
    }

    public LocalDateTime getDataFinalPartida() {
        return dataFinalPartida;
    }

    public void setDataFinalPartida(LocalDateTime dataFinalPartida) {
        this.dataFinalPartida = dataFinalPartida;
    }

    public Set<Jugadors> getPartidaJugador() {
        return partidaJugador;
    }

    public void setPartidaJugador(Set<Jugadors> partidaJugador) {
        this.partidaJugador = partidaJugador;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Partides partides = (Partides) o;
        return idPartida == partides.idPartida;
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(idPartida);
    }

    @Override
    public String toString() {
        return "Partides{" +
                "idPartida=" + idPartida +
                ", finalitzada=" + partidaFinalitzada +
                ", dataInici=" + dataIniciPartida +
                ", dataFinal=" + dataFinalPartida +
                '}';
    }
}
