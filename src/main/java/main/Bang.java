package main;

import DAO.*;
import entity.*;
import factory.DAOFactory;
import factory.DAOFactoryImpl;
import org.apache.commons.io.IOUtils;
import org.hibernate.dialect.function.array.ArrayAggFunction;
import org.hibernate.type.descriptor.java.ByteArrayJavaType;
import utils.Util;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.time.LocalDateTime;
import java.util.*;

public class Bang {
    static Scanner lectura;

    public static void main(String[] args) {
        lectura = new Scanner(System.in);
        Carregar();
        Menu();
        Util.close();
        System.out.println("FINAL PARTIDA");
    }

    private static void Menu() {
        System.out.println();
        System.out.println("Benvingut a Bang! Què vols fer?\n" +
                "1-> Jugar partida :)\n" +
                "2-> Veure els jugadors que tenen un personatge associat\n" +
                "3-> Finalitzar la partida\n" +
                "4-> El jugador agafa una carta\n" +
                "5-> Sortir del menu :(");
        int opcio = Integer.parseInt(lectura.nextLine());
        switch(opcio){
            case 1:
                ReiniciarValorsBBDD();
                Jugar();
                Menu();
                break;
            case 2:
                MirarPersonatgesAssociats();
                Menu();
                break;
            case 3:
                FinalPartida(7);
                Menu();
                break;
            case 4:
                AgafarCartes(new Jugadors("Provisional",1,4));
                Menu();
                break;
            default:
                break;
        }
    }

    private static void MirarPersonatgesAssociats() {
        System.out.println("Mostrant els jugadors que tenen un personatge associat!");
        DAOFactoryImpl dao = new DAOFactoryImpl();
        DAOFactory daoFactory = dao.getFactory("MySQL");
        IJugadorDAO jDAO = (IJugadorDAO) daoFactory.create("jugador");

        List<Jugadors> jlist = jDAO.getJugadorsAmbPersonatges();
        System.out.println(jlist.size());
        for (Jugadors j : jlist) {
            System.out.println("Jugador: " + j);
        }
    }

    private static void Jugar(){
        int j = 0;
        do {
            System.out.println("Quants jugadors vols a la simulació? (Entre 4 i 7 jugadors)");
            j = Integer.parseInt(lectura.nextLine());
        } while(j > 8 || j < 3);

        TornarAJugar(j);
        RepartirArma(j);
        RepartirRol(j);
        RepartirJugadors(j);
        RepartirPersonatges(j);
        RepartirCartes(j);
        BucleJoc(j);
    }

    public static void Carregar() {

        System.out.println("INICI CARREGAR");
        DAOFactoryImpl dao = new DAOFactoryImpl();
        DAOFactory daoFactory = dao.getFactory("MySQL");

        ICartaDAO crtDAO = (ICartaDAO) daoFactory.create("carta");
        ITipusCartaDAO tcrDAO = (ITipusCartaDAO) daoFactory.create("tipusCarta");
        IJugadorDAO jDAO = (IJugadorDAO) daoFactory.create("jugador");
        IPersonatgeDAO pDAO = (IPersonatgeDAO) daoFactory.create("personatge");
        IArmaDAO armDAO = (IArmaDAO) daoFactory.create("arma");
        IPartidaDAO partDAO = (IPartidaDAO) daoFactory.create("partida");
        IRolDAO rDAO = (IRolDAO) daoFactory.create("rol");
        
        Personatges bart = new Personatges("Bart Cassidy", "g5".getBytes(StandardCharsets.UTF_8), 4);
        Personatges black = new Personatges("Black Jack", "descripcio de prova".getBytes(StandardCharsets.UTF_8), 4);
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

        Partides joc = new Partides(true, LocalDateTime.now(), LocalDateTime.now());
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

        HashSet<Jugadors> ju = new HashSet<>();
        for(int i = 1; i < 8; i++) {
            Jugadors j = new Jugadors("Jugadors" + i);
            jDAO.create(j);
            j.setPartidesPropies(Set.of(joc));
            ju.add(j);
            jDAO.update(j);
        }
        joc.setPartidaJugador(ju);
        partDAO.update(joc);

        RepartirPersonatges(7);
        RepartirRol(7);
        System.out.println("FINAL CARREGAR");
    }

