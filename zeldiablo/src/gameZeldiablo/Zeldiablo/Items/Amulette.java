package gameZeldiablo.Zeldiablo.Items;

import gameZeldiablo.Zeldiablo.Sprite;
import gameZeldiablo.Zeldiablo.VariablesGlobales;

public class Amulette extends Item{
    private String name = "Amulette";

    public Amulette() {
        super(VariablesGlobales.SPRITE_AMULETTE);
    }

    @Override
    public String getName() {
        return this.name;
    }

    @Override
    public String toString() {
        return "Amulette";
    }
}
