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

    /**
     * Main del joc on cridem les funcions principals {@link #Carregar()} i {@link #Menu()}.
     */
    public static void main(String[] args) {
        lectura = new Scanner(System.in);
        Carregar();
        Menu();
        Util.close();
        System.out.println("FINAL PARTIDA");
    }

    /**
     * Menu del joc on creem les diferents opcions pel jugador i des d'on
     * cridarem a la resta de funcions del joc.
     */
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
            case 5:
                break;
            default:
                System.out.println("No has escollit una de les opcions del menú");
                Menu();
                break;
        }
    }

    /**
     * Funció que ens imprimeix per pantalla els personatges que
     * té cada jugador existent a la base de dades.
     */
    private static void MirarPersonatgesAssociats() {
        System.out.println("Mostrant els jugadors que tenen un personatge associat!");
        DAOFactory daoFactory = DAOFactoryImpl.getFactory("MySQL");

        IJugadorDAO jDAO = (IJugadorDAO) daoFactory.create("jugador");

        List<Jugadors> jlist = jDAO.getJugadorsAmbPersonatges();
        System.out.println(jlist.size());
        for (Jugadors j : jlist) {
            System.out.println("Jugador: " + j);
        }
    }

    /**
     * Funció prèvia al bucle del joc que ens prepara el joc per poder jugar.
     * Demana al jugador quants jugadors hi haurà a la partida i inicialitza totes les
     * funcions necessàries per poder iniciar una partida correctament.
     */
    private static void Jugar(){
        int j = 0;
        do {
            System.out.println("Quants jugadors vols a la simulació? (Entre 4 i 7 jugadors)");
            j = Integer.parseInt(lectura.nextLine());
        } while(j > 7 || j < 4);

        TornarAJugar(j);
        RepartirArma(j);
        RepartirRol(j);
        RepartirJugadors(j);
        RepartirPersonatges(j);
        RepartirCartes(j);
        BucleJoc(j);
    }

    /**
     * Carrega totes les dades indispensables del joc a la base de dades.
     */
    public static void Carregar() {
        System.out.println("INICI CARREGAR");
        DAOFactory daoFactory = DAOFactoryImpl.getFactory("MySQL");

        // Creem tots el DAO que necessitem per a la càrrega de dades a la base de dades.
        ICartaDAO crtDAO = (ICartaDAO) daoFactory.create("carta");
        ITipusCartaDAO tcrDAO = (ITipusCartaDAO) daoFactory.create("tipusCarta");
        IJugadorDAO jDAO = (IJugadorDAO) daoFactory.create("jugador");
        IPersonatgeDAO pDAO = (IPersonatgeDAO) daoFactory.create("personatge");
        IArmaDAO armDAO = (IArmaDAO) daoFactory.create("arma");
        IPartidaDAO partDAO = (IPartidaDAO) daoFactory.create("partida");
        IRolDAO rDAO = (IRolDAO) daoFactory.create("rol");

        // Creem tots els personatges i els afegim.
        Personatges bart = new Personatges("Bart Cassidy", "g5".getBytes(StandardCharsets.UTF_8), 4);
        Personatges black = new Personatges("Black Jack", "descripcio de prova".getBytes(StandardCharsets.UTF_8), 4);
        Personatges calamity = new Personatges("Calamity Janet", "Soc en Calamity Janet".getBytes(StandardCharsets.UTF_8), 4);
        Personatges gringo = new Personatges("El Gringo", "Soc en Gringo".getBytes(StandardCharsets.UTF_8), 3);
        Personatges jesse = new Personatges("Jesse Jones", "Soc en Jesse Jones".getBytes(StandardCharsets.UTF_8), 4);
        Personatges jourdonnais = new Personatges("Jourdonnais", "Soc en Jourdonnais".getBytes(StandardCharsets.UTF_8), 4);
        Personatges kit = new Personatges("Kit Carlson", "Soc en Kit Carlson".getBytes(StandardCharsets.UTF_8), 4);
        Personatges lucky = new Personatges("Lucky Duke", "Soc en Lucky Duke".getBytes(StandardCharsets.UTF_8), 4);
        Personatges paul = new Personatges("Paul Regret", "Soc en Paul Regret".getBytes(StandardCharsets.UTF_8), 3);
        Personatges pedro = new Personatges("Pedro Ramírez", "Soc en Pedro Ramirez".getBytes(StandardCharsets.UTF_8), 4);
        Personatges rose = new Personatges("Rose Dolan", "Soc na Rose Dolan".getBytes(StandardCharsets.UTF_8), 4);
        Personatges sid = new Personatges("Sid Ketchum", "Soc en Sid Ketchum".getBytes(StandardCharsets.UTF_8), 4);
        Personatges slab = new Personatges("Slab 'el Asesino'", "Soc en Slab".getBytes(StandardCharsets.UTF_8), 4);
        Personatges lucy = new Personatges("Lucy Lafayette", "Soc na Lucy Lafayette".getBytes(StandardCharsets.UTF_8), 4);
        Personatges buitre = new Personatges("'Buitre' Sam", "Soc en Sam".getBytes(StandardCharsets.UTF_8), 4);
        Personatges willy = new Personatges("Willy 'El Niño'", "Soc en Willy".getBytes(StandardCharsets.UTF_8), 4);
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

        // Creem totes les armes del joc i les carreguem a la base de dades.
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

        // Creem els rols i els afegim.
        Rols rolXerif = new Rols(Rol.XERIF);
        Rols rolAjudant = new Rols(Rol.AJUDANT);
        Rols rolMalfactor = new Rols(Rol.MALFACTOR);
        Rols rolRenegat = new Rols(Rol.RENEGAT);
        rDAO.create(rolXerif);
        rDAO.create(rolAjudant);
        rDAO.create(rolRenegat);
        rDAO.create(rolMalfactor);

        // Creem una partida per una de les proves del menú.
        Partides joc = new Partides(true, LocalDateTime.now(), LocalDateTime.now());
        partDAO.create(joc);

        // He canviat el total de cartes que hi havia per aquest, que en surten 112, ja que demana 30 de bang i 80 de la resta.
        List<Cartes> cartes = new ArrayList<>();
        for (int i = 1; i < 28; i++){
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

        // Creem tots els tipus de cartes del joc i els afegim.
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

        // A cada carta creada anteriorment li assignem un tipus de carta.
        // Del total de cartes, 30 seran exclusivament BANG!, mentre que la resta (80) s'assignaran per l'ordre mostrat.
        // (A causa del nombre de tipus de cartes i les cartes existents a la baralla, existiran més cartes de miratelescòpica,
        // fallat i pànic que la resta, només una de més per cada).
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

        // Creem tots els jugadors a la base de dades, els quals anirem escollint
        // aleatòriament per jugar.
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

    /**
     * Repartim una arma de totes les existents a la base de dades de manera aleatòria a cada un dels jugadors
     * actius de la partida actual (cap dels jugadors tindrà una arma repetida, tots tindran armes úniques).
     * @param numJugadors Nombre de jugadors que jugaran al BANG! (s'especifica a la funció {@link #Jugar()}).
     */
    private static void RepartirArma(int numJugadors) {
        System.out.println("INICI REPARTIR ARMES");
        DAOFactory daoFactory = DAOFactoryImpl.getFactory("MySQL");

        IJugadorDAO jDAO = (IJugadorDAO) daoFactory.create("jugador");
        IArmaDAO aDAO = (IArmaDAO) daoFactory.create("arma");

        List<Jugadors> jugadorsList = jDAO.getNumJugadors(numJugadors);
        List<Armes> armesList = aDAO.findAll();
        Collections.shuffle(jugadorsList);

        int i = 0;
        for (Jugadors j: jugadorsList){
            j.setArmaJugador(armesList.get(i));
            i++;
            jDAO.update(j);
        }
        System.out.println("FINAL REPARTIR ARMES");
    }

    /**
     * Repartim els rols del joc als jugadors que participen en la partida actual.
     * Depenent del nombre de jugadors repartirem diferents rols.
     * @param numJugadors Nombre de jugadors que jugaran al BANG! (s'especifica a la funció {@link #Jugar()}).
     */
    private static void RepartirRol(int numJugadors) {
        System.out.println("INICI REPARTIR ROLS");
        DAOFactory daoFactory = DAOFactoryImpl.getFactory("MySQL");

        IJugadorDAO jDAO = (IJugadorDAO) daoFactory.create("jugador");
        IRolDAO rDAO = (IRolDAO) daoFactory.create("rol");

        List<Rols> rolsList = rDAO.findAll();
        List<Jugadors> jugadors = jDAO.getNumJugadors(numJugadors);
        // Fent un shuffle de la llista de jugadors ens estalviem problemes de repetició de rols.
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

    /**
     * Repartim un personatge a cada jugador de la partida actual.
     * Aquest personatge serà un aleatori (que no tingui cap altre jugador) d'entre tots els existents a la base de dades.
     * @param numJugadors Nombre de jugadors que jugaran al BANG! (s'especifica a la funció {@link #Jugar()}).
     */
    private static void RepartirPersonatges(int numJugadors) {
        System.out.println("INICI REPARTIR PERSONATGE");
        DAOFactory daoFactory = DAOFactoryImpl.getFactory("MySQL");

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
        System.out.println("FINAL REPARTIR PERSONATGE");
    }

    /**
     * Repartim cartes de la base de dades als jugadors participants depenent de la vida del seu personatge.<br>
     * Es reparteixen el mateix nombre de cartes BANG que vides té el personatge, i el mateix nombre de cartes de la resta
     * de tipus de cartes de manera aleatòria.
     * @param numJugadors Nombre de jugadors que jugaran al BANG! (s'especifica a la funció {@link #Jugar()}).
     */
    private static void RepartirCartes(int numJugadors) {
        System.out.println("INICI REPARTIR CARTES");
        DAOFactory daoFactory = DAOFactoryImpl.getFactory("MySQL");

        ICartaDAO cDAO = (ICartaDAO) daoFactory.create("carta");
        IJugadorDAO jDAO=(IJugadorDAO) daoFactory.create("jugador");
        List<Jugadors> listJugador = jDAO.getNumJugadors(numJugadors);
        List<Cartes> listCartes = cDAO.findAll();
        // Llista de les cartes BANG de la base de dades
        List<Cartes> listCartesSenseBang = new ArrayList<>();
        // Per repartir aquestes segons la vida que tenen els seus personatges
        List<Cartes> listCartesBang = new ArrayList<>();

        for(Cartes c : listCartes) {
            if(!c.getCartaTipusCarta().getNom().equals("Bang!"))
                listCartesSenseBang.add(c);
            else
                listCartesBang.add(c);
        }
        Collections.shuffle(listJugador);
        Collections.shuffle(listCartesSenseBang);

        for (Jugadors j : listJugador){
            if(j.getPersonatgeDelJugador() != null) {
                for (int i = 0; i < j.getPersonatgeDelJugador().getBales(); i++) {
                    //Repartim tantes cartes BANG com vida té el personatge
                    j.getCartes().add(listCartesBang.get(0));
                    listCartesBang.get(0).setCartesJugador(j);
                    cDAO.update(listCartesBang.get(0));
                    listCartesBang.remove(0);

                    //I repartim les cartes restants de la mateixa manera
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

    /**
     * Repartim els jugadors a una "taula" imaginària per poder determinar les distàncies entre ells.
     * Aquesta distància és la que utilitzarem per saber si un jugador pot disparar a un altre.
     * Les distàncies les guardem a una taula anomenada JugadorsRivals i on hi haurà també
     * @param numJugadors Nombre de jugadors que jugaran al BANG! (s'especifica a la funció {@link #Jugar()}).
     */
    public static void RepartirJugadors(int numJugadors) {
        System.out.println("INICI REPARTIR POSICIONS");
        DAOFactory daoFactory = DAOFactoryImpl.getFactory("MySQL");

        IJugadorDAO jDAO = (IJugadorDAO) daoFactory.create("jugador");
        IJugadorsRivalsDAO jrDAO = (IJugadorsRivalsDAO) daoFactory.create("jugadorRival");
        List<Jugadors> jugadorsList = jDAO.getNumJugadors(numJugadors);
        Collections.shuffle(jugadorsList);

        // Obtenim les posicions segons la quantitat de jugadors que hi ha.
        int[] pos = new int [jugadorsList.size()];
        for(int i = 0; i < jugadorsList.size(); i++){
            pos[i] = i + 1;
        }

        // A cada jugador li posem la posicio.
        int i = 0;
        for(Jugadors j : jugadorsList) {
            j.setPosicio(pos[i]);
            i++;
            jDAO.update(j);
        }

        // Calculem la distància entre els jugadors i creem els enemics rivals
        Set<JugadorsRivals> enemics = new HashSet<>();
        int a = 0;
        for(Jugadors j : jugadorsList) {
            for(Jugadors k : jugadorsList) {
                // Creem dos bucles, per parsejar tots els jugadors entre ells.
                if(!j.equals(k)) {
                    // Si els jugadors són diferents (no és un jugador contras si mateix) creem el valor aux de JugadorRivalsId.
                    JugadorRivalsId aux = new JugadorRivalsId(k, j);
                    if(a > 0) {
                        // Mirem dins del set creat abans per si ja existeix la dupla d'enemics que estem intentant afegir.
                        for (JugadorsRivals jr : enemics) {
                            // Aquí comprovem si el JugadorRivals existent no és el mateix que volem afegir però en l'ordre invers. Si ho és passem al següent.
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
                        // Aquí només entrarem la primera vegada amb el primer jugador, perquè no hi haurà cap valor al set en haver-lo acabat de crear.
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

    /**
     * Bucle principal del joc. Aquí gestionem els torns dels jugadors i els casos de finalització de la partida.
     * @param numJugadors Nombre de jugadors que jugaran al BANG! (s'especifica a la funció {@link #Jugar()}).
     */
    public static void BucleJoc(int numJugadors) {
        System.out.println("COMENÇA EL JOC");
        DAOFactory daoFactory = DAOFactoryImpl.getFactory("MySQL");

        IJugadorDAO jDAO = (IJugadorDAO) daoFactory.create("jugador");

        // Llista de tots els jugadors actuals de la partida.
        List<Jugadors> jList = jDAO.getNumJugadors(numJugadors);
        // Llista dels jugadors dolents morts (Malfactors i Renegats). Per evitar contar-los més d'una vegada.
        List<Jugadors> jMorts = new ArrayList<>();
        boolean acabarPartida = false;

        while(!acabarPartida) {
            int dolentsMorts = 0;
            for(Jugadors j : jList) {
                // Agafem els jugadors vius actuals per comprovar que no estigui jugant un jugador sol o si es dona el cas que estan vius el xèrif i l'ajudant.
                List<Jugadors> jAmbVida = jDAO.getNumJugadorsAmbVida(numJugadors);
                if(jAmbVida.size() > 1){
                    if(jAmbVida.size() == 2) {
                        // Si hi ha dos jugadors vius i són el xèrif i l'ajudant s'acaba el joc
                        if((jAmbVida.get(0).getRolJugador().getNomRol() == Rol.XERIF || jAmbVida.get(0).getRolJugador().getNomRol() == Rol.AJUDANT) &&
                                (jAmbVida.get(1).getRolJugador().getNomRol() == Rol.XERIF || jAmbVida.get(1).getRolJugador().getNomRol() == Rol.AJUDANT)) {
                            acabarPartida = true;
                            break;
                        }
                    }
                    //Si el jugador mort és el xèrif s'acaba el joc
                    if (j.getPersonatgeDelJugador().getBales() == 0 && j.getRolJugador().getNomRol() == Rol.XERIF) {
                        acabarPartida = true;
                        break;
                    }
                    else if(j.getPersonatgeDelJugador().getBales() == 0) {
                        boolean trobat = false;
                        for(Jugadors jM : jMorts){
                            // Si el rol del jugador mort és cap dels dolents i aquest no està dins la llista dels morts l'afegim i el contem.
                            if(jM.equals(j)) {
                                trobat = true;
                                break;
                            }
                        }
                        if(!trobat) {
                            jMorts.add(j);
                        }
                        else {
                            // Si hi ha un jugador mort hem de tornar a contar les distàncies dels nous jugadors
                            CalcularDistancia(numJugadors);
                            trobat = false;
                        }
                        if(j.getRolJugador().getNomRol() == Rol.RENEGAT || j.getRolJugador().getNomRol() == Rol.MALFACTOR) {
                            for(Jugadors jM : jMorts){
                                // Si el rol del jugador mort és cap dels dolents i aquest no està dins la llista dels morts l'afegim i el contem.
                                if(jM.equals(j)) {
                                    trobat = true;
                                    break;
                                }
                            }
                        }
                        else
                            continue;
                        // Si no ha trobat l'enemic dins la llista de morts l'afegeix i augmenta en 1 la variable dolentsMorts.
                        if(!trobat) {
                            dolentsMorts++;
                            jMorts.add(j);
                        }
                    }
                    // Depenent del nombre de jugadors acabarem la partida si han mort 3 o 4 dolents (és a dir, que han mort tots).
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
                    // Torn d'un jugador: Agafa dues cartes i tira un nombre aleatori de cartes de les quals té a la mà actualment.
                    if (j.getPersonatgeDelJugador().getBales() > 0) {
                        System.out.println("El torn és del jugador " + j.getNom() + ". Té actualment " + j.getPersonatgeDelJugador().getBales() + " vides.");
                        AgafarCarta(j);
                        AgafarCarta(j);
                        TirarCartes(j);
                    }
                }
                else {
                    acabarPartida = true;
                    break;
                }
            }
            // Si no hem acabat la partida obliguem els jugadors a retirar cartes de la seva mà fins a tenir-ne les mateixes que la vida del seu personatge.
            // Les cartes que selecciona per retirar són aleatòries.
            if(!acabarPartida) {
                for(Jugadors j : jList) {
                    if(j.getPersonatgeDelJugador().getBales() > 0 && j.getCartes().size() > j.getPersonatgeDelJugador().getBales()) {
                        int cartes = j.getCartes().size();
                        for(int i = 0 ; i < (Math.abs(j.getPersonatgeDelJugador().getBales() - cartes)); i++) {
                            DeixarCartes(j);
                        }
                    }
                }
            }

        }
        FinalPartida(numJugadors);
    }

    /**
     * Calcula la distància entre els jugadors. Fa el mateix que {@link #RepartirJugadors(int)} però només la part de la distància,
     * ja que aquesta funció la utilitzem per recalcular les distàncies entre els jugadors vius de la partida actual.
     * @param numJugadors Nombre de jugadors que jugaran al BANG! (s'especifica a la funció {@link #Jugar()}).
     */
    public static void CalcularDistancia(int numJugadors) {
        DAOFactory daoFactory = DAOFactoryImpl.getFactory("MySQL");

        IJugadorDAO jDAO = (IJugadorDAO) daoFactory.create("jugador");
        IJugadorsRivalsDAO jrDAO = (IJugadorsRivalsDAO) daoFactory.create("jugadorRival");

        //Calculem la distància entre enemics vius restatns i creem els enemics rivals
        List<Jugadors> jugadorsList = jDAO.getNumJugadorsAmbVida(numJugadors);
        Set<JugadorsRivals> enemics = new HashSet<>();
        int a = 0;
        for(Jugadors j : jugadorsList) {
            for(Jugadors k : jugadorsList) {
                // Creem dos bucles, per parsejar tots els jugadors entre ells.
                if(!j.equals(k)) {
                    // Si els jugadors són diferents (no és un jugador contras si mateix) creem el valor aux de JugadorRivalsId.
                    JugadorRivalsId aux = new JugadorRivalsId(k, j);
                    if(a > 0) {
                        // Mirem dins del set creat abans per si ja existeix la dupla d'enemics que estem intentant afegir.
                        for (JugadorsRivals jr : enemics) {
                            // Aquí comprovem si el JugadorRivals existent no és el mateix que volem afegir però en l'ordre invers. Si ho és passem al següent.
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
                        // Aquí només entrarem la primera vegada amb el primer jugador, perquè no hi haurà cap valor al set en haver-lo acabat de crear.
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
            jrDAO.update(jr);
        }
    }

    /**
     * Agafa una carta de la pila de cartes de la base de dades de manera aleatòria.
     * @param j Jugador que agafa la carta de la base de dades.
     */
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

    /**
     * Funció que se'ns demana al {@link #Menu()}. Només realitza l'acció d'agafar una carta de la base de dades,
     * però no guarda cap mena d'informació al jugador ni a la carta, ja que és només simbòlic.
     * @param j Jugador que agafa la carta de la base de dades.
     */
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

    /**
     * Elimina una carta aleatòria de la mà del jugador que es passa per paràmetre.
     * Aquesta carta passa a estar a la pila de robar automàticament.
     * @param j Jugador que descarta la carta
     */
    public static void DeixarCartes(Jugadors j) {
        DAOFactory daoFactory = DAOFactoryImpl.getFactory("MySQL");

        ICartaDAO cDAO = (ICartaDAO) daoFactory.create("carta");
        IJugadorDAO jDAO = (IJugadorDAO) daoFactory.create("jugador");

        List<Cartes> cartes = cDAO.getCartesJugador(j);
        Collections.shuffle(cartes);

        if(!j.getCartes().isEmpty()) {
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

    /**
     * Simula l'acció de tirar un nombre aleatori de cartes a diferents jugadors o a si mateix.
     * Les cartes provenen de la mà del jugador que es passa per paràmetre.
     * @param j Jugador qeu tira les cartes
     */
    public static void TirarCartes(Jugadors j) {
        DAOFactory daoFactory = DAOFactoryImpl.getFactory("MySQL");

        ICartaDAO cDAO = (ICartaDAO) daoFactory.create("carta");
        IJugadorDAO jDAO = (IJugadorDAO) daoFactory.create("jugador");
        IJugadorsRivalsDAO jrDAO = (IJugadorsRivalsDAO) daoFactory.create("jugadorRival");

        List<Cartes> retornar = new ArrayList<>();
        List<Jugadors> jugadorsAmbVida = jDAO.getJugadorsAmbPersonatgesVidaAltres(j);
        List<Cartes> cartes = cDAO.getCartesJugador(j);
        Random r = new Random();

        int cartesATirar = 0;
        // Si el jugador té cartes a la mà crea una variable del nombre de cartes que el jugador haurà de tirar.
        if(!j.getCartes().isEmpty()) {
            cartesATirar = r.nextInt(1, cartes.size());
        }

        Collections.shuffle(cartes);
        System.out.println("El jugador " + j.getNom() + " jugarà " + cartesATirar + " cartes de les " + j.getCartes().size() + " cartes que té: ");

        // Agafem cartes aleatòries de totes les que té el jugador.
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
                    // Mirem tots els jugadors vius actuals
                    for(Jugadors ju : jugadorsAmbVida) {
                        Set<JugadorsRivals> rivals = j.getJugadorsRivals();
                        // Per cada rival que té el jugador que ha tirat la carta
                        for(JugadorsRivals jr : rivals) {
                            // Si aquest jugador rival que hem agafat té vida i és el jugador del bucle anterior entrem
                            if(jr.getIdRival().getIdRival().getPersonatgeDelJugador().getBales() > 0 && jr.getIdRival().getIdRival().getIdJugador() == ju.getIdJugador() && jr.getIdRival().getIdJugador() == j) {
                                // Si el jugador que està tirant la carta és l'ajudant i el contrincant és el Xerif no pot tirar la carta. Ha de buscar un altre blanc
                                if((j.getRolJugador().getNomRol() == Rol.AJUDANT && jr.getIdRival().getIdRival().getRolJugador().getNomRol() == Rol.XERIF)) {
                                    System.out.println("L'Ajudant no pot disparar el Xerif! Seria traició.");
                                }
                                else {
                                    // Si l'arma del jugador té un rang major o igual a la distància amb el contrincant pot tirar la carta.
                                    if(j.getArmaJugador().getDistanciaArma() >= jr.getDistanciaRival()) {
                                        // El contrincant perd una vida.
                                        jr.getIdRival().getIdRival().getPersonatgeDelJugador().setBales(jr.getIdRival().getIdRival().getPersonatgeDelJugador().getBales() - 1);
                                        jDAO.update(jr.getIdRival().getIdRival());
                                        System.out.println("Ha jugat un BANG! contra el jugador " +  jr.getIdRival().getIdRival().getPersonatgeDelJugador().getNom() + "! Quina mala baba.");
                                        System.out.println("L'enemic ara té " +  jr.getIdRival().getIdRival().getPersonatgeDelJugador().getBales() + " vides.");
                                        Set<Cartes> cartesEnemic = jr.getIdRival().getIdRival().getCartes();
                                        // Una vegada el contrincant ha perdut una vida li mirem a la mà si té un "Has Fallat!"
                                        for(Cartes carta : cartesEnemic) {
                                            // Si la té li tirem la carta
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
                                    }
                                    // En el cas que la distància fos major tiraria la carta igualment, però no faria cap efecte
                                    else {
                                        System.out.println("Ha jugat un BANG! contra el jugador " + ju.getNom() + " però no hi arriba! Quina mala sort");
                                    }
                                    acabat = true;
                                }
                                break;
                            }
                        }
                        if(acabat)
                            break;
                    }
                    break;
                case 2: //Miratelescòpica
                    Set<JugadorsRivals> rivals = j.getJugadorsRivals();

                    // Agafem un rival aleatòri de JugadorRivals i li disminuïm la distància en 1 (si aquesta era major a 0)
                    for(JugadorsRivals jr : rivals) {
                        if(jr.getDistanciaRival() > 0) {
                            jr.setDistanciaRival(jr.getDistanciaRival() - 1);
                            System.out.println("Ha jugat una Miratelescòpica contra el jugador " + jr.getIdRival().getIdRival().getNom() + "! Està maquinant alguna cosa...");
                            jrDAO.update(jr);
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

    /**
     * Funció extreta de codi igual a {@link #TirarCartes(Jugadors)} i que cura al personatge que es passa per paràmetre un punt de vida.
     * Aquesta curació no pot superar el nombre màxim de vides del personatge que tingui un jugador.
     * @param j Personatge a curar
     */
    private static void CurarPersonatge(Jugadors j) {
        DAOFactory daoFactory = DAOFactoryImpl.getFactory("MySQL");

        IJugadorDAO jDao = (IJugadorDAO) daoFactory.create("jugador");

        if(!(j.getPersonatgeDelJugador().getNom().equals("El Gringo") || j.getPersonatgeDelJugador().getNom().equals("Paul Regret"))) {
            if(j.getPersonatgeDelJugador().getBales() < 4)
                j.getPersonatgeDelJugador().setBales(j.getPersonatgeDelJugador().getBales() + 1);
        }
        else {
            if(j.getPersonatgeDelJugador().getBales() < 3)
                j.getPersonatgeDelJugador().setBales(j.getPersonatgeDelJugador().getBales() + 1);
        }
        jDao.update(j);
    }

    /**
     * Funció que afegeix una victòria a la base de dades a aquells jugadors que encara estan vius.
     * Una vegada els hi dona la victòria acaba la partida.
     * @param numJugadors Nombre de jugadors jugant a la partida actual
     */
    public static void FinalPartida(int numJugadors){
        DAOFactory daoFactory = DAOFactoryImpl.getFactory("MySQL");

        IPartidaDAO partDAO = (IPartidaDAO) daoFactory.create("partida");
        IJugadorDAO jDao = (IJugadorDAO) daoFactory.create("jugador");

        Partides partida = partDAO.getPartidaFinal();
        List<Jugadors> jList = jDao.getNumJugadorsAmbVida(numJugadors);

        for (Jugadors j : jList) {
            j.setGuanyats(j.getGuanyats()+1);
            System.out.println("Ha guanyat: "+j.getNom()+" amb el rol: "+j.getRolJugador().getNomRol()+"!!");
            jDao.update(j);
        }

        if(partida != null) {
            partida.setPartidaFinalitzada(true);
            partDAO.update(partida);
        }

        System.out.println("ACABA EL JOC");
    }

    /**
     * Reiniciem tots els valors necessàris de la base de dades per a poder començar una nova partida.
     * Les dades reiniciades són principalment relacions, i en el cas dels personatges, en modificar la seva vida directament a la base de dades,
     * tornar a posar en aquesta la vida màxima real dels personatges.
     */
    public static void ReiniciarValorsBBDD() {
        DAOFactory daoFactory = DAOFactoryImpl.getFactory("MySQL");

        ICartaDAO cDAO = (ICartaDAO) daoFactory.create("carta");
        IJugadorDAO jDao = (IJugadorDAO) daoFactory.create("jugador");
        IPersonatgeDAO pDao = (IPersonatgeDAO) daoFactory.create("personatge");
        IArmaDAO aDao=(IArmaDAO)daoFactory.create("arma");
        IJugadorsRivalsDAO jrDAO=(IJugadorsRivalsDAO) daoFactory.create("jugadorRival");

        List<Cartes> cartesList=cDAO.findAll();
        List<Jugadors> listJugadors = jDao.findAll();
        List<Personatges> listPersonatges = pDao.findAll();
        List<Armes> armesList= aDao.findAll();

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
            if(p.getNom().equals("El Gringo") || p.getNom().equals("Paul Regret")) {
                p.setBales(3);
                pDao.update(p);
            }
            else {
                p.setBales(4);
                pDao.update(p);
            }
        }

        for(Armes a : armesList){
            a.setJugador(null);
            aDao.update(a);
        }

        jrDAO.borrarTots();
    }

    /**
     * Funció per agafar el nombre de jugadors que es passa per paràmetre i afegir-los la nova partida creada.
     * @param jugadors Nombre de jugadors que estan jugant la partida actualment
     */
    public static void TornarAJugar(int jugadors) {
        DAOFactory daoFactory = DAOFactoryImpl.getFactory("MySQL");

        IPartidaDAO pDAO = (IPartidaDAO) daoFactory.create("partida");
        IJugadorDAO jDAO = (IJugadorDAO) daoFactory.create("jugador");

        List<Jugadors> jList = jDAO.getNumJugadors(jugadors);
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