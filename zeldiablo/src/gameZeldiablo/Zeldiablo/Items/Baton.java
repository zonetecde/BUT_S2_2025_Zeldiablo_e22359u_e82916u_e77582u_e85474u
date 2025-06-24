package gameZeldiablo.Zeldiablo.Items;

import gameZeldiablo.Zeldiablo.ItemsList;
import gameZeldiablo.Zeldiablo.VariablesGlobales;

/**
 * Arme baton
 */
public class Baton extends Arme {
    public Baton() {
        super(ItemsList.BATON, VariablesGlobales.SPRITE_BATON, 0.5); // 0.5 points de dégâts
    }
}
