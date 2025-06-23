package gameZeldiablo.Zeldiablo.Entities;

import gameZeldiablo.Zeldiablo.StrategieDeplacement.DeplacementStrategie;
import gameZeldiablo.Zeldiablo.StrategieDeplacement.DeplacementStrategieFactory;
import gameZeldiablo.Zeldiablo.Labyrinthe;
import gameZeldiablo.Zeldiablo.VariablesGlobales;

/**
 * Classe des mechants très très mechants
 */
public class Monstre extends Entite implements Cloneable{
    private DeplacementStrategie deplacementStrategie;
    private Intelligence i;

    /**
     * Constructeur de la classe Monstre.
     * @param x La position en x du monstre.
     * @param y La position en y du monstre.
     * @param pv Les points de vie du monstre.
     * @param degat Les dégâts infligés par le monstre.
     * @param intelligence L'intelligence du monstre, qui détermine sa stratégie de déplacement.
     * */
    public Monstre(int x, int y, double pv, double degat, Intelligence intelligence) {
        super(x, y, pv, degat,VariablesGlobales.SPRITE_MONSTRE[intelligence.ordinal()]);
        this.i=intelligence;
        this.deplacementStrategie = DeplacementStrategieFactory.creerStrategie(intelligence);
    }

    /**
     *  Constructeur 2
     */
    public Monstre(int x, int y, Intelligence intelligence) {
        super(x, y, 3, VariablesGlobales.DEGAT_BASE,VariablesGlobales.SPRITE_MONSTRE[intelligence.ordinal()]);
        this.i=intelligence;
        this.deplacementStrategie = DeplacementStrategieFactory.creerStrategie(intelligence);
    }

    /**
     * Constructeur de la classe Monstre avec des valeurs par défaut.
     * @param x La position en x du monstre.
     * @param y La position en y du monstre.
     */
    public Monstre(int x, int y) {
        super(x, y, 3, VariablesGlobales.DEGAT_BASE,VariablesGlobales.SPRITE_MONSTRE[(int)(Math.random()*VariablesGlobales.SPRITE_MONSTRE.length)]);
        this.i=Intelligence.MOYENNE;
        this.deplacementStrategie = DeplacementStrategieFactory.creerStrategie(Intelligence.MOYENNE);
    }

    /**
     * Methode pour deplacer le monstre selon sa strategie
     * @param joueur laby contenant le monstre
     */
    public void deplacer(Player joueur){
        deplacementStrategie.deplacement(joueur,this);
    }

    /**
     * getter de la strategie
     * @return strategie
     */
    public DeplacementStrategie getDeplacementStrategie() {
        return deplacementStrategie;
    }

    /**
     * setter de la strategie
     * @param deplacementStrategie nouvelle strat
     */
    public void setDeplacementStrategie(DeplacementStrategie deplacementStrategie) {
        this.deplacementStrategie = deplacementStrategie;
    }

    @Override
    public Monstre clone(){
        return (Monstre)super.clone();
    }

}
