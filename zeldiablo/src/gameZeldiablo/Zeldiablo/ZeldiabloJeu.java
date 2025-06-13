package gameZeldiablo.Zeldiablo;

import gameZeldiablo.Zeldiablo.Cases.Case;
import gameZeldiablo.Zeldiablo.Cases.CaseSpawn;
import gameZeldiablo.Zeldiablo.Entities.Player;
import moteurJeu.Clavier;
import moteurJeu.Jeu;

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
    private final Player joueur = new Player(0,0,10,1);


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
        if (joueur.estMort()){
            joueur.getLabyrinthe().arreterTimerMonstres();
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
            if (VariablesGlobales.curseur<this.joueur.getInventory().size()-1) {
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
            if (VariablesGlobales.curseur<joueur.getInventory().size()-3) {
                VariablesGlobales.curseur += VariablesGlobales.COL_NUM_MENU;
            }
        } else if (clavier.space) {
            try {
                if (joueur.getInventory().get(VariablesGlobales.curseur).use(joueur)) {
                    joueur.getInventory().remove(VariablesGlobales.curseur);
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
            joueur.ramasserItem();
            // Intéragit avec la case
            Labyrinthe currentLaby = getLaby();
            Player player = joueur;
            Case playerCase = currentLaby.getCase(player.getY(), player.getX());
            playerCase.onAction(player, this);
        }

        if (clavier.droite) {
            getLaby().deplacerPerso(Direction.DROITE, joueur);
            joueur.setSpriteJoueur(3);
        } else if (clavier.gauche) {
            getLaby().deplacerPerso(Direction.GAUCHE, joueur);
            joueur.setSpriteJoueur(2);
        } else if (clavier.haut) {
            getLaby().deplacerPerso(Direction.HAUT, joueur);
            joueur.setSpriteJoueur(0);
        } else if (clavier.bas) {
            getLaby().deplacerPerso(Direction.BAS, joueur);
            joueur.setSpriteJoueur(1);
        }
        else if (clavier.x) {
            getLaby().attaqueJoueur();
            if (joueur.getSpriteJoueur()<VariablesGlobales.SPRITE_JOUEUR.length/2) {
                joueur.setSpriteJoueur(joueur.getSpriteJoueur() + 4);
            }
        }
    }

    private void inputsStart(Clavier clavier){
        if (clavier.haut || clavier.bas){VariablesGlobales.curseurStart=!VariablesGlobales.curseurStart;}

        else if (clavier.space){
            if (VariablesGlobales.curseurStart) {
                joueur.setLabyrinthe(MapList.getMap("FirstMap"));
                getLaby().initTimerMonstres();
                getJoueur().reset();
                getLaby().getEntites().add(joueur);
                //Set du joueur sur un point de spawn
                for (int i=0;i<getLaby().getLongueur();i++){
                    for (int j=0;j<getLaby().getHauteur();j++){
                        if (getLaby().getCase(j,i) instanceof CaseSpawn){
                            joueur.setX(i);
                            joueur.setY(j);
                        }
                    }
                }
                joueur.setEnVie(true);
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
        if (getLaby().getJoueurs().size()==1) {
            getLaby().arreterTimerMonstres();
        }
        getLaby().getEntites().remove(joueur);

        joueur.setLabyrinthe(MapList.getMap(next));
        joueur.setY(y);
        joueur.setX(x);
        getLaby().getEntites().add(joueur);

        getLaby().initTimerMonstres();

    }

    /**
     * Getter de laby
     * @return renvoie le laby actuel
     */
    public Labyrinthe getLaby(){
        return joueur.getLabyrinthe();
    }

    public Player getJoueur(){return this.joueur;}

    /**
     * Action lancée avant le jeu
     */
    @Override
    public void init() {
        joueur.setLabyrinthe(MapList.getMap("FirstMap"));
        joueur.getLabyrinthe().arreterTimerMonstres();
        joueur.setEnVie(false);
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


