package entity;

import jakarta.persistence.*;

import java.io.Serializable;
import java.util.Objects;
import java.util.Set;

@Entity
@Table(name="Rol")
public class Rols implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name="idRol", updatable = false, insertable = false)
    private int idRol;

    @Enumerated(EnumType.STRING)
    @Column(name="NomRol", nullable = false)
    private Rol nomRol;

    @Column(name="Descripcio")
    private String descripcioRol;

    @OneToMany(mappedBy = "rolJugador")
    Set<Jugadors> jugador;

    public Rols() {
        super();
    }

    public Rols(Rol rol) {
        this.nomRol = rol;
    }

    public Rols(Rol nomRol, String descripcioRol) {
        this.nomRol = nomRol;
        this.descripcioRol = descripcioRol;
    }

    public int getIdRol() {
        return idRol;
    }

    public void setIdRol(int idRol) {
        this.idRol = idRol;
    }

    public Rol getNomRol() {
        return nomRol;
    }

    public void setNomRol(Rol nomRol) {
        this.nomRol = nomRol;
    }

    public String getDescripcioRol() {
        return descripcioRol;
    }

    public void setDescripcioRol(String descripcioRol) {
        this.descripcioRol = descripcioRol;
    }

    public Set<Jugadors> getJugador() {
        return jugador;
    }

    public void setJugador(Set<Jugadors> jugador) {
        this.jugador = jugador;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Rols rols = (Rols) o;
        return idRol == rols.idRol;
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(idRol);
    }

    @Override
    public String toString() {
        return "Rols{" +
                "idRol=" + idRol +
                ", nomRol=" + nomRol +
                ", descripcioRol='" + descripcioRol + '\'' +
                '}';
    }
}
