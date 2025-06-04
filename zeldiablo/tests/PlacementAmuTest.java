
import gameZeldiablo.Zeldiablo.ZeldiabloJeu;
import gameZeldiablo.Zeldiablo.Labyrinthe;
import gameZeldiablo.Zeldiablo.Cases.Case;
import gameZeldiablo.Zeldiablo.Items.Item;


import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;


public class PlacementAmuTest {

    @Test
    void testAmuletteToujoursPlaceeNiveau5() {
        for (int essai = 0; essai < 10; essai++) {
            ZeldiabloJeu jeu = new ZeldiabloJeu(0);
            jeu.init();

            // vérifie que le jeu a bien 5 niveaux
            assertTrue(jeu.getNiveaux().size() >= 5, "Il doit y avoir au moins 5 niveaux");
            Labyrinthe niveau5 = jeu.getNiveaux().get(4);
            boolean amuletteTrouvee = false;

            for (int y = 0; y < niveau5.getHauteur(); y++) {
                for (int x = 0; x < niveau5.getLongueur(); x++) {
                    Case c = niveau5.getCase(y, x);
                    if (c.hasItem()) {
                        Item item = c.getItem();
                        if (item != null && "Amulette".equals(item.getName())) {
                            amuletteTrouvee = true;
                        }
                    }
                }
            }
            assertTrue(amuletteTrouvee, "L'amulette doit être placée dans le niveau 5 (essai " + essai + ")");
        }
    }
}