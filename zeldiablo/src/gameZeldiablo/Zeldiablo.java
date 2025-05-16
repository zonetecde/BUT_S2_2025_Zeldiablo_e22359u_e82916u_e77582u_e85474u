package gameZeldiablo;

import moteurJeu.Clavier;
import moteurJeu.Jeu;

public class Zeldiablo implements Jeu {
    public Zeldiablo(){

    }

    public void update(double secondes, Clavier clavier){
        throw new Error();
    }

    @Override
    public void init() {
        throw new Error();
    }

    @Override
    public boolean etreFini() {
        return false;
    }

}
