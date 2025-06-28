package Zeldiablo.Cases;

import Zeldiablo.VariablesGlobales;
import com.fasterxml.jackson.annotation.JsonCreator;

public class CaseMur extends Case {
    /**
     * Constructeur
     */
    @JsonCreator
    public CaseMur(){
        super(false, VariablesGlobales.SPRITE_MUR);
    }
}