package gameZeldiablo.Zeldiablo.Items;

import gameZeldiablo.Zeldiablo.VariablesGlobales;

/**
 * Arme Epee
 */
public class Epee extends Item {
    public Epee() {
        super( "Épée", VariablesGlobales.SPRITE_EPEE, TypeItem.ARME, 2.0); // 2 points de dégâts
    }
}
