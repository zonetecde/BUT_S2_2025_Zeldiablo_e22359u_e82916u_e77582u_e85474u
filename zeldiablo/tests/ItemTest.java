import gameZeldiablo.Zeldiablo.Labyrinthe;
import gameZeldiablo.Zeldiablo.Entities.Player;
import gameZeldiablo.Zeldiablo.Cases.Case;
import gameZeldiablo.Zeldiablo.Items.Item;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

import java.io.File;
import java.io.IOException;

public class ItemTest {

    @Test
    public void testRamasserItem() throws IOException {
        Labyrinthe laby = new Labyrinthe(new File("labySimple/laby0.txt").getAbsolutePath(), null);
        Player joueur = laby.getPlayer();

        boolean trouve = false;
        for (int y = 0; y < laby.getHauteur(); y++) {
            for (int x = 0; x < laby.getLongueur(); x++) {
                Case c = laby.getCase(y, x);
                if (c.hasItem()) {
                    joueur.setX(x);
                    joueur.setY(y);

                    assertEquals(0, joueur.getInventory().size());

                    laby.ramasserObjet(joueur);

                    assertEquals(1, joueur.getInventory().size());

                    assertFalse(c.hasItem());

                    trouve = true;
                    break;
                }
            }
            if (trouve) break;
        }
        assertTrue(trouve, "Aucune case avec item trouvée dans le labyrinthe.");
    }
}