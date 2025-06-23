package gameZeldiablo.Zeldiablo.Entities;

import gameZeldiablo.Zeldiablo.Cases.Case;
import gameZeldiablo.Zeldiablo.Direction;
import gameZeldiablo.Zeldiablo.StrategieDeplacement.DeplacementStrategie;
import gameZeldiablo.Zeldiablo.StrategieDeplacement.DeplacementStrategieFactory;
import gameZeldiablo.Zeldiablo.Labyrinthe;
import gameZeldiablo.Zeldiablo.VariablesGlobales;

import java.util.ArrayList;

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
    public Monstre(int x, int y, double pv, double degat, Intelligence intelligence,Labyrinthe l) {
        super(x, y, pv, degat,VariablesGlobales.SPRITE_MONSTRE[intelligence.ordinal()],l);
        this.i=intelligence;
        this.deplacementStrategie = DeplacementStrategieFactory.creerStrategie(intelligence);
    }

    /**
     *  Constructeur 2
     */
    public Monstre(int x, int y, Intelligence intelligence,Labyrinthe l) {
        super(x, y, 3, VariablesGlobales.DEGAT_BASE,VariablesGlobales.SPRITE_MONSTRE[intelligence.ordinal()],l);
        this.i=intelligence;
        this.deplacementStrategie = DeplacementStrategieFactory.creerStrategie(intelligence);
    }

    /**
     * Constructeur de la classe Monstre avec des valeurs par défaut.
     * @param x La position en x du monstre.
     * @param y La position en y du monstre.
     */
    public Monstre(int x, int y) {
        super(x, y, 3, VariablesGlobales.DEGAT_BASE,VariablesGlobales.SPRITE_MONSTRE[(int)(Math.random()*VariablesGlobales.SPRITE_MONSTRE.length)],null);
        this.i=Intelligence.MOYENNE;
        this.deplacementStrategie = DeplacementStrategieFactory.creerStrategie(Intelligence.MOYENNE);
    }

    /**
     * Methode pour deplacer le monstre selon sa strategie
     */
    public void deplacer(Direction action, Entite p){
        Entite tmp = getLabyrinthe().getJoueurs().get(0);
        deplacementStrategie.deplacement((Player)tmp,this);
        for (Entite joueur : getLabyrinthe().getJoueurs()) {
            if (this.aCote(joueur)) {
                //etat visuelle
                this.mettreDegat(joueur);
            }
        }

        // déclanche le onstepon de la case où se trouve le monstre
        Case caseMonstre = getLabyrinthe().getCase(this.getY(), this.getX());
        caseMonstre.onStepOn(this);

        if (this.estMort()){
            getLabyrinthe().getEntites().remove(this);
        }
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
