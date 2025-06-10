package gameZeldiablo.Zeldiablo;

import gameZeldiablo.Zeldiablo.Cases.Case;
import gameZeldiablo.Zeldiablo.Cases.CaseEscalier;
import gameZeldiablo.Zeldiablo.Entities.Player;
import moteurJeu.Clavier;
import moteurJeu.Jeu;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

public class ZeldiabloJeu implements Jeu {

    /**
     * Classe principale du jeu Zeldiablo.
     * Elle gère le chargement des niveaux, les déplacements du personnage,
     * et l'état du jeu.
     * La premiere map doit s'appeler FirstMap
     */
    // Liste contenant tout les niveaux du jeu (dans le dossier labySimple)
    private Labyrinthe niveaux;
    
    // Indice du niveau actuel
    private int currentLevel; // Le niveau actuel

    // Indique si le jeu est fini
    private boolean estFini = false;

    // Indique si le personnage est en train de se déplacer
    private boolean currentlyMoving = false;


    public ZeldiabloJeu(int level){
        currentLevel = level;
    }

    /**
     * Action à chaque frame
     * @param secondes temps ecoule depuis la derniere mise a jour
     * @param clavier objet contenant l'état du clavier'
     */


    @Override
    public void update(double secondes, Clavier clavier) {
        if (clavier.droite || clavier.gauche || clavier.haut || clavier.bas || clavier.tab || clavier.interactionKey || clavier.space || clavier.x) {
            // Pour empêcher de spam les déplacements du personnage
            if (!currentlyMoving) {
                currentlyMoving = true;
                ScheduledExecutorService scheduler = Executors.newScheduledThreadPool(1);
                scheduler.schedule(() -> {
                    currentlyMoving = false;
                }, 160, TimeUnit.MILLISECONDS);

                if (clavier.tab) {
                    VariablesGlobales.MenuOuvert = !VariablesGlobales.MenuOuvert;
                }
                Inputs(clavier);

                scheduler.shutdown();
            }
        }
    }

    /**
     * Déplace le personnage en fonction des touches pressées.
     * @param clavier Objet Clavier pour recuperer des input
     */
    private void Inputs(Clavier clavier) {
        if (getLaby().getPlayer().estMort()){
            inputsStart(clavier);
        } else if (VariablesGlobales.MenuOuvert){
            inputInv(clavier);
        }
        else {
            inputLaby(clavier);
        }

    }

    private void inputInv(Clavier clavier){
        if (clavier.droite){
            if (VariablesGlobales.curseur<this.niveaux.getPlayer().getInventory().size()-1) {
                VariablesGlobales.curseur += 1;
            }
        } else if (clavier.gauche){
            if (VariablesGlobales.curseur>0) {
                VariablesGlobales.curseur -= 1;
            }
        } else if (clavier.haut){
            if (VariablesGlobales.curseur>VariablesGlobales.COL_NUM_MENU-1) {
                VariablesGlobales.curseur -= VariablesGlobales.COL_NUM_MENU;
            }
        } else if (clavier.bas){
            if (VariablesGlobales.curseur<this.niveaux.getPlayer().getInventory().size()-3) {
                VariablesGlobales.curseur += VariablesGlobales.COL_NUM_MENU;
            }
        } else if (clavier.space) {
            try {
                Player tmp = this.niveaux.getPlayer();
                if (tmp.getInventory().get(VariablesGlobales.curseur).use(niveaux)) {
                    tmp.getInventory().remove(VariablesGlobales.curseur);
                    if (VariablesGlobales.curseur>0) {
                        VariablesGlobales.curseur -= 1;
                    }
                }
            }
            catch (Exception ignore){}
        }
    }

    private void inputLaby(Clavier clavier){
        if (clavier.interactionKey) {
            // Ramasse l'objet si possible
            getLaby().ramasserItem(getLaby().getPlayer());
            // Intéragit avec la case
            Labyrinthe currentLaby = getLaby();
            Player player = currentLaby.getPlayer();
            Case playerCase = currentLaby.getCase(player.getY(), player.getX());
            playerCase.onAction(player, this);
        }

        if (clavier.droite) {
            getLaby().deplacerPerso(Direction.DROITE, this.getLaby().getPlayer());
            getLaby().getPlayer().setSpriteJoueur(3);
        } else if (clavier.gauche) {
            getLaby().deplacerPerso(Direction.GAUCHE, this.getLaby().getPlayer());
            getLaby().getPlayer().setSpriteJoueur(2);
        } else if (clavier.haut) {
            getLaby().deplacerPerso(Direction.HAUT, this.getLaby().getPlayer());
            getLaby().getPlayer().setSpriteJoueur(0);
        } else if (clavier.bas) {
            getLaby().deplacerPerso(Direction.BAS, this.getLaby().getPlayer());
            getLaby().getPlayer().setSpriteJoueur(1);
        }
        else if (clavier.x) {
            getLaby().attaqueJoueur();
            if (getLaby().getPlayer().getSpriteJoueur()<VariablesGlobales.SPRITE_JOUEUR.length/2) {
                getLaby().getPlayer().setSpriteJoueur(getLaby().getPlayer().getSpriteJoueur() + 4);
            }
        }
    }

    private void inputsStart(Clavier clavier){
        if (clavier.haut || clavier.bas){VariablesGlobales.curseurStart=!VariablesGlobales.curseurStart;}

        else if (clavier.space){
            if (VariablesGlobales.curseurStart) {
                this.niveaux = MapList.getMap("FirstMap");
                this.niveaux.getPlayer().setEnVie(true);
                VariablesGlobales.curseur=0;
            }
            else{
                System.exit(0);
            }
        }
    }


    /**
     * Change le niveau du jeu.
     * @param next Si true, passe au niveau suivant, sinon retourne au niveau précédent.
     */
    public void changeLevel(String next, int x, int y) {
        getLaby().arreterTimerMonstres();

        Player playerCloned = getLaby().getPlayer().clone();

        niveaux = MapList.getMap(next);
        getLaby().setPlayer(playerCloned);

        getLaby().initTimerMonstres();

        playerCloned.setY(y);
        playerCloned.setX(x);
    }

    /**
     * Getter de laby
     * @return renvoie le laby actuel
     */
    public Labyrinthe getLaby(){
        return niveaux;
    }

    /**
     * Action lancée avant le jeu
     */
    @Override
    public void init() {
        this.niveaux = MapList.getMap("FirstMap");
        getLaby().getPlayer().setEnVie(false);
    }

    /**
     * spécifie si le jeu est fini
     * @return boolean fini
     */
    @Override
    public boolean etreFini() {
        return estFini;
    }

    /**
     * Getter du niveau
     * @return laby
     */
    public int getCurrentLevel() {
        return currentLevel;
    }

}


