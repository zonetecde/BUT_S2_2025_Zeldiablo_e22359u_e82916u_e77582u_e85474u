import gameZeldiablo.Zeldiablo.ZeldiabloJeu;
import gameZeldiablo.Zeldiablo.Labyrinthe;
import gameZeldiablo.Zeldiablo.Cases.Case;
import gameZeldiablo.Zeldiablo.Entities.Player;
import gameZeldiablo.Zeldiablo.Items.Amulette;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

public class aGagneTest {

    @Test
    void testJoueurGagneAvecAmuletteSurEscalierDescendant() {
        ZeldiabloJeu jeu = new ZeldiabloJeu();
        jeu.init();

        // On prend le niveau 1 (index 0)
        Labyrinthe niveau1 = jeu.getLaby();
        Player joueur = niveau1.getJoueurs();

        // On donne l'amulette au joueur
        joueur.getInventory().add(new Amulette());

        // On place le joueur sur l'escalier descendant
        int[] posEscalier = niveau1.getPositionEscalierEntrant();
        joueur.setY(posEscalier[0]);
        joueur.setX(posEscalier[1]);

        // On récupère la case escalier
        Case escalier = niveau1.getCase(posEscalier[0], posEscalier[1]);

        // On simule l'action sur l'escalier
        escalier.onAction(joueur, jeu);

        // Le joueur doit avoir gagné
        assertTrue(joueur.aGagne(), "Le joueur doit avoir gagné après avoir utilisé l'escalier descendant avec l'amulette");
    }
}