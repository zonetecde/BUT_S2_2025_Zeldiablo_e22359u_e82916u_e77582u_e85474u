package gameZeldiablo.Zeldiablo.Items;

import gameZeldiablo.Zeldiablo.Entities.Player;
import gameZeldiablo.Zeldiablo.Labyrinthe;
import gameZeldiablo.Zeldiablo.VariablesGlobales;

/**
 * item food pour heal
 */
public class Food extends Item{
    public Food(){
        super("Food", VariablesGlobales.SPRITE_FOOD[(int)Math.floor(Math.random()*VariablesGlobales.SPRITE_FOOD.length)],TypeItem.MISC);
    }

    /**
     * Methode utilisation
     * @param laby labu contenant l'item
     * @return si utilisé
     */    
    @Override
    public boolean use(Player p){
        return p.gagnerVie(VariablesGlobales.HEAL_FOOD);
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
