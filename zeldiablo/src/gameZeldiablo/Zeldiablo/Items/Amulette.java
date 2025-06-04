package gameZeldiablo.Zeldiablo.Items;

import gameZeldiablo.Zeldiablo.VariablesGlobales;

/**
 * Amulette pour fin du jeu
 */
public class Amulette extends Item{
    public Amulette() {
        super("Amulette", VariablesGlobales.SPRITE_AMULETTE, TypeItem.OBJET);
    }
    
    /**
     * Getter pour les dégâts de l'item
     * @return 0.0 car ce n'est pas une arme
     */
    @Override
    public double getDegat() {
        return 0.0;
    }
}
