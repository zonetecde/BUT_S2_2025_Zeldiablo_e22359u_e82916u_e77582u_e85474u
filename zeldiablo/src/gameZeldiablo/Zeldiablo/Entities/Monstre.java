package gameZeldiablo.Zeldiablo.Entities;

import gameZeldiablo.Zeldiablo.StrategieDeplacement.DeplacementStrategie;
import gameZeldiablo.Zeldiablo.VariablesGlobales;

public class Monstre extends Entite {
    private DeplacementStrategie deplacementStrategie;

    public Monstre(int x, int y, double pv, double degat, DeplacementStrategie deplacementStrategie) {
        super(x, y, pv, degat);
        this.deplacementStrategie = deplacementStrategie;
    }

    public Monstre(int x, int y, DeplacementStrategie deplacementStrategie) {
        super(x, y, 3, VariablesGlobales.DEGAT_BASE);
        this.deplacementStrategie = deplacementStrategie;
    }
}
