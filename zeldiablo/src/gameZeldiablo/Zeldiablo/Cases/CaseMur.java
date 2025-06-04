package gameZeldiablo.Zeldiablo.Cases;

import gameZeldiablo.Zeldiablo.VariablesGlobales;

public class CaseMur extends Case {
    public CaseMur(int x, int y){
        super(x, y, false, CaseType.SOLIDE, VariablesGlobales.SPRITE_MUR);
    }
}