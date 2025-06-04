package gameZeldiablo.Zeldiablo;

import gameZeldiablo.Zeldiablo.Cases.*;
import gameZeldiablo.Zeldiablo.Entities.Entite;
import gameZeldiablo.Zeldiablo.Entities.Intelligence;
import gameZeldiablo.Zeldiablo.Entities.Monstre;
import gameZeldiablo.Zeldiablo.Entities.Player;
import gameZeldiablo.Zeldiablo.Items.*;
import gameZeldiablo.Zeldiablo.Entities.EtatVisuelle;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Random;
import java.util.Timer;
import java.util.TimerTask;

/**
 * classe labyrinthe. represente un labyrinthe avec
 */
public class Labyrinthe {
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
    public static final char CASE_PIEGE = 'C';
    public static final char VIDE = '.';
    public static final char ITEM = 'I';
    public static final char CASE_OUVERTURE = 'O';
    public static final char STAIR_SORTIE = 'S';
    public static final char STAIRS_DEPART = 'D';

    private final String nomDuLab; // Identifiant du labyrinthe

    private final Case[][] gameBoard; // Contient tout les rectangles du plateau de jeu
    private final String nomFichier;
    private final int[] positionEscalierSortant = new int[2]; // Position de l'escalier
    private final int[] positionEscalierEntrant = new int[2]; // Position de l'escalier
    private CasePorte casePorte; // La porte du niveau, si elle existe. Il y en a seulement une par niveau
    // et celle-ci est ouverte par une seule case d'ouverture.

    private Random random = new Random();

    // Entité joueur
    private Player joueur;
    //Monstres
    private final ArrayList<Monstre> monstres = new ArrayList<>();

    // Timer pour le déplacement automatique des monstres
    private Timer timerMonstres;

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

