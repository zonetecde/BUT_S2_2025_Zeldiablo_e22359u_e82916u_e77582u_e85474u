package Zeldiablo.Items;

import Zeldiablo.Entities.Player;
import Zeldiablo.Equipement;
import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * Classe abstraite représentant une arme
 */
public abstract class Arme extends Item {
    
    protected double degat;
    
    /**
     * Constructeur de la classe Arme
     * @param name Nom de l'arme
     * @param img Chemin de l'image de l'arme
     * @param degat Dégâts infligés par l'arme
     */
    public Arme(ItemsList name, String img, double degat) {
        super(name, img, TypeItem.ARME);
        this.degat = degat;
    }

    public boolean use(Player p){
        Equipement equipement = p.getEquipement();
        Arme oldWeapon;
        if ((oldWeapon = equipement.getWeapon())!=null){
            p.getInventory().add(oldWeapon);
        }

        equipement.setWeapon(this);
        return true;
    }

    /**
     * Getter pour les dégâts de l'arme
     * @return Les dégâts de l'arme
     */
    @Override
    @JsonProperty("degat")
    public double getDegat() {
        return this.degat;
    }

    public String toString() {
        return this.getName().toString().toLowerCase() + " (" + this.getDegat() + ")";
    }
}
