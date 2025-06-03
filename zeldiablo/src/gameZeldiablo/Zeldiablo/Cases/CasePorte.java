package gameZeldiablo.Zeldiablo.Cases;

import gameZeldiablo.Zeldiablo.VariablesGlobales;
import javafx.scene.paint.Color;

public class CasePorte extends Case {
    private boolean ouverte = false; // Indique si la porte est ouverte ou fermée
        
    /**
     * Constructeur de CasePorte
     *
     * @param x position x de la case
     * @param y position y de la case
     */
    public CasePorte(int x, int y) {
        // Au chargement du niveau la porte est fermée
        super(x, y, Color.BLACK, false, VariablesGlobales.SPRITE_PORTE);
    }

    /**
     * Ouvre la porte
     */
    public void ouvrir() {
        if (!ouverte) {
            this.ouverte = true;
            this.setCouleur(Color.WHITE); // Change la couleur pour indiquer que la porte est ouverte
            this.setIsWalkable(true); // Permet de marcher sur la case
        }
    }
}
