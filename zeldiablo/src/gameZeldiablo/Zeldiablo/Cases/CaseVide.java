package gameZeldiablo.Zeldiablo.Cases;

import gameZeldiablo.Zeldiablo.Entities.Entite;
import gameZeldiablo.Zeldiablo.Items.Item;
import gameZeldiablo.Zeldiablo.VariablesGlobales;



public class CaseVide extends Case {
    public CaseVide(int x, int y){
        super(x, y, VariablesGlobales.COULEUR_VIDE, true);
    }
    Item item;
    @Override
    public void onStepOn(Entite entite) {
        return;
    }

    @Override
    public void addItem(Item item) {
        this.item = item;
    }
    public boolean getObjet() {
        if (item != null) {;
            return true;
        } else {
            return false;
        }
    }
}


