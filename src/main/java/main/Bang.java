package main;

import DAO.*;
import entity.*;
import factory.DAOFactory;
import factory.DAOFactoryImpl;
import utils.Util;

import java.time.LocalDateTime;
import java.util.Set;

public class Bang {

    public static void main(String[] args) {

        Cartes carta1 = new Cartes(Pal.TREBOLS, 10);
        TipusCartes tipusCartes1 = new TipusCartes(false, false, "Bang!", Color.MARRO);
        Jugadors jugador1 = new Jugadors("Eloi");
        Jugadors jugador2 = new Jugadors("Marc");
        Armes arma1 = new Armes("Examen Sorpresa", 3);
        Partides partida1 = new Partides(LocalDateTime.now());
        Rols rol1 = new Rols(Rol.XERIF);

        DAOFactoryImpl dao = new DAOFactoryImpl();
        DAOFactory daoFactory = dao.getFactory("MySQL");

        ICartaDAO crtDAO=(ICartaDAO) daoFactory.create("carta");
        ITipusCartaDAO tcr=(ITipusCartaDAO) daoFactory.create("tipusCarta");
        IJugadorDAO jDAO=(IJugadorDAO) daoFactory.create("jugador");
        IArmaDAO armDAO=(IArmaDAO) daoFactory.create("arma");
        IPartidaDAO partDAO=(IPartidaDAO) daoFactory.create("partida");
        IRolDAO rDAO=(IRolDAO) daoFactory.create("rol");
        IJugadorsRivalsDAO jrDAO=(IJugadorsRivalsDAO)daoFactory.create("jugadorRival");

        crtDAO.create(carta1);
        tcr.create(tipusCartes1);
        jDAO.create(jugador1);
        jDAO.create(jugador2);
        armDAO.create(arma1);
        partDAO.create(partida1);
        rDAO.create(rol1);

        JugadorRivalsId jr = new JugadorRivalsId(jugador2, jugador1);
        JugadorsRivals jugadorsRivals= new JugadorsRivals(jr, 2);
        jrDAO.create(jugadorsRivals);

        Util.close();
        System.out.println("FINAL...");
    }
}