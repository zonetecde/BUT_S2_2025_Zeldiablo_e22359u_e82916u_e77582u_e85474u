package gameZeldiablo.Zeldiablo;

import gameZeldiablo.Zeldiablo.Cases.Case;
import gameZeldiablo.Zeldiablo.Cases.CaseMur;
import gameZeldiablo.Zeldiablo.Cases.CasePiege;
import gameZeldiablo.Zeldiablo.Cases.CaseVide;
import gameZeldiablo.Zeldiablo.Cases.CaseEscalier;
import gameZeldiablo.Zeldiablo.Entities.Entite;
import gameZeldiablo.Zeldiablo.Entities.Intelligence;
import gameZeldiablo.Zeldiablo.Entities.Monstre;
import gameZeldiablo.Zeldiablo.Entities.Player;
import gameZeldiablo.Zeldiablo.Items.ItemDefault;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Random;

/**
 * classe labyrinthe. represente un labyrinthe avec
 */
public class Labyrinthe {
    /**
     * Constantes char
     */
    public static final char MUR = 'X';
    public static final char PJ = 'P';
    public static final char CASE_PIEGE = 'C';
    public static final char VIDE = '.';
    public static final char OBJECT = 'O';
    public static final char STAIR_SORTIE = 'S';
    public static final char STAIRS_DEPART = 'D';


    private Case[][] gameBoard; // Contient tout les rectangles du plateau de jeu

    private int[] positionEscalierSortant = new int[2]; // Position de l'escalier
    private int[] positionEscalierEntrant = new int[2]; // Position de l'escalier

    // Entité joueur
    private Player joueur;

