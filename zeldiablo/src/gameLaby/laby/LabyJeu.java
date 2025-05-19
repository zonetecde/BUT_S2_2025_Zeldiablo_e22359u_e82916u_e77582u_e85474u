package gameLaby.laby;

import moteurJeu.Clavier;
import moteurJeu.Jeu;

public class LabyJeu implements Jeu {
    private Labyrinthe l;
    private boolean estFini=false;

    public Labyrinthe getLaby(){
        return l;
    }

    @Override
    public void update(double secondes, Clavier clavier) {


    }

    @Override
    public void init() {
        try {
            this.l = new Labyrinthe("labySimple/laby1.txt");
        }
        catch (Exception e){
            estFini=true;
        }
    }

    @Override
    public boolean etreFini() {
        return estFini;
    }


}
