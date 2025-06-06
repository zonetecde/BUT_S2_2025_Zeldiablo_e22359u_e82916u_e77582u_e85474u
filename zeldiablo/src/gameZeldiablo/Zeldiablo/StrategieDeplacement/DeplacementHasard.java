package gameZeldiablo.Zeldiablo.StrategieDeplacement;

import gameZeldiablo.Zeldiablo.Direction;
import gameZeldiablo.Zeldiablo.Entities.Monstre;
import gameZeldiablo.Zeldiablo.Labyrinthe;

import java.io.Serializable;

public class DeplacementHasard implements DeplacementStrategie, Serializable {
    /**
     * Déplace l'entité selon la stratégie de déplacement aléatoire.
     * @param laby
     * @param monstre
     */
    @Override
    public void deplacement(Labyrinthe laby, Monstre monstre) {
        Direction[] directions = Direction.values();
        Direction directionChoisie = directions[(int)(Math.random() * directions.length)];

        // calcule des coordonnées de la case suivante
        int[] prochainePosition = Labyrinthe.getSuivant(monstre.getY(), monstre.getX(), directionChoisie);

        // vérifie si le déplacement est possible
        if (laby.canEntityMoveTo(prochainePosition[0], prochainePosition[1])) {
            // déplacement
            monstre.setPosition(prochainePosition[0], prochainePosition[1]);
        }
    }
}