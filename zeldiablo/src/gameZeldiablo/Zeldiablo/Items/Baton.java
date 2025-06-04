package gameZeldiablo.Zeldiablo.Items;

import gameZeldiablo.Zeldiablo.VariablesGlobales;

/**
 * Arme baton
 */
public class Baton extends Item {
    public Baton() {
        super( "Bâton", VariablesGlobales.SPRITE_BATON, TypeItem.ARME, 0.5); // 0.5 points de dégâts
    }
}
