package gameZeldiablo.Zeldiablo.Entities;


import gameZeldiablo.Zeldiablo.Items.Item;
import gameZeldiablo.Zeldiablo.VariablesGlobales;

import java.util.ArrayList;

public class Player extends Entite {
    private ArrayList<Item> inventory;

    public Player(int dx, int dy, double hp, double degat) {
        super(dx, dy, hp, degat);
        this.inventory= new ArrayList<>();
    }

    public Player(int dx, int dy) {
        this(dx,dy, VariablesGlobales.PV_BASE,VariablesGlobales.DEGAT_BASE);
        this.inventory= new ArrayList<>();
    }
}
