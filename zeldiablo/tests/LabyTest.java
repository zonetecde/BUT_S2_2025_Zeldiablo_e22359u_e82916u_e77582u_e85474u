
import gameZeldiablo.Zeldiablo.Cases.Case;
import gameZeldiablo.Zeldiablo.Cases.CaseMur;
import gameZeldiablo.Zeldiablo.Cases.CaseVide;
import gameZeldiablo.Zeldiablo.Direction;
import gameZeldiablo.Zeldiablo.Entities.Player;
import gameZeldiablo.Zeldiablo.Labyrinthe;
import org.junit.jupiter.api.Test;

import java.io.IOException;

import static org.junit.jupiter.api.Assertions.*;

class LabyTest {

    private Labyrinthe labyrinthe;
    private static final String CHEMIN_LABY0 = "Laby/labyTests/laby0.txt";
    private static final String CHEMIN_LABY1 = "Laby/labyTests/laby1.txt";
    private static final String CHEMIN_LABY2 = "Laby/labyTests/laby2.txt";

    @Test
    void testConstructionLabyrintheSimple() throws IOException {
        labyrinthe = new Labyrinthe(CHEMIN_LABY0);

        assertEquals(5, labyrinthe.getHauteur(), "La hauteur devrait être 5");
        assertEquals(7, labyrinthe.getLongueur(), "La longueur devrait être 7");

        assertNotNull(labyrinthe.getJoueurs(), "Le joueur devrait être créé");
        assertEquals(3, labyrinthe.getJoueurs().getX(), "Position X du joueur devrait être 3");
        assertEquals(2, labyrinthe.getJoueurs().getY(), "Position Y du joueur devrait être 2");
    }

    @Test
    void testConstructionLabyrintheComplexe() throws IOException {
        labyrinthe = new Labyrinthe(CHEMIN_LABY1);

        assertEquals(7, labyrinthe.getHauteur(), "La hauteur devrait être 7");
        assertEquals(10, labyrinthe.getLongueur(), "La longueur devrait être 10");

        assertNotNull(labyrinthe.getJoueurs(), "Le joueur devrait être créé");
        assertEquals(5, labyrinthe.getJoueurs().getX(), "Position X du joueur devrait être 5");
        assertEquals(5, labyrinthe.getJoueurs().getY(), "Position Y du joueur devrait être 5");
    }

    @Test
    void testConstructionLabyrintheGrand() throws IOException {
        labyrinthe = new Labyrinthe(CHEMIN_LABY2);

        assertEquals(14, labyrinthe.getHauteur(), "La hauteur devrait être 14");
        assertEquals(20, labyrinthe.getLongueur(), "La longueur devrait être 20");

        assertNotNull(labyrinthe.getJoueurs(), "Le joueur devrait être créé");
        assertEquals(17, labyrinthe.getJoueurs().getX(), "Position X du joueur devrait être 17");
        assertEquals(12, labyrinthe.getJoueurs().getY(), "Position Y du joueur devrait être 12");
    }

    @Test
    void testTypesCases() throws IOException {
        labyrinthe = new Labyrinthe(CHEMIN_LABY0);

        Case caseMur = labyrinthe.getCase(0, 0);
        assertTrue(caseMur instanceof CaseMur, "La case (0,0) devrait être un mur");

        Case caseVide = labyrinthe.getCase(1, 1);
        assertTrue(caseVide instanceof CaseVide, "La case (1,1) devrait être vide");
    }

    @Test
    void testDeplacementHaut() throws IOException {
        labyrinthe = new Labyrinthe(CHEMIN_LABY0);

        int xInitial = labyrinthe.getJoueurs().getX();
        int yInitial = labyrinthe.getJoueurs().getY();

        labyrinthe.deplacerPerso(Direction.HAUT, labyrinthe.getJoueurs());

        assertEquals(xInitial, labyrinthe.getJoueurs().getX(), "X ne devrait pas changer");
        assertEquals(yInitial - 1, labyrinthe.getJoueurs().getY(), "Y devrait diminuer de 1");
    }

    @Test
    void testDeplacementBas() throws IOException {
        labyrinthe = new Labyrinthe(CHEMIN_LABY0);

        int xInitial = labyrinthe.getJoueurs().getX();
        int yInitial = labyrinthe.getJoueurs().getY();

        labyrinthe.deplacerPerso(Direction.BAS, labyrinthe.getJoueurs());

        assertEquals(xInitial, labyrinthe.getJoueurs().getX(), "X ne devrait pas changer");
    }

    @Test
    void testDeplacementDroite() throws IOException {
        labyrinthe = new Labyrinthe(CHEMIN_LABY0);

        int xInitial = labyrinthe.getJoueurs().getX();
        int yInitial = labyrinthe.getJoueurs().getY();

        labyrinthe.deplacerPerso(Direction.DROITE, labyrinthe.getJoueurs());

        assertEquals(xInitial + 1, labyrinthe.getJoueurs().getX(), "X devrait augmenter de 1");
        assertEquals(yInitial, labyrinthe.getJoueurs().getY(), "Y ne devrait pas changer");
    }

    @Test
    void testDeplacementGauche() throws IOException {
        labyrinthe = new Labyrinthe(CHEMIN_LABY0);

        int xInitial = labyrinthe.getJoueurs().getX();
        int yInitial = labyrinthe.getJoueurs().getY();

        labyrinthe.deplacerPerso(Direction.GAUCHE, labyrinthe.getJoueurs());

        assertEquals(xInitial - 1, labyrinthe.getJoueurs().getX(), "X devrait diminuer de 1");
        assertEquals(yInitial, labyrinthe.getJoueurs().getY(), "Y ne devrait pas changer");
    }

