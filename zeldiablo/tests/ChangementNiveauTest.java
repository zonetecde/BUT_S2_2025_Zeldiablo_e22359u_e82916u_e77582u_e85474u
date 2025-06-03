import gameZeldiablo.Zeldiablo.ZeldiabloJeu;
import gameZeldiablo.Zeldiablo.Items.Item;
import gameZeldiablo.Zeldiablo.Items.ItemDefault;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

public class ChangementNiveauTest {

    @Test
    public void testNextLevel() {
        ZeldiabloJeu jeu = new ZeldiabloJeu(0);
        jeu.init();

        Item itemTest = new ItemDefault("Clé");
        jeu.getLaby().getPlayer().getInventory().add(itemTest);
        double hpAvant = jeu.getLaby().getPlayer().getHp();

        jeu.nextLevel();

        assertEquals(1, jeu.getCurrentLevel());
        assertTrue(jeu.getLaby().getPlayer().getInventory().stream()
                .anyMatch(i -> i.getName().equals(itemTest.getName())));
        assertEquals(hpAvant, jeu.getLaby().getPlayer().getHp());
    }
}