    //Monstre
    private ArrayList<Monstre> monstres = new ArrayList<>();
    private Random random = new Random();

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
        int[] res = {y, x};
        return res;
    }

    /**
     * Charge le labyrinthe depuis un fichier.
     *
     * @param nom nom du fichier de labyrinthe
     * @return labyrinthe cree
     * @throws IOException probleme a la lecture / ouverture
     */
    public Labyrinthe(String nom, ZeldiabloJeu jeu) throws IOException {
        // ouvrir fichier
        FileReader fichier = new FileReader(nom);
        BufferedReader bfRead = new BufferedReader(fichier);

        int nbLignes, nbColonnes;
        // lecture nblignes
        nbLignes = Integer.parseInt(bfRead.readLine());
        // lecture nbcolonnes
        nbColonnes = Integer.parseInt(bfRead.readLine());

        // creation labyrinthe vide
        gameBoard = new Case[nbLignes][nbColonnes];

        this.joueur = null;

        // lecture des cases
        String ligne = bfRead.readLine();

        // stocke les indices courants
        int numeroLigne = 0;

        // parcours le fichier
        while (ligne != null) {

            // parcours de la ligne
            for (int colonne = 0; colonne < ligne.length(); colonne++) {
                char c = ligne.charAt(colonne);
                switch (c) {
                    case MUR:
                        gameBoard[numeroLigne][colonne] = new CaseMur(colonne, numeroLigne);
                        break;
                    case VIDE:
                        gameBoard[numeroLigne][colonne] = new CaseVide(colonne, numeroLigne);
                        //ajoute un potentiel monstre statique avec 1 chance sur 20
                        if (random.nextDouble() < VariablesGlobales.PROBA_MONSTRE) {
                            // ajoute un monstre statique\
                            Intelligence intelligenceAleatoire = Intelligence.values()[random.nextInt(Intelligence.values().length)];

                            Monstre monstre = new Monstre(colonne, numeroLigne, intelligenceAleatoire);
                            monstres.add(monstre);
                        }
                        break;

                    case OBJECT:
                        gameBoard[numeroLigne][colonne] = new CaseVide(colonne, numeroLigne);
                        gameBoard[numeroLigne][colonne].addItem(new ItemDefault());
                        break;
                    case CASE_PIEGE:
                        gameBoard[numeroLigne][colonne] = new CasePiege(colonne, numeroLigne, 1);
                        break;
                    case STAIR_SORTIE:
                        gameBoard[numeroLigne][colonne] = new CaseEscalier(colonne, numeroLigne, true);
                        // Enregistre la position de l'escalier sortant
                        positionEscalierSortant[0] = numeroLigne;
                        positionEscalierSortant[1] = colonne;
                        break;
                    case STAIRS_DEPART:
                        gameBoard[numeroLigne][colonne] = new CaseEscalier(colonne, numeroLigne, false);
                        // Enregistre la position de l'escalier entrant
                        positionEscalierEntrant[0] = numeroLigne;
                        positionEscalierEntrant[1] = colonne;

                        // je ne met pas de break pour que ça créer le joueur à la position de l'escalier de retour
                    case PJ:
                        // ajoute PJ et crée une case vide à cet endroit
                        if(c != STAIRS_DEPART) gameBoard[numeroLigne][colonne] = new CaseVide(colonne, numeroLigne);
                        this.joueur = new Player(colonne, numeroLigne);
                        break;
                    default:
                        throw new Error("caractere inconnu " + c);
                }
            }

            // lecture
            ligne = bfRead.readLine();
            numeroLigne++;
        }

        // ferme fichier
        bfRead.close();
    }


    /**
     * deplace le personnage en fonction de l'action.
     * gere la collision avec les murs
     *
     * @param action une des actions possibles
     */
    public void deplacerPerso(Direction action, Entite p) {
        // case courante
        int[] courante = {p.getY(), p.getX()};

        // calcule case suivante
        int[] suivante = getSuivant(courante[0], courante[1], action);

        // vérification des limites du plateau et si c'est pas un mur et si il n'y a pas de monstre
        if (canEntityMoveTo(suivante[0], suivante[1])) {
            // on met à jour la position du personnage
            p.setY(suivante[0]);
            p.setX(suivante[1]);
            Case caseSuivante = getCase(suivante[0], suivante[1]);
            caseSuivante.onStepOn(this.joueur);
        }

        // Lorsque le joueur se déplace, peut importe si le déplacement a réussi ou pas, tout les monstres effectuent
        // leur stratégie de déplacement
        for (Monstre monstre : monstres) {
            // On effectue le déplacement du monstre
            monstre.deplacer(this);
            if (monstre.aCote(this.joueur)) {
                monstre.mettreDegat(this.joueur);
            }
        }
    }

    /**
     * Vérifie si une entité peut se déplacer vers une case donnée.
     * @param i coordonnée verticale de la case
     * @param j coordonnée horizontale de la case
     * @return true si l'entité peut se déplacer vers cette case, false sinon
     */
    public boolean canEntityMoveTo(int i, int j) {
        return estDansLimites(i, j) &&
                        (getCase(i, j).getIsWalkable()) &&
                        !monstreSurCase(i, j) &&
                        !joueurSurCase(i, j);
    }

    /**
     * jamais fini
     *
     * @return fin du jeu
     */
    public boolean etreFini() {
        return false;
    }

    // ##################################
    // METHODES UTILITAIRES
    // ##################################

    /**
     * Vérifie si les coordonnées sont dans les limites du plateau
     * @param y coordonnée verticale
     * @param x coordonnée horizontale
     * @return true si les coordonnées sont valides
     */
    private boolean estDansLimites(int y, int x) {
        return y >= 0 && y < getHauteur() && x >= 0 && x < getLongueur();
    }

    // ##################################
    // GETTER
    // ##################################

    /**
     * return taille selon Y
     *
     * @return
     */
    public int getLongueur() {
        return gameBoard[0].length;
    }

    /**
     * return taille selon X
     *
     * @return
     */
    public int getHauteur() {
        return gameBoard.length;
    }

    /**
     * Retourne la case en y;x
     * @param y
     * @param x
     * @return La case en y;x
     */
    public Case getCase(int y, int x) {
        return gameBoard[y][x];
    }

    public Player getPlayer(){
        return (Player)this.joueur;
    }

    /**
     * Retourne la position de l'escalier sortant
     * @return Un tableau contenant les coordonnées de l'escalier sortant [y, x]
     */
    public int[] getPositionEscalierSortant() {
        return positionEscalierSortant;
    }

    /**
     * Retourne la position de l'escalier entrant
     * @return Un tableau contenant les coordonnées de l'escalier entrant [y, x]
     */
    public int[] getPositionEscalierEntrant() {
        return positionEscalierEntrant;
    }

    public void setPlayer(Player joueur) {
        this.joueur = joueur;
    }

    public ArrayList<Monstre> getMonstres() {
        return monstres;
    }

    /**
     * Ramasse un objet si le joueur est sur une case contenant un objet
     * @param joueur Le joueur qui tente de ramasser l'objet
     */
    public void ramasserItem(Player joueur) {
        int x = joueur.getX();
        int y = joueur.getY();
        Case caseCourante = getCase(y, x);
        // Vérifie si la case contient un objet
        if (caseCourante.hasItem()) {
            // Ajoute l'objet à l'inventaire du joueur et retire l'objet de la case
            joueur.getInventory().add(caseCourante.getItem());
            caseCourante.removeItem();
        }
    }

    /**
     * Vérifie si un monstre est présent sur la case spécifiée
     * @param y Coordonnée verticale de la case
     * @param x Coordonnée horizontale de la case
     * @return true si un monstre est sur la case, false sinon
     */
    public boolean monstreSurCase(int y, int x) {
        for (Entite monstre : monstres) {
            if (monstre.getY() == y && monstre.getX() == x) {
                return true;
            }
        }
        return false;
    }

    /**
     * Vérifie si un joueur est présent sur la case spécifiée
     * @param y Coordonnée verticale de la case
     * @param x Coordonnée horizontale de la case
     * @return true si le joueur est sur la case, false sinon
     */
    public boolean joueurSurCase(int y, int x) {
        return this.joueur.getY() == y && this.joueur.getX() == x;
    }



    public void setJoueur(Player joueur) {
        this.joueur = joueur;
    }
    public Player getJoueur() {
        return this.joueur;
    }

    public void attaqueJoueur() {
        ArrayList<Monstre> monstresVerif = new ArrayList<>(monstres);
        for (Monstre monstre : monstresVerif) {
            if (joueur.aCote(monstre)) {
                joueur.mettreDegat(monstre);
                if (monstre.estMort()) {
                    monstres.remove(monstre);
                }
            }
        }
    }
}
