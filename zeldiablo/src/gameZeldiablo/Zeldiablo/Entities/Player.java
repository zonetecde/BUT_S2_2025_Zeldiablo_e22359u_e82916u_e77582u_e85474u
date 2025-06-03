package gameZeldiablo.Zeldiablo.Entities;


import gameZeldiablo.Zeldiablo.Items.Item;
import gameZeldiablo.Zeldiablo.VariablesGlobales;

import java.util.ArrayList;

public class Player extends Entite {
    private ArrayList<Item> inventory;
    boolean aGagne = false;

    public Player(int dx, int dy, double maxHp, double degat) {
        super(dx, dy, maxHp, degat);
        this.inventory= new ArrayList<>();
    }

    public Player(int dx, int dy) {
        this(dx,dy, VariablesGlobales.PV_BASE,VariablesGlobales.DEGAT_BASE);
        this.inventory= new ArrayList<>();
    }

    /**
     * Dit si le joueur est en vie ou non
     * Si le joueur meurt, son inventaire est vidé
     * @param b true si le joueur est en vie, false sinon
     */
    public void setEnVie(boolean b) {
        if (!b) {
            inventory.clear();
        }

        this.enVie = b;
    }

    /**
     * Setter de l'inventaire du joueur (entre les niveaux par exemple)
     * @param inventory L'inventaire du joueur, une liste d'objets
     */
    public void setInventory(ArrayList<Item> inventory) {
        this.inventory = inventory;
    }

    /**
     * Retourne l'inventaire du joueur
     * @return L'inventaire du joueur, une liste d'items
     */
    public ArrayList<Item> getInventory(){
        return inventory;
    }

    public Player clone() {
        Player clone = new Player(this.getX(), this.getY(), this.getMaxHp(), this.getDegat());
        clone.setEnVie(this.enVie);
        clone.setHp(this.getHp());
        clone.setInventory(new ArrayList<>(this.inventory));
        return clone;
    }

    /**
     * Retourne si le joueur a gagné ou non
     * @return true si le joueur a gagné, false sinon
     */
    public boolean aGagne() {
        return aGagne;
    }

    /**
     * Set si le joueur a gagné ou non
     * @param b true si le joueur a gagné, false sinon
     */
    public void setaGagne(boolean b) { this.aGagne = b; }
}
