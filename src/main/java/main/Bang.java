package main;

import DAO.*;
import entity.*;
import factory.DAOFactory;
import factory.DAOFactoryImpl;
import org.hibernate.dialect.function.array.ArrayAggFunction;
import utils.Util;

import java.time.LocalDateTime;
import java.util.*;

public class Bang {

    public static void main(String[] args) {

        Carregar();
        RepartirArma();
        RepartirRol();
        RepartirJugadors();
        RepartirPersonatges();
        RepartirCartes();
        Util.close();
        System.out.println("FINAL PARTIDA");
    }

    public static void Carregar() {

        DAOFactoryImpl dao = new DAOFactoryImpl();
        DAOFactory daoFactory = dao.getFactory("MySQL");

        ICartaDAO crtDAO = (ICartaDAO) daoFactory.create("carta");
        ITipusCartaDAO tcrDAO = (ITipusCartaDAO) daoFactory.create("tipusCarta");
        IJugadorDAO jDAO = (IJugadorDAO) daoFactory.create("jugador");
        IPersonatgeDAO pDAO = (IPersonatgeDAO) daoFactory.create("personatge");
        IArmaDAO armDAO = (IArmaDAO) daoFactory.create("arma");
        IPartidaDAO partDAO = (IPartidaDAO) daoFactory.create("partida");
        IRolDAO rDAO = (IRolDAO) daoFactory.create("rol");

        Personatges bart = new Personatges("Bart Cassidy", null, 4);
        Personatges black = new Personatges("Black Jack", null, 4);
        Personatges calamity = new Personatges("Calamity Janet", null, 4);
        Personatges gringo = new Personatges("El Gringo", null, 3);
        Personatges jesse = new Personatges("Jesse Jones", null, 4);
        Personatges jourdonnais = new Personatges("Jourdonnais", null, 4);
        Personatges kit = new Personatges("Kit Carlson", null, 4);
        Personatges lucky = new Personatges("Lucky Duke", null, 4);
        Personatges paul = new Personatges("Paul Regret", null, 3);
        Personatges pedro = new Personatges("Pedro Ramírez", null, 4);
        Personatges rose = new Personatges("Rose Dolan", null, 4);
        Personatges sid = new Personatges("Sid Ketchum", null, 4);
        Personatges slab = new Personatges("Slab 'el Asesino'", null, 4);
        Personatges lucy = new Personatges("Lucy Lafayette", null, 4);
        Personatges buitre = new Personatges("'Buitre' Sam", null, 4);
        Personatges willy = new Personatges("Willy 'El Niño'", null, 4);

        pDAO.create(bart);
        pDAO.create(black);
        pDAO.create(calamity);
        pDAO.create(gringo);
        pDAO.create(jesse);
        pDAO.create(jourdonnais);
        pDAO.create(kit);
        pDAO.create(lucky);
        pDAO.create(paul);
        pDAO.create(pedro);
        pDAO.create(rose);
        pDAO.create(sid);
        pDAO.create(slab);
        pDAO.create(lucy);
        pDAO.create(buitre);
        pDAO.create(willy);

        Armes Colt45 = new Armes("Colt 45", 1);
        Armes AllenThurber = new Armes("ALLEN & THURBER PEPPERBOX 6 SHOTS", 1);
        Armes Remington = new Armes("Remington", 2);
        Armes Carabina = new Armes("Rev. Carabina", 2);
        Armes Winchester = new Armes("Winchester", 3);
        Armes Sharps = new Armes("Fusil Militar Sharps", 2);
        Armes MaresLeg = new Armes("Rifle 'Mares Leg", 1);
        armDAO.create(Colt45);
        armDAO.create(AllenThurber);
        armDAO.create(Carabina);
        armDAO.create(Remington);
        armDAO.create(Sharps);
        armDAO.create(MaresLeg);
        armDAO.create(Winchester);

        Rols rolXerif = new Rols(Rol.XERIF);
        Rols rolAjudant = new Rols(Rol.AJUDANT);
        Rols rolMalfactor = new Rols(Rol.MALFACTOR);
        Rols rolRenegat = new Rols(Rol.RENEGAT);
        rDAO.create(rolXerif);
        rDAO.create(rolAjudant);
        rDAO.create(rolRenegat);
        rDAO.create(rolMalfactor);

        Partides joc = new Partides();
        partDAO.create(joc);

        List<Cartes> cartes = new ArrayList<>();
        for (int i = 1; i < 21; i++){
            Cartes cartaP = new Cartes(Pal.PIQUES, i);
            cartes.add(cartaP);
            crtDAO.create(cartaP);

            Cartes cartaT = new Cartes(Pal.TREBOLS, i);
            cartes.add(cartaT);
            crtDAO.create(cartaT);

            Cartes cartaC = new Cartes(Pal.CORS, i);
            cartes.add(cartaC);
            crtDAO.create(cartaC);

            Cartes cartaD = new Cartes(Pal.DIAMANTS, i);
            cartes.add(cartaD);
            crtDAO.create(cartaD);
        }
        Collections.shuffle(cartes);

        TipusCartes bang = new TipusCartes(false, false, "Bang!", Color.MARRO);
        tcrDAO.create(bang);
        TipusCartes miraTelescopica = new TipusCartes(false, false, "Miratelescòpica", Color.BLAU);
        tcrDAO.create(miraTelescopica);
        TipusCartes fallat = new TipusCartes(false, false, "Has Fallat!", Color.MARRO);
        tcrDAO.create(fallat);
        TipusCartes panic = new TipusCartes(false, false, "Pànic", Color.MARRO);
        tcrDAO.create(panic);
        TipusCartes ingenua = new TipusCartes(false, false, "Ingenua", Color.MARRO);
        tcrDAO.create(ingenua);
        TipusCartes esquivar = new TipusCartes(false, false, "Esquivar", Color.BLAU);
        tcrDAO.create(esquivar);
        TipusCartes indis = new TipusCartes(false, false, "Indis", Color.BLAU);
        tcrDAO.create(indis);
        TipusCartes cervesa = new TipusCartes(false, false, "Cervesa", Color.BLAU);
        tcrDAO.create(cervesa);

        int ca = 0;
        for(Cartes carta : cartes){
            if(ca < 30) {
                bang.getCartes().add(carta);
                carta.setCartaTipusCarta(bang);
                tcrDAO.update(bang);
            }
            else {
                switch (ca%7) {
                    case 0:
                        miraTelescopica.getCartes().add(carta);
                        carta.setCartaTipusCarta(miraTelescopica);
                        tcrDAO.update(miraTelescopica);
                        break;
                    case 1:
                        fallat.getCartes().add(carta);
                        carta.setCartaTipusCarta(fallat);
                        tcrDAO.update(fallat);
                        break;
                    case 2:
                        panic.getCartes().add(carta);
                        carta.setCartaTipusCarta(panic);
                        tcrDAO.update(panic);
                        break;
                    case 3:
                        ingenua.getCartes().add(carta);
                        carta.setCartaTipusCarta(ingenua);
                        tcrDAO.update(ingenua);
                        break;
                    case 4:
                        esquivar.getCartes().add(carta);
                        carta.setCartaTipusCarta(esquivar);
                        tcrDAO.update(esquivar);
                        break;
                    case 5:
                        indis.getCartes().add(carta);
                        carta.setCartaTipusCarta(indis);
                        tcrDAO.update(indis);
                        break;
                    case 6:
                        cervesa.getCartes().add(carta);
                        carta.setCartaTipusCarta(cervesa);
                        tcrDAO.update(cervesa);
                        break;
                }
            }
            crtDAO.update(carta);
            ca++;
        }

        for(int i = 1; i < 8; i++) {
            Jugadors j = new Jugadors("Jugadors" + i);
            jDAO.create(j);
        }

        System.out.println("FINAL CARREGAR");
    }

