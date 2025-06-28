package Zeldiablo.Items.Armors;

import Zeldiablo.Entities.Player;
import Zeldiablo.Equipement;
import Zeldiablo.Items.Item;
import Zeldiablo.Items.ItemsList;
import Zeldiablo.Items.TypeItem;

public abstract class Armor extends Item {
    double armorPoints;
    /**
     * Constructeur de la classe Item
     *
     * @param name Nom de l'item
     * @param img  Chemin de l'image de l'item
     */
    public Armor(ItemsList name, String img,double armorPoints) {
        super(name, img, TypeItem.ARMOR);
        this.armorPoints = armorPoints;
    }

    public boolean equip(Player p,int i){
        Equipement equipement = p.getEquipement();
        Armor oldArmor;
        if ((oldArmor = equipement.getArmorPiece(i))!=null){
            p.getInventory().add(oldArmor);
        }

        equipement.setArmorPiece(this,i);
        return true;
    }

    public double getArmorPoints() {
        return armorPoints;
    }
}
