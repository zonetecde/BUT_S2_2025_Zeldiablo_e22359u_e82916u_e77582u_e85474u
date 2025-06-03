package gameZeldiablo.Zeldiablo.Entities;

import gameZeldiablo.Zeldiablo.StrategieDeplacement.DeplacementStrategie;

public class Monstre extends Entite {
    private DeplacementStrategie deplacementStrategie;

    public Monstre(int x, int y, double pv, double degat, DeplacementStrategie deplacementStrategie) {
        super(x, y, pv, degat);
        this.deplacementStrategie = deplacementStrategie;
    }
}