    private static void RepartirArma() {
        DAOFactoryImpl dao = new DAOFactoryImpl();
        DAOFactory daoFactory = dao.getFactory("MySQL");
        IJugadorDAO jDAO = (IJugadorDAO) daoFactory.create("jugador");
        IArmaDAO aDAO = (IArmaDAO) daoFactory.create("arma");
        List<Jugadors> jugadorsList = jDAO.findAll();
        List<Armes> armesList = aDAO.findAll();
        Collections.shuffle(jugadorsList);

        int i=0;
        for (Jugadors j: jugadorsList){
            j.setArmaJugador(armesList.get(i));
            i++;
            jDAO.update(j);
        }
        System.out.println("FINAL REPARTIR ARMES");
    }

    private static void RepartirRol() {

        DAOFactoryImpl dao = new DAOFactoryImpl();
        DAOFactory daoFactory = dao.getFactory("MySQL");
        IJugadorDAO jDAO = (IJugadorDAO) daoFactory.create("jugador");
        IRolDAO rDAO = (IRolDAO) daoFactory.create("rol");
        List<Rols> rolsList = rDAO.findAll();
        int numJugadors = jDAO.findAll().size();
        List<Jugadors> jugadors = jDAO.findAll();
        Collections.shuffle(jugadors);

        switch (numJugadors) {
            case 4:
                for(Rols rol: rolsList){
                    if(rol.getNomRol().equals(Rol.XERIF)) {
                        jugadors.get(0).setRolJugador(rol);
                    }
                    else if(rol.getNomRol().equals(Rol.RENEGAT)) {
                        jugadors.get(1).setRolJugador(rol);
                    }
                    else if(rol.getNomRol().equals(Rol.MALFACTOR)) {
                        jugadors.get(2).setRolJugador(rol);
                        jugadors.get(3).setRolJugador(rol);
                    }
                }
                break;
            case 5:
                for(Rols rol: rolsList){
                    if(rol.getNomRol().equals(Rol.XERIF)) {
                        jugadors.get(0).setRolJugador(rol);
                    }
                    else if(rol.getNomRol().equals(Rol.RENEGAT)) {
                        jugadors.get(1).setRolJugador(rol);
                    }
                    else if(rol.getNomRol().equals(Rol.MALFACTOR)) {
                        jugadors.get(2).setRolJugador(rol);
                        jugadors.get(3).setRolJugador(rol);
                    }
                    else if(rol.getNomRol().equals(Rol.AJUDANT)) {
                        jugadors.get(4).setRolJugador(rol);
                    }
                }
                break;
            case 6:
                for(Rols rol: rolsList){
                    if(rol.getNomRol().equals(Rol.XERIF)) {
                        jugadors.get(0).setRolJugador(rol);
                    }
                    else if(rol.getNomRol().equals(Rol.RENEGAT)) {
                        jugadors.get(1).setRolJugador(rol);
                    }
                    else if(rol.getNomRol().equals(Rol.MALFACTOR)) {
                        jugadors.get(2).setRolJugador(rol);
                        jugadors.get(3).setRolJugador(rol);
                        jugadors.get(4).setRolJugador(rol);
                    }
                    else if(rol.getNomRol().equals(Rol.AJUDANT)) {
                        jugadors.get(5).setRolJugador(rol);
                    }
                }
                break;
            case 7:
                for(Rols rol: rolsList){
                    if(rol.getNomRol().equals(Rol.XERIF)) {
                        jugadors.get(0).setRolJugador(rol);
                    }
                    else if(rol.getNomRol().equals(Rol.RENEGAT)) {
                        jugadors.get(1).setRolJugador(rol);
                    }
                    else if(rol.getNomRol().equals(Rol.MALFACTOR)) {
                        jugadors.get(2).setRolJugador(rol);
                        jugadors.get(3).setRolJugador(rol);
                        jugadors.get(4).setRolJugador(rol);
                    }
                    else if(rol.getNomRol().equals(Rol.AJUDANT)) {
                        jugadors.get(5).setRolJugador(rol);
                        jugadors.get(6).setRolJugador(rol);
                    }
                }
                break;
        }
        for(Jugadors j: jugadors){
            jDAO.update(j);
        }
        System.out.println("FINAL REPARTIR ROLS");
    }

