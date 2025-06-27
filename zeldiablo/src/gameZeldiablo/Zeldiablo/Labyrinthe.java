package gameZeldiablo.Zeldiablo;

import com.fasterxml.jackson.annotation.*;
import gameZeldiablo.Zeldiablo.Cases.*;
import gameZeldiablo.Zeldiablo.Entities.Entite;
import gameZeldiablo.Zeldiablo.Entities.Intelligence;
import gameZeldiablo.Zeldiablo.Entities.Monstre;
import gameZeldiablo.Zeldiablo.Entities.Player;
import gameZeldiablo.Zeldiablo.Items.*;

import java.io.*;
import java.util.*;

/**
 * classe labyrinthe. represente un labyrinthe avec
 */
@JsonIdentityInfo(
        generator = ObjectIdGenerators.UUIDGenerator.class,
        property = "@id"
)
@JsonIgnoreProperties(ignoreUnknown = true)
public class Labyrinthe implements Serializable {
    /**
     * Constantes char
     */
    public static final char MUR = 'X';
    public static final char PORTE = 'P';
    public static final char AMULETTE = 'A';
    public static final char PANCARTE = 'M';
    public static final char ITEM_EPEE = '1';
    public static final char ITEM_BATON = '2';
    public static final char ITEM_HACHE = '3';
    public static final char MONSTRE_INTELLIGENT = '9';
    public static final char MONSTRE_NULLE = '7';
    public static final char MONSTRE_MOYEN = '8';
    public static final char MONSTRE_FAIBLE = '6';
    public static final char CASE_PIEGE = 'C';
    public static final char VIDE = '.';
    public static final char ITEM = 'I';
    public static final char CASE_OUVERTURE = 'O';
    public static final char STAIR_SORTIE = 'S';
    public static final char STAIRS_DEPART = 'D';

    @JsonProperty("gameBoard")
    private final Case[][] gameBoard; // Contient tout les rectangles du plateau de jeu

    //Entites
    @JsonProperty("entites")
    private final List<Entite> entites;

    //Ticks
    private final ArrayList<Tickable> ticks;

    // Timer pour le déplacement automatique des monstres
    private transient Timer timerTicks;


    public Labyrinthe(
            @JsonProperty("gameBoard") Case[][] gameBoard,
            @JsonProperty("entites") ArrayList<Entite> entites,
            @JsonProperty("ticks") ArrayList<Tickable> ticks){
        this.gameBoard = gameBoard;
        this.entites = entites;
        this.ticks = new ArrayList<>();  // Create new list
        if (entites != null) {
            this.ticks.addAll(entites);  // Add entities that are Tickable
        }
        timerTicks = new Timer("ticks");
    }

    public Labyrinthe(int x,int y){
        this.gameBoard = new Case[x][y];
        this.entites = new ArrayList<>();
        this.ticks = new ArrayList<>();
        for (int i=0;i<x;i++){
            for (int j=0;j<y;j++){
                this.gameBoard[i][j]= new CaseVide();
            }
        }
    }

