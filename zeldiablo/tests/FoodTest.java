import gameZeldiablo.Zeldiablo.Entities.Player;
import gameZeldiablo.Zeldiablo.Items.Food;
import gameZeldiablo.Zeldiablo.Items.Item;
import gameZeldiablo.Zeldiablo.Labyrinthe;
import gameZeldiablo.Zeldiablo.VariablesGlobales;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.*;

public class FoodTest {

    @Test
    public void testUse() throws Exception{
        Player joueur = new Player(1,1,2,2);
        ArrayList<Item> inv = new ArrayList<Item>();
        inv.add(new Food());
        joueur.setInventory(inv);
        joueur.prendreDegat(3);
        inv.get(0).use(joueur);
        assertEquals(VariablesGlobales.PV_BASE,joueur.getVie());

    }

}