    /**
     * Charge le labyrinthe depuis un fichier.
     *
     * @param nom nom du fichier de labyrinthe
     * @throws IOException probleme a la lecture / ouverture
     */
    public Labyrinthe(String nom, ZeldiabloJeu jeu) throws IOException {
        // recupere le nom du lab : le nom du fichier dans le path
        nomDuLab = new File(nom).getName();

        // ouvrir fichier
        FileReader fichier = new FileReader(nom);
        BufferedReader bfRead = new BufferedReader(fichier);
        this.nomFichier = nom;
        int nbLignes, nbColonnes;
        int nbreMonstresSpawned = 0;

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

        this.joueur = null;

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
                // Ouvre la porte du niveau
                Runnable ouvrirPorte = () -> {
                    // Ouvre la porte du niveau
                    if (casePorte != null) {
                        casePorte.ouvrir();
                    }
                };
                switch (c) {
                    case MUR:
                        gameBoard[numeroLigne][colonne] = new CaseMur(colonne, numeroLigne);
                        break;
                    case VIDE:
                        gameBoard[numeroLigne][colonne] = new CaseVide(colonne, numeroLigne);

                        // Si c'est pas le premier niveau, ajouter potentiellement un monstre
                        if (!nomDuLab.contains("1") && 
                            joueur != null &&
                            random.nextDouble() < VariablesGlobales.PROBA_MONSTRE && 
                            nbreMonstresSpawned < VariablesGlobales.NBRE_MONSTRES_MAX &&
                            !joueurAdjacentACase(numeroLigne, colonne)) {
                            
                            Intelligence intelligenceAleatoire = Intelligence.values()[random.nextInt(Intelligence.values().length)];
                            Monstre monstre = new Monstre(colonne, numeroLigne, intelligenceAleatoire);
                            monstres.add(monstre);
                            nbreMonstresSpawned++;
                        }
                        break;
                    case ITEM:
                        gameBoard[numeroLigne][colonne] = new CaseVide(colonne, numeroLigne);
                        gameBoard[numeroLigne][colonne].addItem(new Food());
                        break;
                    case AMULETTE:
                        gameBoard[numeroLigne][colonne] = new CaseVide(colonne, numeroLigne);
                        gameBoard[numeroLigne][colonne].addItem(new Amulette());
                        break;

                    case ITEM_EPEE:
                        gameBoard[numeroLigne][colonne] = new CaseVide(colonne, numeroLigne);
                        gameBoard[numeroLigne][colonne].addItem(new Epee());
                        break;
                    case ITEM_BATON:
                        gameBoard[numeroLigne][colonne] = new CaseVide(colonne, numeroLigne);
                        gameBoard[numeroLigne][colonne].addItem(new Baton());
                        break;
                    case ITEM_HACHE:
                        gameBoard[numeroLigne][colonne] = new CaseVide(colonne, numeroLigne);
                        gameBoard[numeroLigne][colonne].addItem(new Hache());
                        break;
                    case CASE_OUVERTURE:
                        gameBoard[numeroLigne][colonne] = new CaseOuverture(colonne, numeroLigne, ouvrirPorte);
                        break;
                    case PANCARTE:
                        String textPancarte = ligneBrute.split(";")[1];

                        gameBoard[numeroLigne][colonne] = new CasePancarte(colonne, numeroLigne, textPancarte);
                        break;
                    case CASE_PIEGE:
                        gameBoard[numeroLigne][colonne] = new CasePiege(colonne, numeroLigne, 0.5);
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

                        // ajoute PJ et crée une case vide à cet endroit

                        if (this.joueur == null)
                            this.joueur = new Player(colonne, numeroLigne, VariablesGlobales.PV_BASE, VariablesGlobales.DEGAT_BASE);

                        break;
                    case PORTE:
                        casePorte = new CasePorte(colonne, numeroLigne);
                        gameBoard[numeroLigne][colonne] = casePorte;
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

        // Timer pour que les monstres bougent
        this.initTimerMonstres();
    }


    private boolean joueurAdjacentACase(int y, int x) {
        // Vérifie si le joueur est adjacent à la case (y, x)
        var caseApres = getSuivant(y, x, Direction.HAUT);
        if (joueurSurCase(caseApres[0], caseApres[1])) {
            return true;
        }
        caseApres = getSuivant(y, x, Direction.BAS);
        if (joueurSurCase(caseApres[0], caseApres[1])) {
            return true;
        }
        caseApres = getSuivant(y, x, Direction.DROITE);
        if (joueurSurCase(caseApres[0], caseApres[1])) {
            return true;
        }
        caseApres = getSuivant(y, x, Direction.GAUCHE);
        if (joueurSurCase(caseApres[0], caseApres[1])) {
            return true;
        }

        return false;
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
        int[] suivante = getSuivant(courante[0], courante[1], action); // vérification des limites du plateau et si c'est pas un mur et si il n'y a pas de monstre
        if (canEntityMoveTo(suivante[0], suivante[1])) {
            // Dès que l'entité se déplace, il ne dit plus rien
            p.setMsgToSay("");

            // on met à jour la position du personnage
            p.setY(suivante[0]);
            p.setX(suivante[1]);
            Case caseSuivante = getCase(suivante[0], suivante[1]);
            caseSuivante.onStepOn(this.joueur);
        }
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
                monstreSurCase(i, j) &&
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
     *
     * @param y coordonnée verticale
     * @param x coordonnée horizontale
     * @return true si les coordonnées sont valides
     */
    private boolean estDansLimites(int y, int x) {
        return y >= 0 && y < getHauteur() && x >= 0 && x < getLongueur();
    }

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

    public Player getPlayer() {
        return this.joueur;
    }

    /**
     * Retourne la position de l'escalier sortant
     *
     * @return Un tableau contenant les coordonnées de l'escalier sortant [y, x]
     */
    public int[] getPositionEscalierSortant() {
        return positionEscalierSortant;
    }

    /**
     * Retourne la position de l'escalier entrant
     *
     * @return Un tableau contenant les coordonnées de l'escalier entrant [y, x]
     */
    public int[] getPositionEscalierEntrant() {
        return positionEscalierEntrant;
    }

    /**
     * Setter du joueur
     */
    public void setPlayer(Player joueur) {
        this.joueur = joueur;
    }

    /**
     * getter de la liste de monstres
     *
     * @return Liste de monstre
     */
    public ArrayList<Monstre> getMonstres() {
        return monstres;
    }

    /**
     * getter du nom du laby
     *
     * @return Nom
     */
    public String getNomDuLab() {
        return nomDuLab;
    }

    /**
     * Ramasse un objet si le joueur est sur une case contenant un objet
     *
     * @param joueur Le joueur qui tente de ramasser l'objet
     */
    public void ramasserItem(Player joueur) {
        int x = joueur.getX();
        int y = joueur.getY();
        Case caseCourante = getCase(y, x);
        // Vérifie si la case contient un objet
        if (caseCourante.hasItem()) {
            // Ajoute l'objet à l'inventaire du joueur et retire l'objet de la case
            Item item = caseCourante.getItem();

            joueur.getInventory().add(item);
            String nomItem = item.getName();

            // Génère avec l'IA le texte que le joueur va dire
            Prompt.askGptForMsgWhenPickingItem(nomItem + " " + item.getImgFileName(), joueur::setMsgToSay);

            caseCourante.removeItem();
        }
    }

    /**
     * Vérifie si un monstre est présent sur la case spécifiée
     *
     * @param y Coordonnée verticale de la case
     * @param x Coordonnée horizontale de la case
     * @return true si un monstre est sur la case, false sinon
     */
    public boolean monstreSurCase(int y, int x) {
        for (Entite monstre : monstres) {
            if (monstre.getY() == y && monstre.getX() == x) {
                return false;
            }
        }
        return true;
    }

    /**
     * Vérifie si un joueur est présent sur la case spécifiée
     *
     * @param y     Coordonnée verticale de la case
     * @param x     Coordonnée horizontale de la case
     * @param aCote Si le joueur est à côté de la case retourne true quand même
     * @return true si le joueur est sur la case, false sinon
     */
    public boolean joueurSurCase(int y, int x) {
        return this.joueur.getY() == y && this.joueur.getX() == x;
    }

    /**
     * Setter du joueur
     *
     * @param joueur nouveau joueur
     */
    public void setJoueur(Player joueur) {
        this.joueur = joueur;
    }

    /**
     * getter du joueur
     *
     * @return Joueur actuel
     */
    public Player getJoueur() {
        return this.joueur;
    }

    /**
     * Effectue l'attaque du joueur sur les monstres à proximité.
     * Cette méthode change l'état visuel du joueur et des monstres touchés,
     */

    public void attaqueJoueur() {
        // Change l'état visuel du joueur
        joueur.setEtatVisuelle(EtatVisuelle.ATTAQUE_JOUEUR);

        // Crée une copie de la liste pour éviter les problèmes de modification pendant l'itération
        ArrayList<Monstre> monstresACheck = new ArrayList<>(monstres);

        // Pour chaque monstre
        for (Monstre monstre : monstresACheck) {
            // Si le monstre est à côté du joueur
            if (joueur.aCote(monstre)) {
                joueur.mettreDegat(monstre);
                monstre.setEtatVisuelle(EtatVisuelle.ATTAQUE_JOUEUR);

                // Si le monstre est mort
                if (monstre.estMort()) {
                    monstre.setEtatVisuelle(EtatVisuelle.MORT);
                    monstres.remove(monstre);
                    joueur.setHp(Math.min(joueur.getHp() + 1, joueur.getMaxHp()));
                }
            }
        }
    }

    /**
     * Initialise le timer pour le déplacement automatique des monstres
     */
    public void initTimerMonstres() {
        timerMonstres = new Timer();
        timerMonstres.scheduleAtFixedRate(new TimerTask() {
            @Override
            public void run() {
                deplacerMonstres();
            }
        }, 0, VariablesGlobales.DEPLACEMENT_MONSTRE);
    }

    /**
     * Effectue le déplacement et les actions de tous les monstres
     */
    private void deplacerMonstres() {
        // Crée une copie de la liste pour éviter les problèmes de modification pendant l'itération
        ArrayList<Monstre> monstresASupprimer = new ArrayList<>();
        
        for (Monstre monstre : monstres) {
            // On effectue le déplacement du monstre
            monstre.deplacer(this);

            if (monstre.aCote(this.joueur)) {
                //etat visuelle
                monstre.setEtatVisuelle(EtatVisuelle.ATTAQUE_MONSTRE);
                monstre.mettreDegat(this.joueur);
            } else {
                monstre.setEtatVisuelle(EtatVisuelle.NORMAL);
            }

            // déclanche le onstepon de la case où se trouve le monstre
            Case caseMonstre = getCase(monstre.getY(), monstre.getX());
            caseMonstre.onStepOn(monstre);

            if(monstre.getHp() <= 0) {
                monstresASupprimer.add(monstre);
            }
        }
        
        // Supprime les monstres morts
        monstres.removeAll(monstresASupprimer);
    }

    /**
     * Arrête le timer des monstres (utile lors de la destruction du labyrinthe)
     */
    public void arreterTimerMonstres() {
        if (timerMonstres != null) {
            timerMonstres.cancel();
            timerMonstres = null;
        }
    }

    /**
     * getter du nom de fichier
     *
     * @return String nom
     */
    public String getNomFichier() {
        // Retourne le nom du fichier du labyrinthe
        return nomFichier;
    }
}
