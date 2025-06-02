import gameZeldiablo.Zeldiablo.Entities.Player;
import gameZeldiablo.Zeldiablo.Items.ItemDefault;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Classe de test pour la classe Entite (via Player qui hérite d'Entite).
 */
class InventaireTest {

    @Test
    public void testGetInventory(){
        Player p = new Player(1,2);
        p.getInventory().add(new ItemDefault());
        assertEquals(1,p.getInventory().size());
    }
}