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

        // Déplace le personnage
        deplacerPersonnage(clavier);

        // Dépalce le monstre
        deplacerMonstre(clavier);
    }

    
    /*
     * Déplace le personnage en fonction des touches pressées.
     */
    private void deplacerPersonnage(Clavier clavier) {
        if (clavier.droite) {
            l.deplacerPerso(Labyrinthe.DROITE, this.l.pj);
        } else if (clavier.gauche) {
            l.deplacerPerso(Labyrinthe.GAUCHE, this.l.pj);
        } else if (clavier.haut) {
            l.deplacerPerso(Labyrinthe.HAUT, this.l.pj);
        } else if (clavier.bas) {
            l.deplacerPerso(Labyrinthe.BAS, this.l.pj);
        }
    }

    /*
     * Déplace le monstre en fonction des touches pressées.
     */
    private void deplacerMonstre(Clavier clavier) {
        if (clavier.droite || clavier.gauche || clavier.haut || clavier.bas) {
            // deplace le monstre aléatoirement
            int i = (int) (Math.random() * 4);
            String action = "";
            switch (i) {
                case 0:
                    action = Labyrinthe.HAUT;
                    break;
                case 1:
                    action = Labyrinthe.BAS;
                    break;
                case 2:
                    action = Labyrinthe.GAUCHE;
                    break;
                case 3:
                    action = Labyrinthe.DROITE;
                    break;
            }
            this.l.deplacerPerso(action, this.l.monstre);
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
