

import gameZeldiablo.Zeldiablo.Cases.CasePiege;
import gameZeldiablo.Zeldiablo.Entities.Player;
import gameZeldiablo.Zeldiablo.Labyrinthe;
import gameZeldiablo.Zeldiablo.Direction;
import gameZeldiablo.Zeldiablo.VariablesGlobales;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.IOException;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Classe de test pour la classe CasePiege.
 */

class CasePiegeTest {

    private Labyrinthe labyrinthe;
    private static final String CHEMIN_LABY_PIEGE = "labyTests/laby0.txt";

    @BeforeEach
    void setUp() throws IOException {
        labyrinthe = new Labyrinthe(CHEMIN_LABY_PIEGE);
    }

    /**
     * Test de la construction d'une case piège.
     * Vérifie que les dégâts sont correctement initialisés et que la case est marchable.
     */
    @Test
    void testConstructionCasePiege() {
        CasePiege casePiege = new CasePiege(1, 1, 3);
        assertEquals(3, casePiege.getDegats(), "Les dégâts du piège devraient être de 3");
        assertTrue(casePiege.getIsWalkable(), "La case piège devrait être marchable");
    }

    /**
     * Test de la méthode onStepOn() de la case piège.
     * Vérifie que le joueur subit les dégâts lorsqu'il marche sur la case piège.
     */
    @Test
    void testDegatsInfliges() throws IOException {
        // On récupère le joueur
        Player joueur = labyrinthe.getPlayer();
        double pvInitiaux = joueur.getHp();

        // On déplace le joueur sur la case piège
        labyrinthe.deplacerPerso(Direction.DROITE, joueur);
        labyrinthe.deplacerPerso(Direction.DROITE, joueur);
        labyrinthe.deplacerPerso(Direction.BAS, joueur);

        // Vérification que les dégâts ont été infligés
        assertEquals(pvInitiaux - 3, joueur.getHp(),
                "Le joueur devrait avoir perdu 1 PV en marchant sur le piège");
    }

}