package gameZeldiablo.Zeldiablo.Cases;

import gameZeldiablo.Zeldiablo.VariablesGlobales;

public class CaseMur extends Case {
    public CaseMur(int x, int y){
        super(x, y, VariablesGlobales.COULEUR_MUR, false,VariablesGlobales.SPRITE_MUR);
    }
}
