package gameZeldiablo.Zeldiablo;

import gameZeldiablo.Zeldiablo.Cases.Case;
import gameZeldiablo.Zeldiablo.Cases.CaseVide;
import gameZeldiablo.Zeldiablo.Entities.Player;
import gameZeldiablo.Zeldiablo.Items.Amulette;
import gameZeldiablo.Zeldiablo.Items.Item;
import gameZeldiablo.Zeldiablo.ZeldiabloDessin;
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
    private int currentLevel = 0; // Le niveau actuel

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
            // on met un scheduler
            if (!currentlyMoving) {
                currentlyMoving = true;
                ScheduledExecutorService scheduler = Executors.newScheduledThreadPool(1);
                scheduler.schedule(() -> {
                    currentlyMoving = false;
                }, 100, TimeUnit.MILLISECONDS);

                // Toggle l'affichage du menu si la touche tab est pressée
                if (clavier.tab) {
                    VariablesGlobales.MenuOuvert = !VariablesGlobales.MenuOuvert;
                }
                // Déplace le personnage
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
        }
    }

    private void inputsStart(Clavier clavier){
        if (clavier.haut || clavier.bas){VariablesGlobales.curseurStart=!VariablesGlobales.curseurStart;}

        else if (clavier.space){
            if (VariablesGlobales.curseurStart) {
                this.chargementNiveau();
                currentLevel=0;
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
            File[] folder = new File("labyJeu").listFiles();
            assert folder != null;
            String[] fichiers = new String[folder.length];
            for (int i=0;i<folder.length;i++){
                fichiers[i]=folder[i].getAbsolutePath();
            }
            Arrays.sort(fichiers);            for (String f : fichiers) {
                niveaux.add(new Labyrinthe(f,this));
            }

            // Ajoute une amulette aléatoirement dans le labyrinthe
            placerAmuletteAleatoire();
        }
        catch (IOException e){
            System.out.println("Données de laby corrompues");
            System.err.println(e);
            estFini=true;
            System.exit(1);
        }
    }

    /**
     * Change le niveau du jeu.
     * @param next Si true, passe au niveau suivant, sinon retourne au niveau précédent.
     */
    public void changeLevel(boolean next) {
        int newLevel = next ? currentLevel + 1 : currentLevel - 1;
        if (newLevel < niveaux.size()) {
            Player playerCloned = getLaby().getPlayer().clone();

            // Changement de niveau
            currentLevel = newLevel;

            getLaby().setPlayer(playerCloned);
            
            // Place le joueur à la position de départ du nouveau niveau si next = true, sinon à la position de la case d'escalier si next = false
            if (!next) {
                // Si on essaie d'aller au niveau -1, et que on a l'amulette, on gagne
                if (currentLevel == -1) {
                    if(getLaby().getPlayer().possedeItem("Amulette")) {
                        getLaby().getPlayer().setaGagne(true);
                    }
                }
                else {
                    getLaby().getPlayer().setY(getLaby().getPositionEscalierSortant()[0]);
                    getLaby().getPlayer().setX(getLaby().getPositionEscalierSortant()[1]);
                }
            } else {
                getLaby().getPlayer().setY(getLaby().getPositionEscalierEntrant()[0]);
                getLaby().getPlayer().setX(getLaby().getPositionEscalierEntrant()[1]);
            }
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

    public int getCurrentLevel() {
        return currentLevel;
    }

    /**
     * Place une amulette aléatoirement dans un niveau aléatoire du jeu.
     * Cherche une case vide et marchable pour y placer l'amulette.
     */
    private void placerAmuletteAleatoire() {
        if (!niveaux.isEmpty()) {
            // On choisit un niveau aléatoire pour l'amulette
            int niveauAmulette = Utilities.getRandomNumber(0, niveaux.size());
            Labyrinthe laby = niveaux.get(niveauAmulette);

            // On cherche toutes les cases vides du labyrinthe
            ArrayList<Case> casesVides = new ArrayList<>();
            for (int y = 0; y < laby.getHauteur(); y++) {
                for (int x = 0; x < laby.getLongueur(); x++) {
                    if (laby.getCase(y, x).getIsWalkable() && !laby.getCase(y, x).hasItem()) {
                        casesVides.add((laby.getCase(y, x)));
                    }
                }
            }

            // Si on a trouvé des cases vides, on en choisit une au hasard pour y mettre l'amulette
            if (!casesVides.isEmpty()) {
                int idx = Utilities.getRandomNumber(0, casesVides.size());
                casesVides.get(idx).addItem(new Amulette());
            }
        }
    }
}
