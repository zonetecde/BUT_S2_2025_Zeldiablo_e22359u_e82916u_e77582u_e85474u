package Zeldiablo.Entities;

import Zeldiablo.Cases.Case;
import Zeldiablo.Direction;
import Zeldiablo.Labyrinthe;
import Zeldiablo.StrategieDeplacement.DeplacementStrategie;
import Zeldiablo.StrategieDeplacement.DeplacementStrategieFactory;
import Zeldiablo.Tickable;
import Zeldiablo.VariablesGlobales;
import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonTypeName;

/**
 * Classe des mechants très très mechants
 */
@JsonTypeName("Monstre")
public class Monstre extends Entite implements Cloneable, Tickable {
    // Add these annotations to handle the fields during deserialization
    @JsonIgnore
    private final DeplacementStrategie deplacementStrategie;
    @JsonProperty("curTick")
    private int curTick = 0;
    @JsonProperty("ticInterval")
    private final int ticInterval;
    @JsonProperty("intelligence")
    private Intelligence intelligence;

    // Add a no-args constructor for Jackson
    @JsonCreator
    public Monstre() {
        super(0, 0, 3, VariablesGlobales.DEGAT_BASE, 
              VariablesGlobales.SPRITE_MONSTRE[0], null);
        this.intelligence = Intelligence.NULLE;
        this.deplacementStrategie = DeplacementStrategieFactory.creerStrategie(Intelligence.MOYENNE);
        this.ticInterval = 15;
    }

    // Update the existing constructor
    @JsonCreator
    public Monstre(@JsonProperty("x") int x,
               @JsonProperty("y") int y,
               @JsonProperty("hp") double hp,
               @JsonProperty("degat") double degat,
               @JsonProperty("intelligence") Intelligence intelligence,
               @JsonProperty("labyrinthe") Labyrinthe l) {
    super(x, y, hp, degat, null, l);  // Pass null for sprite initially
    this.intelligence = intelligence;
    this.deplacementStrategie = DeplacementStrategieFactory.creerStrategie(intelligence);
    this.ticInterval = 15;
    // Set sprite after intelligence is set
    this.setSprite(VariablesGlobales.SPRITE_MONSTRE[intelligence.ordinal()]);
}

    /**
     *  Constructeur 2
     */
    public Monstre(int x, int y, Intelligence intelligence,Labyrinthe l) {
        super(x, y, 3, VariablesGlobales.DEGAT_BASE,VariablesGlobales.SPRITE_MONSTRE[intelligence.ordinal()],l);
        this.intelligence=intelligence;
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
        this.intelligence=Intelligence.NULLE;
        this.deplacementStrategie = DeplacementStrategieFactory.creerStrategie(Intelligence.MOYENNE);
        this.ticInterval=15;
    }

    /**
     * Methode pour deplacer le monstre selon sa strategie
     */
    public void deplacer(Direction action){
        Entite tmp = getLabyrinthe().nameJoueurs().getFirst();
        //Deplacement
        deplacementStrategie.deplacement((Player)tmp,this);

        //Attaque
        for (Entite joueur : getLabyrinthe().nameJoueurs()) {
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

    public Intelligence getIntelligence(){return intelligence;}

    @Override
    public Monstre clone(){
        return (Monstre)super.clone();
    }

    @Override
    public String toString() {
        return "Monstre "+ deplacementStrategie ;
    }
}