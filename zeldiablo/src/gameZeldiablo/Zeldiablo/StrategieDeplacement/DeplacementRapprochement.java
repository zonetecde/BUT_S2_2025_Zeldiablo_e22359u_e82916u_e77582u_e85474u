package gameZeldiablo.Zeldiablo.StrategieDeplacement;

import gameZeldiablo.Zeldiablo.Direction;
import gameZeldiablo.Zeldiablo.Labyrinthe;
import gameZeldiablo.Zeldiablo.Entities.Monstre;

public class DeplacementRapprochement implements DeplacementStrategie {
    @Override
    public void deplacement(Labyrinthe labyrinthe, Monstre monstre) {
        // Déplacement vers le joueur sans prendre en compte les obstacles
        // vérifie que labytinthe.canEntityMoveTo avant de déplacer le monstre
        int[] prochainePosition = Labyrinthe.getSuivant(monstre.getY(), monstre.getX(), Direction.DROITE);
        if (labyrinthe.canEntityMoveTo(prochainePosition[0], prochainePosition[1])) {
            monstre.setPosition(prochainePosition[0], prochainePosition[1]);
        } else {
            prochainePosition = Labyrinthe.getSuivant(monstre.getY(), monstre.getX(), Direction.BAS);
            if (labyrinthe.canEntityMoveTo(prochainePosition[0], prochainePosition[1])) {
                monstre.setPosition(prochainePosition[0], prochainePosition[1]);
            } else {
                prochainePosition = Labyrinthe.getSuivant(monstre.getY(), monstre.getX(), Direction.GAUCHE);
                if (labyrinthe.canEntityMoveTo(prochainePosition[0], prochainePosition[1])) {
                    monstre.setPosition(prochainePosition[0], prochainePosition[1]);
                } else {
                    prochainePosition = Labyrinthe.getSuivant(monstre.getY(), monstre.getX(), Direction.HAUT);
                    if (labyrinthe.canEntityMoveTo(prochainePosition[0], prochainePosition[1])) {
                        monstre.setPosition(prochainePosition[0], prochainePosition[1]);
                    }
                }
            }
        }
    }
}
