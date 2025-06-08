import gameZeldiablo.Zeldiablo.Entities.Monstre;
import gameZeldiablo.Zeldiablo.Entities.Player;
import gameZeldiablo.Zeldiablo.Labyrinthe;
import gameZeldiablo.Zeldiablo.StrategieDeplacement.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.IOException;

import static org.junit.jupiter.api.Assertions.*;

public class DeplacementTest {
    private Labyrinthe labyrinthe;
    private static final String CHEMIN_LABY_TEST = "Laby/labyTests/laby0.txt";

    @BeforeEach
    void setUp() throws IOException {
        labyrinthe = new Labyrinthe(CHEMIN_LABY_TEST);
    }

    @Test
    void testDeplacementStatique() {
        // Création d'un monstre avec déplacement statique
        Monstre monstre = new Monstre(2, 2);
        monstre.setDeplacementStrategie(new DeplacementStatique());

        int xInitial = monstre.getX();
        int yInitial = monstre.getY();

        // Le monstre tente de se déplacer
        monstre.getDeplacementStrategie().deplacement(labyrinthe, monstre);

        // Vérifie que le monstre n'a pas bougé
        assertEquals(xInitial, monstre.getX(), "Le monstre ne devrait pas bouger en X");
        assertEquals(yInitial, monstre.getY(), "Le monstre ne devrait pas bouger en Y");
    }

    @Test
    void testDeplacementHasard() {
        Monstre monstre = new Monstre(2, 2);
        monstre.setDeplacementStrategie(new DeplacementHasard());

        int xInitial = monstre.getX();
        int yInitial = monstre.getY();

        // Le monstre tente de se déplacer 10 fois
        boolean aBouge = false;
        for (int i = 0; i < 10; i++) {
            monstre.getDeplacementStrategie().deplacement(labyrinthe, monstre);
            if (monstre.getX() != xInitial || monstre.getY() != yInitial) {
                aBouge = true;
                break;
            }
        }

        assertTrue(aBouge, "Le monstre devrait avoir bougé au moins une fois sur 10 tentatives");
    }


}