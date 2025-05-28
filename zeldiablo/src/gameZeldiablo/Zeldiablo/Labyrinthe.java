package gameZeldiablo.Zeldiablo;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;

/**
 * classe labyrinthe. represente un labyrinthe avec
 */
public class Labyrinthe {

    /**
     * Constantes char
     */
    public static final char MUR = 'X';
    public static final char PJ = 'P';
    public static final char VIDE = '.';
    public static final char MONSTRE = 'M';

    private Case[][] gameBoard; // Contient tout les rectangles du plateau de jeu

    // Entité joueur
    public Entite joueur;

    /**
     * Retourne la case suivante en fonction de l'action
     *
     * @param x      case depart
     * @param y      case depart
     * @param action action effectuee
     * @return case suivante
     */
    static int[] getSuivant(int y, int x, Direction action) {
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
                        break;
                    case PJ:
                        // ajoute PJ et crée une case vide à cet endroit
                        gameBoard[numeroLigne][colonne] = new CaseVide(colonne, numeroLigne);
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
     */    public void deplacerPerso(Direction action, Entite p) {
        // case courante
        int[] courante = {p.y, p.x};

        // calcule case suivante
        int[] suivante = getSuivant(courante[0], courante[1], action);

        // vérification des limites du plateau et si c'est pas un mur
        if (estDansLimites(suivante[0], suivante[1]) && 
            !(getCase(suivante[0], suivante[1]) instanceof CaseMur)) {
            // on met a jour personnage - CORRECTION: suivante[0] = y, suivante[1] = x
            p.y = suivante[0];
            p.x = suivante[1];
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

}
