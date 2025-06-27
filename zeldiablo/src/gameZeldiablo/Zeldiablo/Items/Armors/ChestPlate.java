package gameZeldiablo.Zeldiablo.Items.Armors;

import gameZeldiablo.Zeldiablo.Entities.Player;
import gameZeldiablo.Zeldiablo.Equipement;
import gameZeldiablo.Zeldiablo.Items.Item;
import gameZeldiablo.Zeldiablo.Items.ItemsList;
import gameZeldiablo.Zeldiablo.Items.TypeItem;
import gameZeldiablo.Zeldiablo.VariablesGlobales;

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
