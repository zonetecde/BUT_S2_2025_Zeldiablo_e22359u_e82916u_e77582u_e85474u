package gameZeldiablo.Zeldiablo.Entities;

import gameZeldiablo.Zeldiablo.VariablesGlobales;

public class Player extends Entite {
    public Player(int dx, int dy, int hp, int maxHp) {
        super(dx, dy, hp, maxHp);
    }

    public Player(int dx, int dy) {
        super(dx, dy);
    }
}