    @Test
    void testCollisionMur() throws IOException {
        labyrinthe = new Labyrinthe(CHEMIN_LABY0);

        // Déplacer le joueur contre le mur du haut (position initiale: x=3, y=2)
        labyrinthe.deplacerPerso(Direction.HAUT, labyrinthe.getJoueurs()); // y=1

        int xAvantCollision = labyrinthe.getJoueurs().getX();
        int yAvantCollision = labyrinthe.getJoueurs().getY();

        // Tentative de déplacement vers le mur du haut
        labyrinthe.deplacerPerso(Direction.HAUT, labyrinthe.getJoueurs());

        // Le joueur ne devrait pas avoir bougé
        assertEquals(xAvantCollision, labyrinthe.getJoueurs().getX(), "X ne devrait pas changer lors d'une collision");
        assertEquals(yAvantCollision, labyrinthe.getJoueurs().getY(), "Y ne devrait pas changer lors d'une collision");
    }

    @Test
    void testCollisionLimites() throws IOException {
        labyrinthe = new Labyrinthe(CHEMIN_LABY0);

        labyrinthe.getJoueurs().setX(6);
        labyrinthe.getJoueurs().setY(2);

        int xAvantCollision = labyrinthe.getJoueurs().getX();
        int yAvantCollision = labyrinthe.getJoueurs().getY();

        labyrinthe.deplacerPerso(Direction.DROITE, labyrinthe.getJoueurs());

        assertEquals(xAvantCollision, labyrinthe.getJoueurs().getX(), "X ne devrait pas changer lors d'une collision avec les limites");
        assertEquals(yAvantCollision, labyrinthe.getJoueurs().getY(), "Y ne devrait pas changer lors d'une collision avec les limites");
    }

    @Test
    void testGetSuivant() {
        int[] resultat = Labyrinthe.getSuivant(5, 3, Direction.HAUT);
        assertArrayEquals(new int[]{4, 3}, resultat, "Déplacement vers le haut incorrect");

        resultat = Labyrinthe.getSuivant(5, 3, Direction.BAS);
        assertArrayEquals(new int[]{6, 3}, resultat, "Déplacement vers le bas incorrect");

        resultat = Labyrinthe.getSuivant(5, 3, Direction.DROITE);
        assertArrayEquals(new int[]{5, 4}, resultat, "Déplacement vers la droite incorrect");

        resultat = Labyrinthe.getSuivant(5, 3, Direction.GAUCHE);
        assertArrayEquals(new int[]{5, 2}, resultat, "Déplacement vers la gauche incorrect");
    }


    @Test
    void testGetCaseValides() throws IOException {
        labyrinthe = new Labyrinthe(CHEMIN_LABY0);

        Case case00 = labyrinthe.getCase(0, 0);
        assertNotNull(case00, "La case (0,0) ne devrait pas être null");

        Case case11 = labyrinthe.getCase(1, 1);
        assertNotNull(case11, "La case (1,1) ne devrait pas être null");
    }

    @Test
    void testDimensions() throws IOException {
        // Test avec laby0
        labyrinthe = new Labyrinthe(CHEMIN_LABY0);
        assertEquals(5, labyrinthe.getHauteur(), "Hauteur laby0 incorrecte");
        assertEquals(7, labyrinthe.getLongueur(), "Longueur laby0 incorrecte");

        // Test avec laby1
        labyrinthe = new Labyrinthe(CHEMIN_LABY1);
        assertEquals(7, labyrinthe.getHauteur(), "Hauteur laby1 incorrecte");
        assertEquals(10, labyrinthe.getLongueur(), "Longueur laby1 incorrecte");

        // Test avec laby2
        labyrinthe = new Labyrinthe(CHEMIN_LABY2);
        assertEquals(14, labyrinthe.getHauteur(), "Hauteur laby2 incorrecte");
        assertEquals(20, labyrinthe.getLongueur(), "Longueur laby2 incorrecte");
    }

    @Test
    void testFichierInexistant() {
        assertThrows(IOException.class, () -> {
            new Labyrinthe("fichier_inexistant.txt");
        }, "Une IOException devrait être levée pour un fichier inexistant");
    }

    @Test
    void testSequenceDeplacements() throws IOException {
        labyrinthe = new Labyrinthe(CHEMIN_LABY0);

        // Position initiale du joueur dans laby0: x=3, y=2
        assertEquals(3, labyrinthe.getJoueurs().getX());
        assertEquals(2, labyrinthe.getJoueurs().getY());

        labyrinthe.deplacerPerso(Direction.DROITE, labyrinthe.getJoueurs());
        assertEquals(4, labyrinthe.getJoueurs().getX());
        assertEquals(2, labyrinthe.getJoueurs().getY());

        labyrinthe.deplacerPerso(Direction.DROITE, labyrinthe.getJoueurs());
        assertEquals(5, labyrinthe.getJoueurs().getX());
        assertEquals(2, labyrinthe.getJoueurs().getY());

        labyrinthe.deplacerPerso(Direction.BAS, labyrinthe.getJoueurs());
        assertEquals(5, labyrinthe.getJoueurs().getX());
        assertEquals(3, labyrinthe.getJoueurs().getY());
    }


    @Test
    void testCreationJoueur() throws IOException {
        labyrinthe = new Labyrinthe(CHEMIN_LABY0);

        assertNotNull(labyrinthe.getJoueurs(), "Le joueur devrait être créé");
        assertInstanceOf(Player.class, labyrinthe.getJoueurs(), "Le joueur devrait être une instance de Player");
    }
}