package gameZeldiablo.Zeldiablo.Entities;


import gameZeldiablo.Zeldiablo.Items.Item;
import gameZeldiablo.Zeldiablo.VariablesGlobales;

import java.util.ArrayList;

public class Player extends Entite {
    private ArrayList<Item> inventory;

    public Player(int dx, int dy, int hp, int maxHp) {
        super(dx, dy, hp, maxHp);
        this.inventory= new ArrayList<>();
    }

    public Player(int dx, int dy) {
        this(dx,dy, VariablesGlobales.PV_BASE,VariablesGlobales.PV_BASE);
        this.inventory= new ArrayList<>();
    }

    public ArrayList<Item> getInventory(){return inventory;}
}