    private static void RepartirArma(int numJugadors) {

        System.out.println("INICI REPARTIR ARMES");
        DAOFactoryImpl dao = new DAOFactoryImpl();
        DAOFactory daoFactory = dao.getFactory("MySQL");
        IJugadorDAO jDAO = (IJugadorDAO) daoFactory.create("jugador");
        IArmaDAO aDAO = (IArmaDAO) daoFactory.create("arma");

        List<Jugadors> jugadorsList = jDAO.getNumJugadors(numJugadors);
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

    private static void RepartirRol(int numJugadors) {

        System.out.println("INICI REPARTIR ROLS");
        DAOFactoryImpl dao = new DAOFactoryImpl();
        DAOFactory daoFactory = dao.getFactory("MySQL");
        IJugadorDAO jDAO = (IJugadorDAO) daoFactory.create("jugador");
        IRolDAO rDAO = (IRolDAO) daoFactory.create("rol");

        List<Rols> rolsList = rDAO.findAll();
        List<Jugadors> jugadors = jDAO.getNumJugadors(numJugadors);
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

    private static void RepartirPersonatges(int numJugadors) {

        System.out.println("INICI REPARTIR PERSONATGE");
        DAOFactoryImpl dao = new DAOFactoryImpl();
        DAOFactory daoFactory = dao.getFactory("MySQL");
        IJugadorDAO jDAO=(IJugadorDAO) daoFactory.create("jugador");
        IPersonatgeDAO pDAO=(IPersonatgeDAO) daoFactory.create("personatge");

        List<Jugadors> jugadorsList = jDAO.getNumJugadors(numJugadors);
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

        /*
        List<Jugadors> jol=jDAO.findAll();
        System.out.println("REPARTIR PJ");
        for(Jugadors j: jol){
            System.err.println("JUGADOR: "+j);
            System.err.println("PERSONATGE JUGADOR"+ j.getPersonatgeDelJugador());
        }
        */
        System.out.println("FINAL REPARTIR PERSONATGE");
    }

    private static void RepartirCartes(int numJugadors) {

        System.out.println("INICI REPARTIR CARTES");
        DAOFactoryImpl dao = new DAOFactoryImpl();
        DAOFactory daoFactory = dao.getFactory("MySQL");

        assert daoFactory != null;
        ICartaDAO cDAO = (ICartaDAO) daoFactory.create("carta");
        IJugadorDAO jDAO=(IJugadorDAO) daoFactory.create("jugador");
        List<Jugadors> listJugador = jDAO.getNumJugadors(numJugadors);
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

        /*
        for(Jugadors j: listJugador){
            System.out.println("JUGADOR: "+j);
            System.out.println("PERSONATGE JUGADOR"+ j.getPersonatgeDelJugador());
        }*/
        for (Jugadors j : listJugador){
            if(j.getPersonatgeDelJugador() != null) {
                for (int i = 0; i < j.getPersonatgeDelJugador().getBales(); i++) {
                    //Repartim tantes cartes BANG segons la vida del personatge
                    j.getCartes().add(listCartesBang.get(0));
                    listCartesBang.get(0).setCartesJugador(j);
                    cDAO.update(listCartesBang.get(0));
                    listCartesBang.remove(0);

                    //I també repartim les cartes restants, segons la vida dels personatges
                    j.getCartes().add(listCartesSenseBang.get(0));
                    listCartesSenseBang.get(0).setCartesJugador(j);
                    cDAO.update(listCartesSenseBang.get(0));
                    listCartesSenseBang.remove(0);
                }
                jDAO.update(j);
            }
        }
        System.out.println("FINAL REPARTIR CARTES");
    }

    public static void RepartirJugadors(int numJugadors) {

        System.out.println("INICI REPARTIR POSICIONS");
        DAOFactoryImpl dao = new DAOFactoryImpl();
        DAOFactory daoFactory = dao.getFactory("MySQL");

        IJugadorDAO jDAO = (IJugadorDAO) daoFactory.create("jugador");
        IJugadorsRivalsDAO jrDAO=(IJugadorsRivalsDAO)daoFactory.create("jugadorRival");
        List<Jugadors> jugadorsList = jDAO.getNumJugadors(numJugadors);
        Collections.shuffle(jugadorsList);

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
        System.out.println("FINAL REPARTIR POSICIONS");
    }

    public static void BucleJoc(int numJugadors) {

        System.out.println("COMENÇA EL JOC");
        DAOFactory daoFactory = DAOFactoryImpl.getFactory("MySQL");
        IJugadorDAO jDAO = (IJugadorDAO) daoFactory.create("jugador");
        IPartidaDAO pDAO = (IPartidaDAO) daoFactory.create("partida");

        List<Jugadors> jList = jDAO.getNumJugadors(numJugadors);
        boolean acabarPartida = false;

        while(!acabarPartida) {
            int dolentsMorts = 0;
            for(Jugadors j : jList) {
                if (j.getPersonatgeDelJugador().getBales() <= 0 && j.getRolJugador().getNomRol() == Rol.XERIF) {
                    acabarPartida = true;
                    break;
                }
                else if(j.getPersonatgeDelJugador().getBales() <= 0) {
                    if(j.getRolJugador().getNomRol() == Rol.RENEGAT || j.getRolJugador().getNomRol() == Rol.MALFACTOR)
                        dolentsMorts++;
                    else
                        continue;
                }
                switch (jList.size())
                {
                    case 4:
                    case 5:
                        if(dolentsMorts == 3)
                            acabarPartida = true;
                        break;
                    case 6:
                    case 7:
                        if(dolentsMorts == 4)
                            acabarPartida = true;
                        break;
                }
                if (j.getPersonatgeDelJugador().getBales() > 0){
                    System.out.println("El torn és del jugador " + j.getNom() + ".");
                    AgafarCarta(j);
                    AgafarCarta(j);
                    TirarCartes(j);
                }
            }
            for(Jugadors j : jList) {
                if(j.getCartes().size() > j.getPersonatgeDelJugador().getBales()) {
                    int cartes = j.getCartes().size();
                    for(int i = 0 ; i < (Math.abs(j.getPersonatgeDelJugador().getBales() - cartes)); i++) {
                        DeixarCartes(j);
                    }
                }
            }
        }
        FinalPartida(numJugadors);
    }

    public static void AgafarCarta(Jugadors j) {

        DAOFactory daoFactory = DAOFactoryImpl.getFactory("MySQL");

        ICartaDAO cDAO = (ICartaDAO) daoFactory.create("carta");
        IJugadorDAO jDAO = (IJugadorDAO) daoFactory.create("jugador");

        Random r = new Random();
        List<Cartes> listCartes = cDAO.getCartesSenseJugador();
        int cartaRandom = r.nextInt(0, listCartes.size());
        j.getCartes().add(listCartes.get(cartaRandom));
        listCartes.get(cartaRandom).setCartesJugador(j);
        jDAO.update(j);
        cDAO.update(listCartes.get(cartaRandom));
        System.out.println("El jugador " + j.getNom() + " ha agafat una carta: "+listCartes.get(cartaRandom));
    }

    public static void AgafarCartes(Jugadors j) {

        DAOFactory daoFactory = DAOFactoryImpl.getFactory("MySQL");
        ICartaDAO cDAO = (ICartaDAO) daoFactory.create("carta");

        Random r = new Random();
        List<Cartes> listCartes = cDAO.getCartesSenseJugador();
        int cartaRandom = r.nextInt(0, listCartes.size());
        j.getCartes().add(listCartes.get(cartaRandom));
        listCartes.get(cartaRandom).setCartesJugador(j);
        System.out.println("El jugador " + j.getNom() + " ha agafat una carta: "+listCartes.get(cartaRandom));
    }


    public static void DeixarCartes(Jugadors j) {

        DAOFactory daoFactory = DAOFactoryImpl.getFactory("MySQL");
        ICartaDAO cDAO = (ICartaDAO) daoFactory.create("carta");
        IJugadorDAO jDAO = (IJugadorDAO) daoFactory.create("jugador");

        List<Cartes> cartes = cDAO.getCartesJugador(j);
        Collections.shuffle(cartes);
        if(j.getCartes().size() > 0) {
            cartes.get(0).setCartesJugador(null);
            j.getCartes().remove(cartes.get(0));
            cDAO.update(cartes.get(0));
            jDAO.update(j);
            System.out.println("El jugador " + j.getNom() + " ha deixat una carta.");
        }
        else {
            System.out.println("El jugador no té cartes per descartar :(");
        }
    }

    public static void TirarCartes(Jugadors j) {

        List<Cartes> retornar = new ArrayList<>();
        DAOFactory daoFactory = DAOFactoryImpl.getFactory("MySQL");
        ICartaDAO cDAO = (ICartaDAO) daoFactory.create("carta");
        IJugadorDAO jDAO = (IJugadorDAO) daoFactory.create("jugador");

        List<Jugadors> jugadorsAmbVida = jDAO.getJugadorsAmbPersonatgesVidaAltres(j);
        List<Cartes> cartes = cDAO.getCartesJugador(j);
        Random r = new Random();
        int cartesATirar = 0;
        if(j.getCartes().size() > 0) {
            cartesATirar = r.nextInt(1, cartes.size());
        }
        //Mirem de retornar no totes les cartes que té el jugador a la mà (per ara el nombre màxim de cartes que té menys 3)
        //Si aquesta resta donés menys de 0 el MAth.max retornaria el 0, ja que seria el nombre més gran.
        Collections.shuffle(cartes);
        System.out.println("El jugador " + j.getNom() + " jugarà " + cartesATirar + " cartes de les " + j.getCartes().size() + " cartes que té: ");

        for(int i = 0; i < cartesATirar; i++) {
            if(!cartes.get(i).getCartaTipusCarta().getNom().equals("Has Fallat!")) {
                retornar.add(cartes.get(i));
                cartes.get(i).setCartesJugador(null);
                j.getCartes().remove(cartes.get(i));
                cDAO.update(cartes.get(i));
                jDAO.update(j);
            }
        }

        boolean mort = false;
        for(Cartes c : retornar) {
            switch(c.getCartaTipusCarta().getIdTipusCartes()) {
                case 1: //BANG!
                    Collections.shuffle(jugadorsAmbVida);
                    boolean acabat = false;
                    for(Jugadors ju : jugadorsAmbVida) {
                        Set<JugadorsRivals> rivals = j.getJugadorsRivals();
                        for(JugadorsRivals jr : rivals) {
                            if(jr.getIdRival().getIdRival().getPersonatgeDelJugador().getBales() > 0 && jr.getIdRival().getIdRival().getIdJugador() == ju.getIdJugador()) {
                                if((j.getRolJugador().getNomRol() == Rol.AJUDANT && jr.getIdRival().getIdRival().getRolJugador().getNomRol() == Rol.XERIF)) {
                                    System.out.println("L'Ajudant no pot disparar el Xerif! Seria traició.");
                                }
                                else {
                                    if (j.getArmaJugador().getDistanciaArma() >= jr.getDistanciaRival()) {
                                        jr.getIdRival().getIdRival().getPersonatgeDelJugador().setBales(jr.getIdRival().getIdRival().getPersonatgeDelJugador().getBales() - 1);
                                        System.out.println("Ha jugat un BANG! contra el jugador " + ju.getNom() + "! Quina mala baba.");
                                        Set<Cartes> cartesEnemic = jr.getIdRival().getIdRival().getCartes();
                                        for(Cartes carta : cartesEnemic) {
                                            if(carta.getCartaTipusCarta().getNom().equals("Has Fallat!")) {
                                                carta.setCartesJugador(null);
                                                jr.getIdRival().getIdRival().getCartes().remove(carta);
                                                cDAO.update(carta);
                                                jr.getIdRival().getIdRival().getPersonatgeDelJugador().setBales(jr.getIdRival().getIdRival().getPersonatgeDelJugador().getBales() + 1);
                                                jDAO.update(jr.getIdRival().getIdRival());
                                                System.out.println("L'enemic tenia un Has Fallat! No ha servit de res el BANG :(");
                                                break;
                                            }
                                        }
                                        acabat = true;
                                    } else {
                                        System.out.println("Ha jugat un BANG! contra el jugador " + ju.getNom() + " però no hi arriba! Quina mala sort");
                                        acabat = true;
                                    }
                                }
                                break;
                            }
                        }
                        if(acabat)
                            break;
                    }
                    break;
                case 2: //Miratelescòpica
                    //Collections.shuffle(jugadorsAmbVida);
                    Set<JugadorsRivals> rivals = j.getJugadorsRivals();

                    for(JugadorsRivals jr : rivals) {
                        if(jr.getDistanciaRival() > 0) {
                            jr.setDistanciaRival(jr.getDistanciaRival() - 1);
                            System.out.println("Ha jugat una Miratelescòpica contra el jugador " + jr.getIdRival().getIdRival().getNom() + "! Està maquinant alguna cosa...");
                            break;
                        }
                    }
                    break;
                /*case 3: //Has fallat!
                    CurarPersonatge(j);
                    System.out.println("Ha jugat un Has Fallat! i s'ha curat! Que bé que es deu sentir amb una vida més.");
                    break;*/
                case 4: //Pànic
                    AgafarCarta(j);
                    System.out.println("Ha jugat un Pànic!. Necessitava més cartes.");
                    break;
                case 5: //Ingenua
                    DeixarCartes(j);
                    System.out.println("Ha jugat una Ingenua. No deu tenir bones cartes...");
                    break;
                case 6: //Esquivar
                    CurarPersonatge(j);
                    AgafarCarta(j);
                    System.out.println("Ha tirat un Esquivar. Deu estar en les últimes...");
                    break;
                case 7: //Indis
                    j.getPersonatgeDelJugador().setBales(j.getPersonatgeDelJugador().getBales() - 1);
                    System.out.println("Ha jugat un Indis. S'ha fet un tret als peus.");
                    if(j.getPersonatgeDelJugador().getBales() == 0)
                        mort = true;
                    break;
                case 8: //Cervesa
                    CurarPersonatge(j);
                    System.out.println("Ha tirat una Cervesa. Mai va malament emborratxar-se una mica.");
                    break;
            }
            if(mort)
                break;
        }
    }

    private static void CurarPersonatge(Jugadors j) {
        if(!(j.getPersonatgeDelJugador().getNom().equals("El Gringo") || j.getPersonatgeDelJugador().getNom().equals("Paul Regret"))) {
            if(j.getPersonatgeDelJugador().getBales() < 4)
                j.getPersonatgeDelJugador().setBales(j.getPersonatgeDelJugador().getBales() + 1);
        }
        else {
            if(j.getPersonatgeDelJugador().getBales() < 3)
                j.getPersonatgeDelJugador().setBales(j.getPersonatgeDelJugador().getBales() + 1);
        }
    }

    public static void FinalPartida(int numJugadors){

        DAOFactoryImpl dao = new DAOFactoryImpl();
        DAOFactory daoFactory = dao.getFactory("MySQL");
        IPartidaDAO partDAO = (IPartidaDAO) daoFactory.create("partida");
        IJugadorDAO jDao = (IJugadorDAO) daoFactory.create("jugador");

        Partides partida = partDAO.getPartidaFinal();
        List<Jugadors> jList = jDao.getNumJugadors(numJugadors);

        for (Jugadors j : jList) {
            if (j.getPersonatgeDelJugador() != null && j.getPersonatgeDelJugador().getBales() > 0) {
                j.setGuanyats(j.getGuanyats()+1);
                System.out.println("Ha guanyat: "+j.getNom()+" amb el rol: "+j.getRolJugador().getNomRol()+"!!");
                jDao.update(j);
            }
        }

        if(partida != null) {
            partida.setPartidaFinalitzada(true);
            partDAO.update(partida);
        }
        System.out.println("ACABA EL JOC");
    }

    public static void ReiniciarValorsBBDD() {
        DAOFactoryImpl dao = new DAOFactoryImpl();
        DAOFactory daoFactory = dao.getFactory("MySQL");
        ICartaDAO cDAO = (ICartaDAO) daoFactory.create("carta");
        IJugadorDAO jDao = (IJugadorDAO) daoFactory.create("jugador");
        IPersonatgeDAO pDao = (IPersonatgeDAO) daoFactory.create("personatge");
        IArmaDAO aDao=(IArmaDAO)daoFactory.create("arma");
        IJugadorsRivalsDAO jrDAO=(IJugadorsRivalsDAO) daoFactory.create("jugadorRival");
        List<Cartes> cartesList=cDAO.findAll();
        List<Jugadors> listJugadors = jDao.findAll();
        List<Personatges> listPersonatges = pDao.findAll();
        List<Armes> armesList= aDao.findAll();
        List<JugadorsRivals> jugadorsRivalsList=jrDAO.findAll();

        for (Cartes c : cartesList) {
            c.setCartesJugador(null);
            cDAO.update(c);
        }

        for(Jugadors j : listJugadors){
            j.setArmaJugador(null);
            j.setPersonatgeDelJugador(null);
            j.setCartes(null);
            jDao.update(j);
        }

        for(Personatges p : listPersonatges) {
            p.setPersonatgeAmbJugador(null);
            pDao.update(p);
        }

        for(Armes a : armesList){
            a.setJugador(null);
            aDao.update(a);
        }

        //drop taula jugadors rivals per poder fer més d'una partida

    }

    public static void TornarAJugar(int jugadors) {

        DAOFactoryImpl dao = new DAOFactoryImpl();
        DAOFactory daoFactory = dao.getFactory("MySQL");
        IPartidaDAO pDAO = (IPartidaDAO) daoFactory.create("partida");
        IJugadorDAO jDAO = (IJugadorDAO) daoFactory.create("jugador");

        List<Jugadors> jList = jDAO.getNumJugadors(jugadors);
        //jDAO.delete(jList.get(0).getIdJugador()); //Això esborra tots els jugadors de la base de dades

        Partides joc = new Partides();
        HashSet<Jugadors> jugadorsSet = new HashSet<>();
        for(Jugadors j : jList) {
            j.setPartidesPropies(Set.of(joc));
            jugadorsSet.add(j);
            jDAO.update(j);
        }
        joc.setPartidaJugador(jugadorsSet);
        pDAO.update(joc);
    }
}