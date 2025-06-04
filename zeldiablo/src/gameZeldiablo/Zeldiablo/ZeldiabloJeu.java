package gameZeldiablo.Zeldiablo;

import gameZeldiablo.Zeldiablo.Cases.Case;
import gameZeldiablo.Zeldiablo.Entities.Player;
import moteurJeu.Clavier;
import moteurJeu.Jeu;

import java.io.File;
import java.io.IOException;
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
     */
    // Liste contenant tout les niveaux du jeu (dans le dossier labySimple)
    private ArrayList<Labyrinthe> niveaux;
    
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
            if (VariablesGlobales.curseur<this.niveaux.get(currentLevel).getPlayer().getInventory().size()-1) {
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
            if (VariablesGlobales.curseur<this.niveaux.get(currentLevel).getPlayer().getInventory().size()-3) {
                VariablesGlobales.curseur += VariablesGlobales.COL_NUM_MENU;
            }
        } else if (clavier.space) {
            try {
                Player tmp = this.niveaux.get(currentLevel).getPlayer();
                if (tmp.getInventory().get(VariablesGlobales.curseur).use(niveaux.get(currentLevel))) {
                    tmp.getInventory().remove(VariablesGlobales.curseur);
                    if (VariablesGlobales.curseur>0) {
                        VariablesGlobales.curseur -= 1;
                    }
                }
            }
            catch (Exception e){}
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
                this.chargementNiveau();
                currentLevel=0;
                VariablesGlobales.curseur=0;
            }
            else{
                System.exit(0);
            }
        }
    }

    /**
     * Methode pour charger les niveaux du jeu
     */
    private void chargementNiveau(){
        try {
            niveaux =new ArrayList<>();
            File[] folder = new File(VariablesGlobales.DOSSIER_LABY).listFiles();
            assert folder != null;
            String[] fichiers = new String[folder.length];
            for (int i=0;i<folder.length;i++){
                fichiers[i]=folder[i].getAbsolutePath();
            }
            Arrays.sort(fichiers);
            for (String f : fichiers) {
                niveaux.add(new Labyrinthe(f,this));
            }
        }
        catch (IOException e){
            System.out.println("Données de laby corrompues");
            estFini = true;
            System.exit(1);
        }
    }

    /**
     * Change le niveau du jeu.
     * @param next Si true, passe au niveau suivant, sinon retourne au niveau précédent.
     */
    public void changeLevel(boolean next) {
        int newLevel = next ? currentLevel + 1 : currentLevel - 1;

        if (newLevel < 0 || newLevel >= niveaux.size()) {
            return;
        }

        getLaby().arreterTimerMonstres();

        Player playerCloned = getLaby().getPlayer().clone();

        currentLevel = newLevel;

        getLaby().setPlayer(playerCloned);

        getLaby().initTimerMonstres();

        if (!next) {
            playerCloned.setY(getLaby().getPositionEscalierSortant()[0]);
            playerCloned.setX(getLaby().getPositionEscalierSortant()[1]);
        } else {
            playerCloned.setY(getLaby().getPositionEscalierEntrant()[0]);
            playerCloned.setX(getLaby().getPositionEscalierEntrant()[1]);
        }
    }

    /**
     * Getter de laby
     * @return renvoie le laby actuel
     */
    public Labyrinthe getLaby(){
        return niveaux.get(currentLevel);
    }

    /**
     * Action lancée avant le jeu
     */
    @Override
    public void init() {
        this.currentLevel=0;
        chargementNiveau();
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

    /**
     * getter des niveaus
     * @return liste de laby
     */
    public ArrayList<Labyrinthe> getNiveaux() {
        return niveaux;
    }
}