    private static void RepartirPersonatges() {
        DAOFactoryImpl dao = new DAOFactoryImpl();
        DAOFactory daoFactory = dao.getFactory("MySQL");
        IJugadorDAO jDAO=(IJugadorDAO) daoFactory.create("jugador");
        IPersonatgeDAO pDAO=(IPersonatgeDAO) daoFactory.create("personatge");
        List<Jugadors> jugadorsList=jDAO.findAll();
        List<Personatges> personatgesList=pDAO.findAll();
        Collections.shuffle(jugadorsList);
        Collections.shuffle(personatgesList);
        Random r = new Random();
        for(Jugadors j : jugadorsList){
            int randomNumber = r.nextInt(0, personatgesList.size());
            j.setPersonatgeDelJugador(personatgesList.get(randomNumber));
            personatgesList.get(randomNumber).setPersonatgeAmbJugador(j);
            pDAO.update(personatgesList.get(randomNumber));
            jDAO.update(j);
            personatgesList.remove(randomNumber);
        }

        List<Jugadors> jol=jDAO.findAll();
        System.out.println("REPARTIR PJ");
        for(Jugadors j: jol){
            System.err.println("JUGADOR: "+j);
            System.err.println("PERSONATGE JUGADOR"+ j.getPersonatgeDelJugador());
        }
        System.out.println("ACABADO REPARTIR PERSOANGE");
    }

