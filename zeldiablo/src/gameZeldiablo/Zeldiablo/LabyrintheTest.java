package gameZeldiablo.Zeldiablo;
import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.*;
import java.io.*;

class LabyrintheTest {

    private static final String LABY_FILE = "test_laby.txt";

    @BeforeAll
    static void setupFile() throws IOException {
        // Crée un fichier temporaire pour les tests
        try (PrintWriter out = new PrintWriter(LABY_FILE)) {
            out.println("3");
            out.println("3");
            out.println("XXX");
            out.println("XP.");
            out.println("X.X");
        }
    }

    @AfterAll
    static void cleanupFile() {
        new File(LABY_FILE).delete();
    }

    @Test
    void testChargementLabyrinthe() throws IOException {
        Labyrinthe laby = new Labyrinthe(LABY_FILE);
        assertEquals(3, laby.getHauteur());
        assertEquals(3, laby.getLongueur());
        assertNotNull(laby.joueur);
        assertTrue(laby.getCase(1, 1) instanceof CaseVide);
        assertTrue(laby.getCase(0, 0) instanceof CaseMur);
    }

    @Test
    void testDeplacementPersoDansVide() throws IOException {
        Labyrinthe laby = new Labyrinthe(LABY_FILE);
        Entite joueur = laby.joueur;
        int yInit = joueur.y;
        int xInit = joueur.x;
        laby.deplacerPerso(Direction.DROITE, joueur);
        assertEquals(xInit + 1, joueur.x);
        assertEquals(yInit, joueur.y);
    }

    @Test
    void testDeplacementPersoContreMur() throws IOException {
        Labyrinthe laby = new Labyrinthe(LABY_FILE);
        Entite joueur = laby.joueur;
        int yInit = joueur.y;
        int xInit = joueur.x;
        laby.deplacerPerso(Direction.GAUCHE, joueur); // Mur à gauche
        assertEquals(xInit, joueur.x);
        assertEquals(yInit, joueur.y);
    }

    @Test
    void testDeplacementPersoHorsLimite() throws IOException {
        Labyrinthe laby = new Labyrinthe(LABY_FILE);
        Entite joueur = laby.joueur;
        joueur.y = 0; joueur.x = 1;
        laby.deplacerPerso(Direction.HAUT, joueur); // Hors limite
        assertEquals(0, joueur.y);
        assertEquals(1, joueur.x);
    }
}