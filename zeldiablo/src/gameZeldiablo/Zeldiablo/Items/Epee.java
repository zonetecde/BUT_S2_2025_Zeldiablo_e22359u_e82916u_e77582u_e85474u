package gameZeldiablo.Zeldiablo.Items;

import gameZeldiablo.Zeldiablo.Sprite;
import gameZeldiablo.Zeldiablo.VariablesGlobales;

public class Epee extends Item {
    public Epee() {
        super( "Épée", VariablesGlobales.SPRITE_EPEE, TypeItem.ARME, 2.0); // 2 points de dégâts
    }
}
