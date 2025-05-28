package gameZeldiablo.Zeldiablo.Cases;

import gameZeldiablo.Zeldiablo.Entities.Entite;
import gameZeldiablo.Zeldiablo.VariablesGlobales;

public class CaseVide extends Case {
    public CaseVide(int x, int y){
        super(x, y, VariablesGlobales.COULEUR_VIDE, true);
    }

    @Override
    public void onStepOn(Entite entite) {
        return;
    }
}


