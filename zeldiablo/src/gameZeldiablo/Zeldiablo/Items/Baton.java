package gameZeldiablo.Zeldiablo.Items;

import gameZeldiablo.Zeldiablo.Sprite;
import gameZeldiablo.Zeldiablo.VariablesGlobales;

public class Baton extends Item {
    public Baton() {
        super( "Bâton", VariablesGlobales.SPRITE_BATON, TypeItem.ARME, 0.5); // 0.5 points de dégâts
    }
}
