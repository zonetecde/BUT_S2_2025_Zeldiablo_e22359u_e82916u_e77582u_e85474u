package gameZeldiablo.Zeldiablo.Entities;

import gameZeldiablo.Zeldiablo.Cases.Case;
import gameZeldiablo.Zeldiablo.Direction;
import gameZeldiablo.Zeldiablo.StrategieDeplacement.DeplacementStrategie;
import gameZeldiablo.Zeldiablo.StrategieDeplacement.DeplacementStrategieFactory;
import gameZeldiablo.Zeldiablo.Labyrinthe;
import gameZeldiablo.Zeldiablo.Tickable;
import gameZeldiablo.Zeldiablo.VariablesGlobales;

/**
 * Classe des mechants très très mechants
 */
public class Monstre extends Entite implements Cloneable, Tickable {
    private final DeplacementStrategie deplacementStrategie;
    private int curTick = 0;
    private final int ticInterval;

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
        this.deplacementStrategie = DeplacementStrategieFactory.creerStrategie(intelligence);
        this.ticInterval=15;
    }

    /**
     *  Constructeur 2
     */
    public Monstre(int x, int y, Intelligence intelligence,Labyrinthe l) {
        super(x, y, 3, VariablesGlobales.DEGAT_BASE,VariablesGlobales.SPRITE_MONSTRE[intelligence.ordinal()],l);
        this.deplacementStrategie = DeplacementStrategieFactory.creerStrategie(intelligence);
        this.ticInterval=15;
    }

    /**
     * Constructeur de la classe Monstre avec des valeurs par défaut.
     * @param x La position en x du monstre.
     * @param y La position en y du monstre.
     */
    public Monstre(int x, int y) {
        super(x, y, 3, VariablesGlobales.DEGAT_BASE,VariablesGlobales.SPRITE_MONSTRE[(int)(Math.random()*VariablesGlobales.SPRITE_MONSTRE.length)],null);
        this.deplacementStrategie = DeplacementStrategieFactory.creerStrategie(Intelligence.MOYENNE);
        this.ticInterval=15;
    }

    /**
     * Methode pour deplacer le monstre selon sa strategie
     */
    public void deplacer(Direction action){
        Entite tmp = getLabyrinthe().getJoueurs().get(0);
        //Deplacement
        deplacementStrategie.deplacement((Player)tmp,this);

        //Attaque
        for (Entite joueur : getLabyrinthe().getJoueurs()) {
            if (this.aCote(joueur)) {
                //etat visuelle
                this.infligerDegats(joueur);
            }
        }

        // déclenche le onstepon de la case où se trouve le monstre
        Case caseMonstre = getLabyrinthe().getCase(this.getY(), this.getX());
        caseMonstre.onStepOn(this);

        if (this.estMort()){
            getLabyrinthe().getEntites().remove(this);
        }
    }

    @Override
    public void tick() {
        if (curTick==ticInterval) {
            deplacer(null);
            curTick = 0;
        }else{
            curTick++;
        }
    }



    @Override
    public Monstre clone(){
        return (Monstre)super.clone();
    }

}