    /**
     * Charge le labyrinthe depuis un fichier.
     *
     * @param nom nom du fichier de labyrinthe
     * @throws IOException probleme a la lecture / ouverture
     */
    public Labyrinthe(String nom) throws IOException {
        this.entites = new ArrayList<>();
        this.ticks = new ArrayList<>();
        // ouvrir fichier
        FileReader fichier = new FileReader(nom);
        BufferedReader bfRead = new BufferedReader(fichier);
        int nbLignes, nbColonnes;

        // récupère le nbre de lignes et de colonnes
        String premiereLigne = bfRead.readLine();
        nbColonnes = premiereLigne.length();
        // compte le nombre de lignes
        nbLignes = 0;
        while (premiereLigne != null) {
            nbLignes++;
            premiereLigne = bfRead.readLine();
        }

        // remet le curseur au début du fichier
        fichier.close();
        fichier = new FileReader(nom);
        bfRead = new BufferedReader(fichier);

        // creation labyrinthe vide
        gameBoard = new Case[nbLignes][nbColonnes];


        // lecture des cases
        String ligneBrute = bfRead.readLine();

        // stocke les indices courants
        int numeroLigne = 0;

        // parcours le fichier
        while (ligneBrute != null) {
            String ligne = ligneBrute.trim();
            if (ligneBrute.split(";").length > 1) {
                // Si la ligne contient une pancarte, on ignore le reste de la ligne
                ligne = ligneBrute.split(";")[0];
            }

            // parcours de la ligne
            for (int colonne = 0; colonne < ligne.length(); colonne++) {
                char c = ligne.charAt(colonne);
                /*
                 * Runnable pour ouvrir la porte du niveau.
                 * Cette action est déclenchée par une case d'ouverture.
                 */

                switch (c) {
                    case MUR:
                        gameBoard[numeroLigne][colonne] = new CaseMur();
                        break;
                    case VIDE:
                        gameBoard[numeroLigne][colonne] = new CaseVide();
                        break;
                    case ITEM:
                        gameBoard[numeroLigne][colonne] = new CaseVide();
                        gameBoard[numeroLigne][colonne].setItem(new Food());
                        break;
                    case AMULETTE:
                        gameBoard[numeroLigne][colonne] = new CaseVide();
                        gameBoard[numeroLigne][colonne].setItem(new Amulette());
                        break;
                    case ITEM_EPEE:
                        gameBoard[numeroLigne][colonne] = new CaseVide();
                        gameBoard[numeroLigne][colonne].setItem(new Epee());
                        break;
                    case ITEM_BATON:
                        gameBoard[numeroLigne][colonne] = new CaseVide();
                        gameBoard[numeroLigne][colonne].setItem(new Baton());
                        break;
                    case ITEM_HACHE:
                        gameBoard[numeroLigne][colonne] = new CaseVide();
                        gameBoard[numeroLigne][colonne].setItem(new Hache());
                        break;
                    case CASE_OUVERTURE:
                        gameBoard[numeroLigne][colonne] = new CaseSwitch();
                        break;
                    case PANCARTE:
                        String textPancarte = ligneBrute.split(";")[1];

                        gameBoard[numeroLigne][colonne] = new CasePancarte(textPancarte);
                        break;
                    case CASE_PIEGE:
                        gameBoard[numeroLigne][colonne] = new CasePiege(0.5);
                        break;
                    case STAIR_SORTIE:
                        gameBoard[numeroLigne][colonne] = new CaseEscalier( true);
                        break;
                    case STAIRS_DEPART:
                        gameBoard[numeroLigne][colonne] = new CaseEscalier(false);
                        break;
                    case PORTE:
                        gameBoard[numeroLigne][colonne] = new CasePorte();
                        break;
                    case MONSTRE_INTELLIGENT:
                        gameBoard[numeroLigne][colonne] = new CaseVide();
                        Monstre monstreIntelligent = new Monstre(colonne, numeroLigne, Intelligence.FORTE,this);
                        entites.add(monstreIntelligent);
                        break;
                    case MONSTRE_NULLE:
                        gameBoard[numeroLigne][colonne] = new CaseVide();
                        Monstre monstreNulle = new Monstre(colonne, numeroLigne, Intelligence.NULLE,this);
                        entites.add(monstreNulle);
                        break;
                    case MONSTRE_MOYEN:
                        gameBoard[numeroLigne][colonne] = new CaseVide();
                        Monstre monstreMoyen = new Monstre(colonne, numeroLigne, Intelligence.MOYENNE,this);
                        entites.add(monstreMoyen);
                        break;
                    case MONSTRE_FAIBLE:
                        gameBoard[numeroLigne][colonne] = new CaseVide();
                        Monstre monstreFaible = new Monstre(colonne, numeroLigne, Intelligence.FAIBLE,this);
                        entites.add(monstreFaible);
                        break;
                    default:
                        throw new Error("caractere inconnu " + c);
                }
            }

            // lecture
            ligneBrute = bfRead.readLine();
            numeroLigne++;
        }        // ferme fichier
        bfRead.close();

    }



