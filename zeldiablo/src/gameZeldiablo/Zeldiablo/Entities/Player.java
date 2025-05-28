package gameZeldiablo.Zeldiablo.Entities;

import gameZeldiablo.Zeldiablo.VariablesGlobales;

public class Player extends Entite {
    int maxHp;
    public Player(int dx, int dy, int hp) {
        super(dx, dy, hp);
        maxHp=hp;
    }

    public Player(int dx, int dy) {
        super(dx, dy);
        maxHp= VariablesGlobales.PVBASE;
    }

    public int getMaxHp(){
        return maxHp;
    }


}
