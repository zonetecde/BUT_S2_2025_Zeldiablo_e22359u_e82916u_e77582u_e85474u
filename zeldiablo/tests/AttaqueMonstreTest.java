import gameZeldiablo.Zeldiablo.Labyrinthe;
import gameZeldiablo.Zeldiablo.Entities.Player;
import gameZeldiablo.Zeldiablo.Entities.Monstre;
import gameZeldiablo.Zeldiablo.Entities.Intelligence;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

public class AttaqueMonstreTest {

    @Test

    /**
     * Teste si un monstre attaque le joueur lorsqu'il est à côté.
     * Le joueur doit perdre des points de vie (PV) si un monstre l'attaque.
     */
    void testMonstreAttaqueJoueurQuandACote() throws Exception {

        Labyrinthe laby = new Labyrinthe("Laby/labyTests/laby0.txt");

        Player joueur = laby.getJoueurs();
        joueur.setX(1);
        joueur.setY(1);

        Monstre monstre = new Monstre(2, 1, Intelligence.NULLE);
        laby.getEntites().clear();
        laby.getEntites().add(monstre);

        double hpAvant = joueur.getHp();
        laby.attaqueJoueur();
        laby.getEntites().get(0).mettreDegat(joueur);
        double hpApres = joueur.getHp();
        assertTrue(hpApres < hpAvant, "Le joueur doit perdre des PV si un monstre l'attaque à côté");
    }
}