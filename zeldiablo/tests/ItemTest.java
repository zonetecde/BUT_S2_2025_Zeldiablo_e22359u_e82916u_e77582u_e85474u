import gameZeldiablo.Zeldiablo.Labyrinthe;
import gameZeldiablo.Zeldiablo.Entities.Player;
import gameZeldiablo.Zeldiablo.Cases.Case;
import gameZeldiablo.Zeldiablo.Items.Item;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

import java.io.File;
import java.io.IOException;

public class ItemTest {

    /**
     * Test de la méthode ramasserObjet du labyrinthe.
     * Vérifie que le joueur peut ramasser un item et que l'item n'est plus présent dans la case.
     */

    @Test
    public void testRamasserItem() throws IOException {
        Labyrinthe laby = new Labyrinthe(new File("labySimple/laby0.txt").getAbsolutePath());
        Player joueur = laby.getPlayer();

        boolean trouve = false;
        for (int y = 0; y < laby.getHauteur(); y++) {
            for (int x = 0; x < laby.getLongueur(); x++) {
                Case c = laby.getCase(y, x);
                if (c.hasItem()) {
                    joueur.setX(x);
                    joueur.setY(y);

                    assertEquals(0, joueur.getInventory().size());

                    laby.ramasserItem(joueur);

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