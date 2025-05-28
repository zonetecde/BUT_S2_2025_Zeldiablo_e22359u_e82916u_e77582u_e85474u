package gameZeldiablo.Zeldiablo.Cases;

import gameZeldiablo.Zeldiablo.Entities.Entite;
import gameZeldiablo.Zeldiablo.VariablesGlobales;

public class CaseMur extends Case {
    public CaseMur(int x, int y){
        super(x, y, VariablesGlobales.COULEUR_MUR, false);
    }

    @Override
    public void onStepOn(Entite entite) {
        return;
    }
}
