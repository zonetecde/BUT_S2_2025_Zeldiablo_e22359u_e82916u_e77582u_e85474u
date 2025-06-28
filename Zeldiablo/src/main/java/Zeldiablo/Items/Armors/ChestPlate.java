package Zeldiablo.Items.Armors;

import Zeldiablo.Entities.Player;
import Zeldiablo.Items.ItemsList;
import Zeldiablo.VariablesGlobales;

public class ChestPlate extends Armor {

    /**
     * Constructeur de la classe Item
     *
     */
    public ChestPlate() {
        super(ItemsList.CHESTPLATE, VariablesGlobales.SPRITE_CHAINMAIL,5);
    }

    public boolean use(Player p){return equip(p,1);}
}
