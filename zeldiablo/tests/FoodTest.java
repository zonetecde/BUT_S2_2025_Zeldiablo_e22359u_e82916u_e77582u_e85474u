import gameZeldiablo.Zeldiablo.Entities.Monstre;
import gameZeldiablo.Zeldiablo.Entities.Player;
import gameZeldiablo.Zeldiablo.Items.Food;
import gameZeldiablo.Zeldiablo.Items.Item;
import gameZeldiablo.Zeldiablo.Labyrinthe;
import gameZeldiablo.Zeldiablo.StrategieDeplacement.*;
import gameZeldiablo.Zeldiablo.VariablesGlobales;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.*;

public class FoodTest {

    @Test
    public void testUse() throws Exception{
        Labyrinthe laby = new Labyrinthe("labyTests/laby0.txt",null);
        ArrayList<Item> inv = new ArrayList<Item>();
        inv.add(new Food());
        laby.getPlayer().setInventory(inv);
        laby.getPlayer().prendreDegat(3);
        inv.get(0).use(laby);
        assertEquals(VariablesGlobales.PV_BASE,laby.getPlayer().getVie());

    }

}
