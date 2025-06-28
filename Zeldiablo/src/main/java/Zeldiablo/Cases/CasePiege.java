package Zeldiablo.Cases;

import Zeldiablo.Entities.Entite;
import Zeldiablo.VariablesGlobales;
import com.fasterxml.jackson.annotation.JsonProperty;


/**
 * Classe représentant une case piège dans le jeu Zeldiablo
 */
public class CasePiege extends Case {
    private final double degats;

    /**
     * Constructeur de CasePiege
     *
     * @param degats Dégâts infligés par le piège
     */

    public CasePiege(@JsonProperty("degats") double degats) {
        super(true, VariablesGlobales.SPRITE_PIEGE);
        this.degats = degats;
    }

    /**
     * Méthode appelée lorsqu'une entité marche sur la case piège.
     * Cette méthode inflige des dégâts à l'entité.
     * @param e L'entité qui marche sur la case piège
     * @see Entite#prendreDegat(double)
     */
    @Override
    public void onStepOn(Entite e) {
        e.prendreDegat(degats);
    }
}