    private static void RepartirCartes() {
        DAOFactoryImpl dao = new DAOFactoryImpl();
        DAOFactory daoFactory = DAOFactoryImpl.getFactory("MySQL");

        assert daoFactory != null;
        ICartaDAO cDAO = (ICartaDAO) daoFactory.create("carta");
        IJugadorDAO jDAO=(IJugadorDAO) daoFactory.create("jugador");
        List<Jugadors> listJugador = jDAO.findAll();
        List<Cartes> listCartes = cDAO.findAll();
        List<Cartes> listCartesSenseBang = new ArrayList<>(); //Aquesta llista és per les cartes que no tenen bang, per repartir-les
        List<Cartes> listCartesBang = new ArrayList<>(); //Per repartir aquestes segons la vida que tenen els seus personatges

        for(Cartes c : listCartes) {
            if(!c.getCartaTipusCarta().getNom().equals("Bang!"))
                listCartesSenseBang.add(c);
            else
                listCartesBang.add(c);
        }
        Collections.shuffle(listJugador);
        Collections.shuffle(listCartesSenseBang);

        for(Jugadors j: listJugador){
            System.out.println("JUGADOR: "+j);
            System.out.println("PERSONATGE JUGADOR"+ j.getPersonatgeDelJugador());
        }
        for (Jugadors j : listJugador){
            if(j.getPersonatgeDelJugador() != null) {
                for (int i = 0; i < j.getPersonatgeDelJugador().getBales(); i++) {
                    //Repartim tantes cartes BANG segons la vida del personatge
                    j.getCartes().add(listCartesBang.get(i));
                    listCartesBang.get(i).setCartesJugador(j);
                    cDAO.update(listCartesBang.get(i));
                    listCartesBang.remove(i);

                    //I també repartim les cartes restants, segons la vida dels personatges
                    j.getCartes().add(listCartesSenseBang.get(i));
                    listCartesSenseBang.get(i).setCartesJugador(j);
                    cDAO.update(listCartesSenseBang.get(i));
                    listCartesSenseBang.remove(i);
                }
                jDAO.update(j);
            }
        }
        System.out.println("FINAL REPARTIR CARTES");
    }

    public static void RepartirJugadors() {
        DAOFactoryImpl dao = new DAOFactoryImpl();
        DAOFactory daoFactory = dao.getFactory("MySQL");

        IJugadorDAO jDAO = (IJugadorDAO) daoFactory.create("jugador");
        IJugadorsRivalsDAO jrDAO=(IJugadorsRivalsDAO)daoFactory.create("jugadorRival");
        List<Jugadors> jugadorsList = jDAO.findAll();
        //Collections.shuffle(jugadorsList);

        //Obtenim les posicions segons la quantiat de jugadors que hi ha.
        int[] pos = new int [jugadorsList.size()];
        for(int i = 0; i < jugadorsList.size(); i++){
            pos[i] = i + 1;
        }

        //A cada jugador li posem la posicio.
        int i = 0;
        for(Jugadors j : jugadorsList) {
            j.setPosicio(pos[i]);
            i++;
            jDAO.update(j);
        }

        //Calculem la distància entre enemics i creem els enemics rivals
        Set<JugadorsRivals> enemics = new HashSet<>();
        int a = 0;
        for(Jugadors j : jugadorsList) {
            for(Jugadors k : jugadorsList) {
                if(!j.equals(k)) {
                    JugadorRivalsId aux = new JugadorRivalsId(k, j);
                    if(a > 0) {
                        for (JugadorsRivals jr : enemics) {
                            if (!(jr.getIdRival().getIdJugador().getIdJugador() == k.getIdJugador() && jr.getIdRival().getIdRival().getIdJugador() == j.getIdJugador())) {
                                if (Math.abs(j.getPosicio() - k.getPosicio()) < jugadorsList.size()/2) {
                                    enemics.add(new JugadorsRivals(aux, Math.abs(j.getPosicio() - k.getPosicio())));
                                } else {
                                    enemics.add(new JugadorsRivals(aux, jugadorsList.size() - Math.abs(j.getPosicio() - k.getPosicio())));
                                }
                                break;
                            }
                        }
                    }
                    else {
                        if (Math.abs(j.getPosicio() - k.getPosicio()) < jugadorsList.size()/2) {
                            enemics.add(new JugadorsRivals(aux, Math.abs(j.getPosicio() - k.getPosicio())));
                        } else {
                            enemics.add(new JugadorsRivals(aux, jugadorsList.size() - Math.abs(j.getPosicio() - k.getPosicio())));
                        }
                    }
                }
            }
            j.setJugadorsRivals(enemics);
            a++;
        }
        for(JugadorsRivals jr : enemics) {
            jrDAO.create(jr);
        }
        System.out.println("FINAL REPARTIR POSICIONES");
    }

    public static void FinalPartida(){


    }
}