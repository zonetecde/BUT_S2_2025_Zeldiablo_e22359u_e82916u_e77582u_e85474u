package gameZeldiablo.Zeldiablo.Cases;

import javafx.scene.paint.Color;


/**
 * Classe représentant une case piège dans le jeu Zeldiablo
 */
public class CasePiege extends Case {
    private int degats;

        /**
        * Constructeur de CasePiege
        * @param x Position x de la case
        * @param y Position y de la case
        * @param degats Dégâts infligés par le piège
        */

     public CasePiege (int x, int y, int degats) {
         super(x, y, Color.RED, false);
         this.degats = 1;
     }
    public int getDegats() {
            return degats;
        }
     }


