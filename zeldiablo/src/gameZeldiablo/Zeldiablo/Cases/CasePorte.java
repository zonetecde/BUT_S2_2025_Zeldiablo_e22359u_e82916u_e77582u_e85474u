package gameZeldiablo.Zeldiablo.Cases;

import gameZeldiablo.Zeldiablo.VariablesGlobales;


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
        super(x, y, false, VariablesGlobales.SPRITE_PORTE_FERMEE);
    }

    /**
     * Ouvre la porte
     */
    public void ouvrir() {
        if (!ouverte) {
            this.ouverte = true;
            this.setIsWalkable(true); // Permet de marcher sur la case
            this.setImg(VariablesGlobales.SPRITE_PORTE_OUVERTE);
        }
    }
}
