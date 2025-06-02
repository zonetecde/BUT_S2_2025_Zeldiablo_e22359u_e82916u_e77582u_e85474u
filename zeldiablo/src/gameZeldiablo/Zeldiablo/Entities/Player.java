package gameZeldiablo.Zeldiablo.Entities;

import gameZeldiablo.Zeldiablo.VariablesGlobales;

public class Player extends Entite {
    public Player(int dx, int dy, double hp, double degat) {
        super(dx, dy, hp, degat);
    }

    public Player(int dx, int dy) {
        super(dx, dy);
    }
}
