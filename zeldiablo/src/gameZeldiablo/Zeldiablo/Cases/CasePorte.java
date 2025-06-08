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
    public CasePorte() {
        // Au chargement du niveau la porte est fermée
        super(false, VariablesGlobales.SPRITE_PORTE_FERMEE);
    }


    /**
     * Ouvre la porte
     */
    @Override
    public void activate() {
        this.ouverte = !this.ouverte;
        this.setIsWalkable(!this.getIsWalkable()); // Permet de marcher sur la case
        if (ouverte) {this.setSprite(VariablesGlobales.SPRITE_PORTE_FERMEE);}
        else{this.setSprite(VariablesGlobales.SPRITE_PORTE_OUVERTE);}
    }
}
