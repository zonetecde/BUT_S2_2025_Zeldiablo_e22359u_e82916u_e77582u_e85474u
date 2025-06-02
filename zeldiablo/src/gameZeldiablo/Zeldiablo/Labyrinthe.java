package gameZeldiablo.Zeldiablo;

import gameZeldiablo.Zeldiablo.Cases.Case;
import gameZeldiablo.Zeldiablo.Cases.CaseMur;
import gameZeldiablo.Zeldiablo.Cases.CasePiege;
import gameZeldiablo.Zeldiablo.Cases.CaseVide;
import gameZeldiablo.Zeldiablo.Entities.Entite;
import gameZeldiablo.Zeldiablo.Entities.Player;
import gameZeldiablo.Zeldiablo.Entities.MonstreStatique;
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


    private Case[][] gameBoard; // Contient tout les rectangles du plateau de jeu

    // Entité joueur
    private Player joueur;

    //Monstre
    private ArrayList<Entite> monstres = new ArrayList<>();
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
    public Labyrinthe(String nom) throws IOException {
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
                        if (random.nextInt(VariablesGlobales.PROBA_MONSTRE) == 0) {
                            // ajoute un monstre statique
                            MonstreStatique monstre = new MonstreStatique(colonne, numeroLigne);
                            monstres.add(monstre);
                        }
                        break;
                    case PJ:
                        // ajoute PJ et crée une case vide à cet endroit
                        gameBoard[numeroLigne][colonne] = new CaseVide(colonne, numeroLigne);
                        this.joueur = new Player(colonne, numeroLigne);
                        break;
                    case OBJECT:
                        gameBoard[numeroLigne][colonne] = new CaseVide(colonne, numeroLigne);
                        gameBoard[numeroLigne][colonne].addItem(new ItemDefault());
                        break;
                    case CASE_PIEGE:
                        gameBoard[numeroLigne][colonne] = new CasePiege(colonne, numeroLigne, 3);
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
        if (estDansLimites(suivante[0], suivante[1]) &&
                (getCase(suivante[0], suivante[1]).getIsWalkable()) &&
                !monstreSurCase(suivante[0], suivante[1])) {
            // on met à jour la position du personnage
            p.setY(suivante[0]);
            p.setX(suivante[1]);
            Case caseSuivante = getCase(suivante[0], suivante[1]);
            caseSuivante.onStepOn(this.joueur);
        }
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

    public ArrayList<Entite> getMonstres() {
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
}
