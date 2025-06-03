package gameZeldiablo.Zeldiablo.Entities;

import gameZeldiablo.Zeldiablo.StrategieDeplacement.DeplacementStatique;
import gameZeldiablo.Zeldiablo.VariablesGlobales;

public class MonstreStatique extends Monstre {
    public MonstreStatique(int dx, int dy, double hp, double degat) {
        super(dx, dy, hp, degat, new DeplacementStatique());
    }

    public MonstreStatique(int dx, int dy){
        super(dx, dy, new DeplacementStatique());
    }
}
