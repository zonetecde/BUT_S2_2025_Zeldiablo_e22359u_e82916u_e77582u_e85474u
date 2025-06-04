package gameZeldiablo.Zeldiablo.Items;

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
    public boolean use(Labyrinthe laby){
        return laby.getPlayer().gagnerVie(VariablesGlobales.HEAL_FOOD);
    }

}
