package gameZeldiablo.Zeldiablo.Cases;

import gameZeldiablo.Zeldiablo.VariablesGlobales;
import javafx.scene.paint.Color;


/**
 * Classe représentant une case piège dans le jeu Zeldiablo
 */
public class CasePiege extends Case {
    private int degats;

    /**
     * Constructeur de CasePiege
     *
     * @param x      Position x de la case
     * @param y      Position y de la case
     * @param degats Dégâts infligés par le piège
     */

    public CasePiege(int x, int y, int degats) {
         super(x, y, VariablesGlobales.COULEUR_PIEGE, true);
        this.degats = degats;
    }

    public int getDegats() {
        return degats;
    }

    @Override
    public void onStepOn() {
        VariablesGlobales.joueur.prendreDegat(degats);
    }
}


