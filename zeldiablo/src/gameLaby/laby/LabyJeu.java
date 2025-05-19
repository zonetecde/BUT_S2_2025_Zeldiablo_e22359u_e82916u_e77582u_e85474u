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

        // deplace la raquette en fonction des touches
        if (clavier.droite) {
            this.l.deplacerPerso(Labyrinthe.DROITE);
        }

        if (clavier.gauche) {
            this.l.deplacerPerso(Labyrinthe.GAUCHE);
        }

        if (clavier.haut) {
            this.l.deplacerPerso(Labyrinthe.HAUT);
        }

        if (clavier.bas) {
            this.l.deplacerPerso(Labyrinthe.BAS);
        }
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
