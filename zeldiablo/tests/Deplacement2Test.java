import gameZeldiablo.Zeldiablo.Entities.Monstre;
import gameZeldiablo.Zeldiablo.Entities.Player;
import gameZeldiablo.Zeldiablo.Labyrinthe;
import gameZeldiablo.Zeldiablo.StrategieDeplacement.*;
import org.junit.jupiter.api.Test;

import java.io.IOException;

import static org.junit.jupiter.api.Assertions.*;

public class Deplacement2Test {
    private static final String CHEMIN_LABY_SANS_MUR = "labyTests/labySansMur.txt";
    private static final String CHEMIN_LABY_AVEC_MUR = "labyTests/labyAvecMur.txt";
    private static final String CHEMIN_LABY_BELLMAN = "labyTests/testBellmanFord.txt";

    @Test
    void testDeplacementRapprochementSansMur() throws IOException {
        Labyrinthe labyrinthe = new Labyrinthe(CHEMIN_LABY_SANS_MUR, null);

        // place le joueur en bas à droite
        Player joueur = new Player(8, 8, 5, 1);
        labyrinthe.setPlayer(joueur);
        labyrinthe.getMonstres().clear(); // vide la liste des monstres existants

        // place le monstre en haut à gauche avec stratégie de rapprochement
        Monstre monstre = new Monstre(2, 2);
        monstre.setDeplacementStrategie(new DeplacementRapprochement());
        labyrinthe.getMonstres().add(monstre);

        // position initiale
        int xInitial = monstre.getX();
        int yInitial = monstre.getY();

        // on fait bouger le monstre plusieurs fois pour s'assurer qu'il se déplace
        boolean aBouge = false;
        for (int i = 0; i < 5 && !aBouge; i++) {
            monstre.getDeplacementStrategie().deplacement(labyrinthe, monstre);
            aBouge = (monstre.getX() != xInitial || monstre.getY() != yInitial);
        }

        assertTrue(aBouge, "Le monstre aurait dû se déplacer vers le joueur");
    }

    @Test
    void testDeplacementIntelligentAvecMur() throws IOException {
        Labyrinthe labyrinthe = new Labyrinthe(CHEMIN_LABY_BELLMAN, null);

        // place le joueur de l'autre côté du mur
        Player joueur = new Player(1, 1, 5, 1);
        labyrinthe.setPlayer(joueur);
        labyrinthe.getMonstres().clear(); // Vide la liste des monstres existants

        // place le monstre avant le mur avec stratégie intelligente
        Monstre monstre = new Monstre(8, 5);
        monstre.setDeplacementStrategie(new DeplacementIntelligent());
        labyrinthe.getMonstres().add(monstre);

        // position initiale
        int xInitial = monstre.getX();
        int yInitial = monstre.getY();

        // on fait bouger le monstre plusieurs fois pour s'assurer qu'il contourne le mur
        boolean mouvement = true;
        monstre.getDeplacementStrategie().deplacement(labyrinthe, monstre);
        mouvement = (monstre.getX() != xInitial || monstre.getY() != yInitial);

        assertFalse(mouvement, "Le monstre aurait dû trouver un chemin pour contourner le mur");
    }
}