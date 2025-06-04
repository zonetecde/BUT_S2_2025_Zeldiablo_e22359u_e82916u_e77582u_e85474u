package gameZeldiablo.Zeldiablo.Items;

import gameZeldiablo.Zeldiablo.Labyrinthe;
import gameZeldiablo.Zeldiablo.VariablesGlobales;

import java.util.Random;

public class Food extends Item{
    public Food(){
        super("Food", VariablesGlobales.SPRITE_FOOD[(int)Math.floor(Math.random()*VariablesGlobales.SPRITE_FOOD.length)],TypeItem.MISC);
    }

    @Override
    public void use(Labyrinthe laby){
        laby.getPlayer().prendreDegat(-3);
    }

}
