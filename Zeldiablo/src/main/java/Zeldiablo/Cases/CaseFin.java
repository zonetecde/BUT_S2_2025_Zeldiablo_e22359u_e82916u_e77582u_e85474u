package Zeldiablo.Cases;

import Zeldiablo.Entities.Entite;
import Zeldiablo.Entities.Player;
import Zeldiablo.Items.ItemsList;
import Zeldiablo.VariablesGlobales;

public class CaseFin extends Case {

    /**
     * Constructeur basique de case
     *
     */
    public CaseFin() {
        super(true, VariablesGlobales.SPRITE_SORTIE);
    }

    @Override
    public void onStepOn(Entite entite) {
        if (entite instanceof Player){
            Player player = (Player) entite;
            if (player.getInventory().possedeItem(ItemsList.AMULETTE)){
                player.setaGagne(true);
            }else{
                entite.setMsgToSay("Il me manque quelque chose...");
            }
        }
    }
}
