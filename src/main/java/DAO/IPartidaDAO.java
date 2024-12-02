package DAO;

import entity.Partides;

import java.io.Serializable;

public interface IPartidaDAO extends IGenericDAO<Partides, Integer>, Serializable {
    Partides getPartidaFinal();
}
