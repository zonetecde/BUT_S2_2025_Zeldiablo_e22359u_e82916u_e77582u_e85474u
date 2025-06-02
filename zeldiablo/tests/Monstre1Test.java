import gameZeldiablo.Zeldiablo.Labyrinthe;
import gameZeldiablo.Zeldiablo.Direction;
import gameZeldiablo.Zeldiablo.Entities.Player;
import gameZeldiablo.Zeldiablo.Entities.MonstreStatique;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.IOException;

import static org.junit.jupiter.api.Assertions.*;

class Monstre1Test {

    private Labyrinthe labyrinthe;
    private static final String CHEMIN_LABY_MONSTRE = "labyTests/laby0.txt";

    @BeforeEach
    void setUp() throws IOException {
        labyrinthe = new Labyrinthe(CHEMIN_LABY_MONSTRE);
    }

    //Vu que l'apparition n'est pas garantie, on ne peut pas le tester donc je test simplement la colision

    @Test
    void testCollisionMonstre() {
        Player joueur = labyrinthe.getPlayer();
        // Place un monstre à droite du joueur
        MonstreStatique monstre = new MonstreStatique(joueur.getX() + 1, joueur.getY());
        labyrinthe.getMonstres().add(monstre);

        int yAvant = joueur.getY();
        int xAvant = joueur.getX();
        labyrinthe.deplacerPerso(Direction.DROITE, joueur);

        assertEquals(yAvant, joueur.getY());
        assertEquals(xAvant, joueur.getX());
    }
}