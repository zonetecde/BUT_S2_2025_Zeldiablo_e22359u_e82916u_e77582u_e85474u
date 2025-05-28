
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
    private static final String CHEMIN_LABY0 = "labySimple/laby0.txt";
    private static final String CHEMIN_LABY1 = "labySimple/laby1.txt";
    private static final String CHEMIN_LABY2 = "labySimple/laby2.txt";

    @Test
    void testConstructionLabyrintheSimple() throws IOException {
        labyrinthe = new Labyrinthe(CHEMIN_LABY0);

        assertEquals(5, labyrinthe.getHauteur(), "La hauteur devrait être 5");
        assertEquals(7, labyrinthe.getLongueur(), "La longueur devrait être 7");

        assertNotNull(labyrinthe.joueur, "Le joueur devrait être créé");
        assertEquals(3, labyrinthe.joueur.getX(), "Position X du joueur devrait être 3");
        assertEquals(2, labyrinthe.joueur.getY(), "Position Y du joueur devrait être 2");
    }

    @Test
    void testConstructionLabyrintheComplexe() throws IOException {
        labyrinthe = new Labyrinthe(CHEMIN_LABY1);

        assertEquals(7, labyrinthe.getHauteur(), "La hauteur devrait être 7");
        assertEquals(10, labyrinthe.getLongueur(), "La longueur devrait être 10");

        assertNotNull(labyrinthe.joueur, "Le joueur devrait être créé");
        assertEquals(5, labyrinthe.joueur.getX(), "Position X du joueur devrait être 5");
        assertEquals(5, labyrinthe.joueur.getY(), "Position Y du joueur devrait être 5");
    }

    @Test
    void testConstructionLabyrintheGrand() throws IOException {
        labyrinthe = new Labyrinthe(CHEMIN_LABY2);

        assertEquals(14, labyrinthe.getHauteur(), "La hauteur devrait être 14");
        assertEquals(20, labyrinthe.getLongueur(), "La longueur devrait être 20");

        assertNotNull(labyrinthe.joueur, "Le joueur devrait être créé");
        assertEquals(17, labyrinthe.joueur.getX(), "Position X du joueur devrait être 17");
        assertEquals(12, labyrinthe.joueur.getY(), "Position Y du joueur devrait être 12");
    }

    @Test
    void testTypesCases() throws IOException {
        labyrinthe = new Labyrinthe(CHEMIN_LABY0);

        Case caseMur = labyrinthe.getCase(0, 0);
        assertTrue(caseMur instanceof CaseMur, "La case (0,0) devrait être un mur");

        Case caseVide = labyrinthe.getCase(1, 1);
        assertTrue(caseVide instanceof CaseVide, "La case (1,1) devrait être vide");

        Case caseJoueur = labyrinthe.getCase(2, 3);
        assertTrue(caseJoueur instanceof CaseVide, "La case du joueur devrait être vide");
    }

    @Test
    void testDeplacementHaut() throws IOException {
        labyrinthe = new Labyrinthe(CHEMIN_LABY0);

        int xInitial = labyrinthe.joueur.getX();
        int yInitial = labyrinthe.joueur.getY();

        labyrinthe.deplacerPerso(Direction.HAUT, labyrinthe.joueur);

        assertEquals(xInitial, labyrinthe.joueur.getX(), "X ne devrait pas changer");
        assertEquals(yInitial - 1, labyrinthe.joueur.getY(), "Y devrait diminuer de 1");
    }

    @Test
    void testDeplacementBas() throws IOException {
        labyrinthe = new Labyrinthe(CHEMIN_LABY0);

        int xInitial = labyrinthe.joueur.getX();
        int yInitial = labyrinthe.joueur.getY();

        labyrinthe.deplacerPerso(Direction.BAS, labyrinthe.joueur);

        assertEquals(xInitial, labyrinthe.joueur.getX(), "X ne devrait pas changer");
        assertEquals(yInitial + 1, labyrinthe.joueur.getY(), "Y devrait augmenter de 1");
    }

    @Test
    void testDeplacementDroite() throws IOException {
        labyrinthe = new Labyrinthe(CHEMIN_LABY0);

        int xInitial = labyrinthe.joueur.getX();
        int yInitial = labyrinthe.joueur.getY();

        labyrinthe.deplacerPerso(Direction.DROITE, labyrinthe.joueur);

        assertEquals(xInitial + 1, labyrinthe.joueur.getX(), "X devrait augmenter de 1");
        assertEquals(yInitial, labyrinthe.joueur.getY(), "Y ne devrait pas changer");
    }

    @Test
    void testDeplacementGauche() throws IOException {
        labyrinthe = new Labyrinthe(CHEMIN_LABY0);

        int xInitial = labyrinthe.joueur.getX();
        int yInitial = labyrinthe.joueur.getY();

        labyrinthe.deplacerPerso(Direction.GAUCHE, labyrinthe.joueur);

        assertEquals(xInitial - 1, labyrinthe.joueur.getX(), "X devrait diminuer de 1");
        assertEquals(yInitial, labyrinthe.joueur.getY(), "Y ne devrait pas changer");
    }

    @Test
    void testCollisionMur() throws IOException {
        labyrinthe = new Labyrinthe(CHEMIN_LABY0);

        // Déplacer le joueur contre le mur du haut (position initiale: x=3, y=2)
        labyrinthe.deplacerPerso(Direction.HAUT, labyrinthe.joueur); // y=1

        int xAvantCollision = labyrinthe.joueur.getX();
        int yAvantCollision = labyrinthe.joueur.getY();

        // Tentative de déplacement vers le mur du haut
        labyrinthe.deplacerPerso(Direction.HAUT, labyrinthe.joueur);

        // Le joueur ne devrait pas avoir bougé
        assertEquals(xAvantCollision, labyrinthe.joueur.getX(), "X ne devrait pas changer lors d'une collision");
        assertEquals(yAvantCollision, labyrinthe.joueur.getY(), "Y ne devrait pas changer lors d'une collision");
    }

    @Test
    void testCollisionLimites() throws IOException {
        labyrinthe = new Labyrinthe(CHEMIN_LABY0);

        labyrinthe.joueur.setX(6);
        labyrinthe.joueur.setY(2);

        int xAvantCollision = labyrinthe.joueur.getX();
        int yAvantCollision = labyrinthe.joueur.getY();

        labyrinthe.deplacerPerso(Direction.DROITE, labyrinthe.joueur);

        assertEquals(xAvantCollision, labyrinthe.joueur.getX(), "X ne devrait pas changer lors d'une collision avec les limites");
        assertEquals(yAvantCollision, labyrinthe.joueur.getY(), "Y ne devrait pas changer lors d'une collision avec les limites");
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
    void testEtreFini() throws IOException {
        labyrinthe = new Labyrinthe(CHEMIN_LABY0);

        assertFalse(labyrinthe.etreFini(), "Le jeu ne devrait jamais être fini");
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
        assertEquals(3, labyrinthe.joueur.getX());
        assertEquals(2, labyrinthe.joueur.getY());

        labyrinthe.deplacerPerso(Direction.DROITE, labyrinthe.joueur);
        assertEquals(4, labyrinthe.joueur.getX());
        assertEquals(2, labyrinthe.joueur.getY());

        labyrinthe.deplacerPerso(Direction.DROITE, labyrinthe.joueur);
        assertEquals(5, labyrinthe.joueur.getX());
        assertEquals(2, labyrinthe.joueur.getY());

        labyrinthe.deplacerPerso(Direction.BAS, labyrinthe.joueur);
        assertEquals(5, labyrinthe.joueur.getX());
        assertEquals(3, labyrinthe.joueur.getY());
    }


    @Test
    void testCreationJoueur() throws IOException {
        labyrinthe = new Labyrinthe(CHEMIN_LABY0);

        assertNotNull(labyrinthe.joueur, "Le joueur devrait être créé");
        assertTrue(labyrinthe.joueur instanceof Player, "Le joueur devrait être une instance de Player");
    }
}