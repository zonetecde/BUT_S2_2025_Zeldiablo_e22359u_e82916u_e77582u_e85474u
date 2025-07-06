package Zeldiablo.Cases;

import Zeldiablo.VariablesGlobales;


public class CasePorte extends Case {
    public static int idset = 0;
    private boolean ouverte = false; // Indique si la porte est ouverte ou fermée
    /**
     * Constructeur de CasePorte
     *
     */
    public CasePorte() {
        // Au chargement du niveau la porte est fermée
        super(false, VariablesGlobales.SPRITE_PORTE_FERMEE);
        setActivable(true);
    }


    /**
     * Ouvre la porte
     */
    @Override
    public void activate() {
        this.ouverte = !this.ouverte;
        this.setIsWalkable(!this.getIsWalkable()); // Permet de marcher sur la case
        if (!ouverte) {this.setSprite(VariablesGlobales.SPRITE_PORTE_FERMEE);}
        else{this.setSprite(VariablesGlobales.SPRITE_PORTE_OUVERTE);}
    }



    @Override
    public void updateId(){
        this.setId(idset);
        idset++;
    }
}