    /**
     * Vérifie si une entité peut se déplacer vers une case donnée.
     *
     * @param i coordonnée verticale de la case
     * @param j coordonnée horizontale de la case
     * @return true si l'entité peut se déplacer vers cette case, false sinon
     */
    public boolean canEntityMoveTo(int i, int j) {
        return estDansLimites(i, j) &&
                (getCase(i, j).getIsWalkable()) &&
                !entiteSurCase(i, j);
    }


    // ##################################
    // METHODES UTILITAIRES
    // ##################################

    /**
     * Vérifie si les coordonnées sont dans les limites du plateau
     *
     * @param y coordonnée verticale
     * @param x coordonnée horizontale
     * @return true si les coordonnées sont valides
     */
    private boolean estDansLimites(int y, int x) {
        return y >= 0 && y < getHauteur() && x >= 0 && x < getLongueur();
    }


    /**
     * Vérifie si un monstre est présent sur la case spécifiée
     *
     * @param y Coordonnée verticale de la case
     * @param x Coordonnée horizontale de la case
     * @return true si un monstre est sur la case, false sinon
     */
    public boolean entiteSurCase(int y, int x) {
        for (Entite e : entites) {
            if (e.getY() == y && e.getX() == x) {
                return true;
            }
        }
        return false;
    }


    /**
     * Arrête le timer des monstres (utile lors de la destruction du labyrinthe)
     */
    public void stopTickLoop() {
        timerTicks.purge();
    }

    /**
     * Initialise le timer pour le déplacement automatique des monstres
     */
    public void launchTickLoop() {
        timerTicks = new Timer("Ticks");
        timerTicks.scheduleAtFixedRate(new TimerTask() {
            @Override
            public void run() {
                try {
                    for (Tickable t : getTicks()) {
                        t.tick();
                    }
                }catch (ConcurrentModificationException e){
                    System.out.println(e.getMessage());
                }
            }
        }, 0, VariablesGlobales.TICK_SPEED);
    }

    //Getters

    public List<Tickable> getTicks(){return ticks;}

    /**
     * return taille selon Y
     *
     * @return longueur du tableau
     */
    public int getLongueur() {
        return gameBoard[0].length;
    }

    /**
     * return taille selon X
     *
     * @return hauteur du tableau
     */
    public int getHauteur() {
        return gameBoard.length;
    }

    /**
     * Retourne la case en y;x
     *
     * @param y pos y
     * @param x pos x
     * @return La case en y;x
     */
    public Case getCase(int y, int x) {
        return gameBoard[y][x];
    }


    /**
     * getter de la liste de monstres
     *
     * @return Liste de monstre
     */
    public List<Entite> getEntites() {
        return entites;
    }


    public Case[][] getGameBoard(){return gameBoard;}

    public List<Entite> nameJoueurs(){
        List<Entite> joueurs = new ArrayList<>();
        for (Entite e : entites){
            if (e instanceof Player){
                joueurs.add(e);
            }
        }
        return joueurs;
    }

    public List<Entite> nameMonstres(){
        List<Entite> joueurs = new ArrayList<>();
        for (Entite e : entites){
            if (e instanceof Monstre){
                joueurs.add(e);
            }
        }
        return joueurs;
    }

    /**
     * Retourne la case suivante en fonction de l'action
     *
     * @param x      case depart
     * @param y      case depart
     * @param action action effectuee
     * @return case suivante
     */
    public static int[] getSuivant(int y, int x, Direction action) {
        switch (action) {
            case HAUT:
                // on monte une ligne
                y--;
                break;
            case BAS:
                // on descend une ligne
                y++;
                break;
            case DROITE:
                // on augmente colonne
                x++;
                break;
            case GAUCHE:
                // on augmente colonne
                x--;
                break;
            default:
                throw new Error("action inconnue");
        }
        return new int[]{y, x};
    }


}