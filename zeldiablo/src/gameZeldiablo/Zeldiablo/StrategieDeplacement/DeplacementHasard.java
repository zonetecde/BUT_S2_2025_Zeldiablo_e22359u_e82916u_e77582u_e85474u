package gameZeldiablo.Zeldiablo.StrategieDeplacement;

import gameZeldiablo.Zeldiablo.Entities.Monstre;
import gameZeldiablo.Zeldiablo.Labyrinthe;
import gameZeldiablo.Zeldiablo.StrategieDeplacement.DeplacementStrategie;

public class DeplacementHasard implements DeplacementStrategie {


    public void deplacement(Labyrinthe laby, Monstre monstre) {
        int dx = (int) (Math.random() * 3) - 1; // -1, 0, ou 1
        int dy = (int) (Math.random() * 3) - 1; // -1, 0, ou 1

        if (dx == 0 && dy == 0) {
            dx = 1;
        }

        monstre.setX(dx);
        monstre.setY(dy);

    }
}

