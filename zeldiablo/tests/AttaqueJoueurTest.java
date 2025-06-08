import gameZeldiablo.Zeldiablo.Labyrinthe;
import gameZeldiablo.Zeldiablo.Entities.Player;
import gameZeldiablo.Zeldiablo.Entities.Monstre;
import gameZeldiablo.Zeldiablo.Entities.Intelligence;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

public class AttaqueJoueurTest {

    @Test
    void testJoueurAttaqueMonstreQuandACote() throws Exception {

        Labyrinthe laby = new Labyrinthe("Laby/labyTests/laby3.txt");


        Player joueur = laby.getPlayer();
        joueur.setX(1);
        joueur.setY(1);

        // Ajoute un monstre à côté du joueur en (2,1)
        Monstre monstre = new Monstre(1, 2, Intelligence.NULLE);
        laby.getMonstres().clear();
        laby.getMonstres().add(monstre);

        double hpAvant = monstre.getHp();


        laby.attaqueJoueur();

        double hpApres = monstre.getHp();

        assertTrue(hpApres < hpAvant, "Le monstre doit perdre des PV si le joueur l'attaque à côté");
    }
}