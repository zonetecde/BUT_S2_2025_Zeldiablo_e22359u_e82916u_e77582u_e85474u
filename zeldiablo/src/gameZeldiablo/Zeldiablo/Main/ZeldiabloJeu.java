package gameZeldiablo.Zeldiablo.Main;

import gameZeldiablo.Zeldiablo.*;
import gameZeldiablo.Zeldiablo.Entities.Player;
import moteurJeu.Clavier;
import moteurJeu.Jeu;

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
    Thread animationItem;

    // Clavier correspondant à la derniere config
    private Clavier oldKb;


    /**
     * Action à chaque frame
     * @param secondes temps ecoule depuis la derniere mise a jour
     * @param c objet contenant l'état du clavier'
     */


    @Override
    public void update(double secondes, Clavier c) {
        if (multiplayer) {
            if (!c.equals(oldKb)) {
                oldKb = c.clone();
                room.sendData(new Encapsulation(c.clone(), idComp), -1);
                room.getClaviers().set(this.idComp,c);
                System.out.println("nbJoueurs: "+ joueur.size());
            }

            //room.getClaviers().forEach((System.out::println));


            for (int i = 0 ; i<room.getClaviers().size();i++) {
                Clavier clavier = room.getClaviers().get(i);
                curJoueur = i;
                takeInput(clavier, i);
            }
        }else{
            takeInput(c,idComp);
        }

    }

    private void takeInput(Clavier clavier, int i) {
        if (clavier.droite || clavier.gauche || clavier.haut || clavier.bas || clavier.tab || clavier.interactionKey || clavier.space || clavier.x) {
            // Pour empêcher de spam les déplacements du personnage
            //System.out.println("dans Takeinput :\n"+clavier+"\n"+oldKb);
            if (!joueur.get(i).currentlyMoving) {
                joueur.get(i).currentlyMoving = true;
                ScheduledExecutorService scheduler = Executors.newScheduledThreadPool(1);
                scheduler.schedule(() -> {
                    joueur.get(i).currentlyMoving = false;
                }, 160, TimeUnit.MILLISECONDS);

                Inputs(clavier,getJoueur(i));

                scheduler.shutdown();
            }
        }
    }

    /**
     * Déplace le personnage en fonction des touches pressées.
     * @param clavier Objet Clavier pour recuperer des input
     */
    private void Inputs(Clavier clavier,Player joueur) {
        if (clavier.tab) {
            getJoueur().menuOuvert = !getJoueur().menuOuvert;
        }
        if (!lance){
            Join.inputJoin(clavier,joueur,this);
        } else if (getJoueur().estMort()){
            Start.inputsStart(clavier,joueur,this);
        } else if (getJoueur().menuOuvert){
            Inv.inputInv(clavier,joueur);
        }
        else {
            Laby.inputLaby(clavier,joueur,this);
        }

    }




    /**
     * Change le niveau du jeu.
     * @param next Si true, passe au niveau suivant, sinon retourne au niveau précédent.
     */
    public void changeLevel(String next, int x, int y) {
        if (next!=null) {
            if (getLaby().nameJoueurs().size() == 1) {
                getLaby().stopTickLoop();
            }

            getJoueur().setLabyrinthe(MapList.getMap(next));
            getJoueur().setY(y);
            getJoueur().setX(x);

            getLaby().launchTickLoop();
        }else{
            System.out.println("No destination");
        }
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
        if (animationItem != null){animationItem.interrupt();}
        lance = false;
        multiplayer = false;
        curJoueur = 0;
        idComp = 0;
        joueur.add(new Player(0,0,5,5));
        getJoueur().setEnVie(false);
        animationItem = new Thread(() -> {
            int LOWPOINT = 15;
            int HIGHPOINT = 20;
            int INTERVAL = 150;

            try {
                while (true) {
                    for (int i = LOWPOINT; i < HIGHPOINT; i++) {
                        Laby.itemFloat = i;
                        Thread.sleep(INTERVAL);
                    }
                    for (int i = HIGHPOINT; i > LOWPOINT; i--) {
                        Laby.itemFloat = i;
                        Thread.sleep(INTERVAL);
                    }
                }
            } catch (InterruptedException ignore) {}
        },"ItemAnimation");
    }

    /**
     * spécifie si le jeu est fini
     * @return boolean fini
     */
    @Override
    public boolean etreFini() {
        return false;
    }


}


