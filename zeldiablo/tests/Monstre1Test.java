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
    private static final String CHEMIN_LABY_MONSTRE = "Laby/labyTests/laby0.txt";

    @BeforeEach
    void setUp() throws IOException {
        labyrinthe = new Labyrinthe(CHEMIN_LABY_MONSTRE);
    }

    //Vu que l'apparition n'est pas garantie, on ne peut pas le tester donc je test simplement la colision

    /**
     * Test de la collision avec un monstre.
     * Vérifie que le joueur ne peut pas se déplacer sur une case occupée par un monstre.
     */
    @Test
    void testCollisionMonstre() {
        Player joueur = labyrinthe.getJoueurs();
        // Place un monstre à droite du joueur
        MonstreStatique monstre = new MonstreStatique(joueur.getX() + 1, joueur.getY());
        labyrinthe.getEntites().add(monstre);

        int yAvant = joueur.getY();
        int xAvant = joueur.getX();
        labyrinthe.deplacerPerso(Direction.DROITE, joueur);

        assertEquals(yAvant, joueur.getY());
        assertEquals(xAvant, joueur.getX());
    }
}