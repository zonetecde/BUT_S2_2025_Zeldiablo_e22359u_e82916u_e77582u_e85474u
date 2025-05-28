import gameZeldiablo.Zeldiablo.Entities.Player;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Classe de test pour la classe Entite (via Player qui hérite d'Entite).
 */
class EntiteTest {

    private Player joueur;

    @BeforeEach
    void setUp() {
        joueur = new Player(5, 5, 10);
    }

    /**
     * Test de la méthode prendreDegat() avec des dégâts qui ne le tue pas.
     * Vérifie que les PV diminuent correctement sans tuer le joueur.
     */
    @Test
    void testPrendreDegatsNonLetaux() {
        int pvInitiaux = joueur.getHp();
        
        // Le joueur prend 3 dégâts
        joueur.prendreDegat(3);
        
        assertEquals(pvInitiaux - 3, joueur.getHp(), 
            "Les PV devraient diminuer de 3 après avoir pris 3 dégâts");
        assertTrue(joueur.estMort(), 
            "Le joueur devrait encore être en vie après avoir pris des dégâts non-létaux");
    }

    @Test
    void testPrendreDegatsEtMort() {
        // Le joueur prend des dégâts supérieurs à ses PV
        joueur.prendreDegat(15);
        
        assertEquals(0, joueur.getHp(), 
            "Les PV devraient être à 0 après avoir pris des dégâts > vie");
        assertFalse(joueur.estMort(), 
            "Le joueur devrait être mort après avoir pris des dégâts > vie");
    }

    /**
     * Test de la méthode estMort().
     * Vérifie que la méthode retourne l'état correct du joueur.
     */
    @Test
    void testEstMort() {
        // Au début, le joueur est vivant
        assertTrue(joueur.estMort(), 
            "Le joueur devrait être en vie au début");
        
        // Après avoir pris des dégâts >= vie, il devrait être mort
        joueur.prendreDegat(joueur.getHp() + 1);
        assertFalse(joueur.estMort(), 
            "Le joueur devrait être mort après avoir pris des dégâts >= vie");
    }
}
