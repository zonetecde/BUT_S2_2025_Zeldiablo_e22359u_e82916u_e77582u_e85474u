package gameZeldiablo.Zeldiablo.Items;

import gameZeldiablo.Zeldiablo.Entities.Player;
import gameZeldiablo.Zeldiablo.VariablesGlobales;

/**
 * item food pour heal
 */
public class Food extends Item{
    public Food(){
        super(ItemsList.FOOD, VariablesGlobales.SPRITE_FOOD[(int)Math.floor(Math.random()*VariablesGlobales.SPRITE_FOOD.length)],TypeItem.MISC);
    }

    /**
     * Methode utilisation
     * @return si utilisé
     */    
    @Override
    public boolean use(Player p){
        return p.gagnerVie(VariablesGlobales.HEAL_FOOD);
    }

}
