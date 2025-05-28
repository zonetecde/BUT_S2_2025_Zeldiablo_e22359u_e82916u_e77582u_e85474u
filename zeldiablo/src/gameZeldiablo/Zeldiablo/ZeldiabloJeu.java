package gameZeldiablo.Zeldiablo;

import moteurJeu.Clavier;
import moteurJeu.Jeu;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

public class ZeldiabloJeu implements Jeu {
    // Liste contenant tout les niveaux du jeu (dans le dossier labySimple)
    private ArrayList<Labyrinthe> niveaux;
    
    // Indice du niveau actuel
    private int currentLevel = 0; // Le niveau actuel

    // Indique si le jeu est fini
    private boolean estFini = false;

    // Indique si le personnage est en train de se déplacer
    private boolean currentlyMoving = false;

    /**
     * Action à chaque frame
     * @param secondes temps ecoule depuis la derniere mise a jour
     * @param clavier objet contenant l'état du clavier'
     */
    @Override
    public void update(double secondes, Clavier clavier) {
        // Pour empêcher de spam les déplacements du personnage
        // on met un scheduler
        if (!currentlyMoving) {
            currentlyMoving =true;
            ScheduledExecutorService scheduler = Executors.newScheduledThreadPool(1);
            scheduler.schedule(() -> {
                currentlyMoving = false;
            }, 100, TimeUnit.MILLISECONDS);

            // Déplace le personnage
            deplacerPersonnage(clavier);

            scheduler.shutdown();
        }
    }

    
    /**
     * Déplace le personnage en fonction des touches pressées.
     * @param clavier Objet Clavier pour recuperer des input
     */
    private void deplacerPersonnage(Clavier clavier) {
        if (clavier.droite) {
            getLaby().deplacerPerso(Direction.DROITE, this.getLaby().joueur);
        } else if (clavier.gauche) {
            getLaby().deplacerPerso(Direction.GAUCHE, this.getLaby().joueur);
        } else if (clavier.haut) {
            getLaby().deplacerPerso(Direction.HAUT, this.getLaby().joueur);
        } else if (clavier.bas) {
            getLaby().deplacerPerso(Direction.BAS, this.getLaby().joueur);
        }
    }

    /**
     * Methode pour charger les niveaux du jeu
     */
    private void chargementNiveau(){
        try {
            niveaux =new ArrayList<>();
            File[] folder = new File("labySimple").listFiles();
            assert folder != null;
            for (File file : folder) {
                niveaux.add(new Labyrinthe(file.getAbsolutePath()));
            }
        }
        catch (IOException e){
            System.out.println("Données de laby corrompues");
            System.err.println(e);
            estFini=true;
            System.exit(1);
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
        chargementNiveau();
    }

    /**
     * spécifie si le jeu est fini
     * @return boolean fini
     */
    @Override
    public boolean etreFini() {
        return estFini;
    }
}
