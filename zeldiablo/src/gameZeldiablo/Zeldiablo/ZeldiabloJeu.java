package gameZeldiablo.Zeldiablo;

import gameZeldiablo.Zeldiablo.Cases.Case;
import gameZeldiablo.Zeldiablo.Cases.CaseSpawn;
import gameZeldiablo.Zeldiablo.Entities.Player;
import moteurJeu.Clavier;
import moteurJeu.Jeu;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

public class ZeldiabloJeu implements Jeu {
    public ServerRoom room;
    public int idComp;
    public boolean lance;
    public boolean multiplayer;
    public String roomID;

    /**
     * Classe principale du jeu Zeldiablo.
     * Elle gère le chargement des niveaux, les déplacements du personnage,
     * et l'état du jeu.
     * La premiere map doit s'appeler FirstMap
     */
    // Liste contenant tout les niveaux du jeu (dans le dossier labySimple)
    private List<Player> joueur = new ArrayList<>();
    private int curJoueur = 0;


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
            if (multiplayer){
                room.sendData(new Encapsulation(clavier, idComp));
            }
            if (!currentlyMoving) {
                currentlyMoving = true;
                ScheduledExecutorService scheduler = Executors.newScheduledThreadPool(1);
                scheduler.schedule(() -> {
                    currentlyMoving = false;
                }, 160, TimeUnit.MILLISECONDS);

                if (lance && multiplayer) {
                    room.getClaviers().set(idComp, clavier);

                    for (int i=0;i<joueur.size();i++) {
                        curJoueur = i;
                        System.out.println(room.getClaviers().get(i));
                        Inputs(room.getClaviers().get(i));
                    }

                } else{
                    curJoueur = idComp;
                    Inputs(clavier);
                }

                scheduler.shutdown();
            }
        }
    }

    /**
     * Déplace le personnage en fonction des touches pressées.
     * @param clavier Objet Clavier pour recuperer des input
     */
    private void Inputs(Clavier clavier) {
        if (clavier.tab) {
            getJoueur().menuOuvert = !getJoueur().menuOuvert;
        }
        if (!lance){
            inputJoin(clavier);
        } else if (getJoueur().estMort()){
            inputsStart(clavier);
        } else if (getJoueur().menuOuvert){
            inputInv(clavier);
        }
        else {
            inputLaby(clavier);
        }

    }

    private void inputJoin(Clavier clavier){
        if (clavier.bas) {
            getJoueur().curseurLog = (getJoueur().curseurLog + 1) % 3;
        } else if (clavier.haut) {
            if (getJoueur().curseurLog > 0) {
                getJoueur().curseurLog--;
            } else {
                getJoueur().curseurLog = 2;
            }
        } else if (clavier.space) {
            //Creation d'une nouvelle room
            room = new ServerRoom();
            switch (getJoueur().curseurLog) {
                case 0:
                    //Lancement en solo sans room
                    lance = true;
                    break;
                case 1:
                    //rejoindre une room
                    this.idComp = room.log(this, clavier);
                    multiplayer = true;
                    lance = true;
                    break;
                case 2:
                    //Heberger une room
                    new Thread( () -> room.host(this, clavier)).start();
                    this.idComp = 0;
                    lance = true;
            }

        }
    }

    private void inputInv(Clavier clavier){
        if (clavier.droite){
            if (getJoueur().curseur<this.getJoueur().getInventory().size()-1) {
                getJoueur().curseur += 1;
            }
        } else if (clavier.gauche){
            if (getJoueur().curseur>0) {
                getJoueur().curseur -= 1;
            }
        } else if (clavier.haut){
            if (getJoueur().curseur>VariablesGlobales.COL_NUM_MENU-1) {
                getJoueur().curseur -= VariablesGlobales.COL_NUM_MENU;
            }
        } else if (clavier.bas){
            if (getJoueur().curseur<getJoueur().getInventory().size()-3) {
                getJoueur().curseur += VariablesGlobales.COL_NUM_MENU;
            }
        } else if (clavier.space) {
            try {
                if (getJoueur().getInventory().get(getJoueur().curseur).use(getJoueur())) {
                    getJoueur().getInventory().remove(getJoueur().curseur);
                    if (getJoueur().curseur>0) {
                        getJoueur().curseur -= 1;
                    }
                }
            }
            catch (Exception ignore){}
        }
    }

    private void inputLaby(Clavier clavier){
        if (clavier.interactionKey) {
            // Ramasse l'objet si possible
            getJoueur().ramasserItem();
            // Intéragit avec la case
            Labyrinthe currentLaby = getLaby();
            Player player = getJoueur();
            Case playerCase = currentLaby.getCase(player.getY(), player.getX());
            playerCase.onAction(player, this);
        }

        if (clavier.droite) {
            getLaby().deplacerPerso(Direction.DROITE, getJoueur());
            getJoueur().setSpriteJoueur(3);
        } else if (clavier.gauche) {
            getLaby().deplacerPerso(Direction.GAUCHE, getJoueur());
            getJoueur().setSpriteJoueur(2);
        } else if (clavier.haut) {
            getLaby().deplacerPerso(Direction.HAUT, getJoueur());
            getJoueur().setSpriteJoueur(0);
        } else if (clavier.bas) {
            getLaby().deplacerPerso(Direction.BAS, getJoueur());
            getJoueur().setSpriteJoueur(1);
        }
        else if (clavier.x) {
            getJoueur().attaque();
            if (getJoueur().getSpriteJoueur()<VariablesGlobales.SPRITE_JOUEUR.length/2) {
                getJoueur().setSpriteJoueur(getJoueur().getSpriteJoueur() + 4);
            }
        }
    }

    private void inputsStart(Clavier clavier){
        if (clavier.haut || clavier.bas){getJoueur().curseurStart=!getJoueur().curseurStart;}

        else if (clavier.space){
            if (getJoueur().curseurStart) {
                getJoueur().setLabyrinthe(MapList.getMap("FirstMap"));
                getLaby().initTimerMonstres();
                getJoueur().reset();
                getLaby().getEntites().add(getJoueur());
                //Set du joueur sur un point de spawn
                for (int i=0;i<getLaby().getLongueur();i++){
                    for (int j=0;j<getLaby().getHauteur();j++){
                        if (getLaby().getCase(j,i) instanceof CaseSpawn){
                            getJoueur().setX(i);
                            getJoueur().setY(j);
                        }
                    }
                }
                getJoueur().setEnVie(true);
                getJoueur().curseur=0;
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
        getLaby().getEntites().remove(getJoueur());

        getJoueur().setLabyrinthe(MapList.getMap(next));
        getJoueur().setY(y);
        getJoueur().setX(x);
        getLaby().getEntites().add(getJoueur());

        getLaby().initTimerMonstres();

    }

    /**
     * Getter de laby
     * @return renvoie le laby actuel
     */
    public Labyrinthe getLaby(int joueur){
        return getJoueur(joueur).getLabyrinthe();
    }

    public Labyrinthe getLaby(){
        return getLaby(curJoueur);
    }

    public Player getJoueur(int joueur){return this.joueur.get(joueur);}

    public Player getJoueur(){return getJoueur(curJoueur);}

    public void setJoueurs(List<Player> players){this.joueur=players;}

    public List<Player> getJoueurs(){return joueur;}

    /**
     * Action lancée avant le jeu
     */
    @Override
    public void init() {
        MapList.initialisation();
        lance = false;
        multiplayer = false;
        curJoueur = 0;
        idComp = 0;
        joueur.add(new Player(0,0,5,5));
        getJoueur().setEnVie(false);

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


