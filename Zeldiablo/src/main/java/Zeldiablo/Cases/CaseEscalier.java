package Zeldiablo.Cases;

import Zeldiablo.Entities.Entite;
import Zeldiablo.Items.ItemsList;
import Zeldiablo.Main.ZeldiabloJeu;
import Zeldiablo.MapList;
import Zeldiablo.VariablesGlobales;
import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * Case d'escalier pour changer de niveau
 */
public class CaseEscalier extends Case{
    boolean monte;
    String nextMap;
    int x,y;

    /**
     * Constructeur escalier
     * @param monte escalier montant ou descendant
     */
    public CaseEscalier(@JsonProperty("monte") boolean monte) {
        super(true, VariablesGlobales.SPRITE_ESCALIER_HAUT);
        this.monte = monte;
        if (!monte) {
            this.setSprite(VariablesGlobales.SPRITE_ESCALIER_BAS);
        }
    }

    public void setNext(
            @JsonProperty("nextMap") String nextMap,
            @JsonProperty("x") int x,
            @JsonProperty("y") int y){
        this.nextMap=nextMap;
        this.x=x;
        this.y=y;
    }

    /**
     * Methode executée quand une entité interagit avec la case d'escalier.
     * Changement de niveau
     */
    @Override
    public void onAction(Entite entite, ZeldiabloJeu jeu) {
        jeu.changeLevel(nextMap,x,y);
    }

    public String getNextMap() {
        return nextMap;
    }

    public boolean getMonte(){return monte;}

    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }

    public boolean isLinked(){return (this.nextMap!=null);}
}