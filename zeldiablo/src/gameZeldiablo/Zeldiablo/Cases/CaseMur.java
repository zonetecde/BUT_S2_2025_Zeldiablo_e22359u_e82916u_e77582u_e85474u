package gameZeldiablo.Zeldiablo.Cases;

import com.fasterxml.jackson.annotation.JsonCreator;
import gameZeldiablo.Zeldiablo.VariablesGlobales;

public class CaseMur extends Case {
    /**
     * Constructeur
     */
    @JsonCreator
    public CaseMur(){
        super(false, VariablesGlobales.SPRITE_MUR);
    }
}