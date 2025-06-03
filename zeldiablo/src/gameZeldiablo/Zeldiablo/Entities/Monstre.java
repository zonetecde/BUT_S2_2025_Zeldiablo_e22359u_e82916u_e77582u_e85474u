package gameZeldiablo.Zeldiablo.Entities;

import gameZeldiablo.Zeldiablo.StrategieDeplacement.DeplacementStrategie;
import gameZeldiablo.Zeldiablo.Labyrinthe;
import gameZeldiablo.Zeldiablo.VariablesGlobales;

public class Monstre extends Entite {
    private DeplacementStrategie deplacementStrategie;

    /**
     * Constructeur de la classe Monstre.
     * @param x La position en x du monstre.
     * @param y La position en y du monstre.
     * @param pv Les points de vie du monstre.
     * @param degat Les dégâts infligés par le monstre.
     * @param intelligence L'intelligence du monstre, qui détermine sa stratégie de déplacement.
     * */
    public Monstre(int x, int y, double pv, double degat, Intelligence intelligence) {
        super(x, y, pv, degat);
        
        setIntelligence(intelligence);
    }

    /**
     * Définit la stratégie de déplacement du monstre en fonction de son intelligence.
     * @param intelligence L'intelligence du monstre, qui détermine sa stratégie de déplacement.
     */
    private void setIntelligence(Intelligence intelligence) {
        switch(intelligence) {
            case NULLE:
                this.deplacementStrategie = new gameZeldiablo.Zeldiablo.StrategieDeplacement.DeplacementStatique();
                break;
            case FAIBLE:
                this.deplacementStrategie = new gameZeldiablo.Zeldiablo.StrategieDeplacement.DeplacementHasard();
                break;
            case MOYENNE:
                this.deplacementStrategie = new gameZeldiablo.Zeldiablo.StrategieDeplacement.DeplacementRapprochement();
                break;
            case FORTE:
                this.deplacementStrategie = new gameZeldiablo.Zeldiablo.StrategieDeplacement.DeplacementIntelligent();
                break;
            default:
                throw new IllegalArgumentException("Intelligence non reconnue: " + intelligence);
        }
    }

    /**
     *  Constructeur 2
     * 
     */
    public Monstre(int x, int y, Intelligence intelligence) {
        super(x, y, 3, VariablesGlobales.DEGAT_BASE);
        setIntelligence(intelligence);
    }

    /**
     * Constructeur de la classe Monstre avec des valeurs par défaut.
     * @param x La position en x du monstre.
     * @param y La position en y du monstre.
     */
    public Monstre(int x, int y) {
        super(x, y, 3, VariablesGlobales.DEGAT_BASE);
        setIntelligence(Intelligence.MOYENNE);
    }

    public void deplacer(Labyrinthe laby){
        deplacementStrategie.deplacement(laby, this);
    }

    public DeplacementStrategie getDeplacementStrategie() {
        return deplacementStrategie;
    }

    public void setDeplacementStrategie(DeplacementStrategie deplacementStrategie) {
        this.deplacementStrategie = deplacementStrategie;
    }
}
