package gameZeldiablo.Zeldiablo.Cases;

import gameZeldiablo.Zeldiablo.Entities.Entite;
import gameZeldiablo.Zeldiablo.Items.Item;
import gameZeldiablo.Zeldiablo.VariablesGlobales;
import gameZeldiablo.Zeldiablo.ZeldiabloJeu;
import javafx.scene.image.Image;
import javafx.scene.paint.Color;

/**
 * Case d'escalier pour changer de niveau
 */
public class CaseEscalier extends Case{
    boolean monte = true;

    public CaseEscalier(int x, int y, boolean monte) {
        super(x, y, true, CaseType.SOL, VariablesGlobales.SPRITE_ESCALIER_HAUT);
        this.monte = monte;
        if (!monte) {
            this.setImg(VariablesGlobales.SPRITE_ESCALIER_BAS);
        }
    }

    /**
     * Methode executée quand une entité interagit avec la case d'escalier.
     * Changement de niveau
     */
    @Override
    public void onAction(Entite entite, ZeldiabloJeu jeu) {
        String nomFichier = jeu.getLaby().getNomFichier();
        if (!monte && nomFichier.endsWith("labyjeu1.txt")) {
            for (Item item : jeu.getLaby().getPlayer().getInventory()) {
                if ("Amulette".equals(item.getName())) {
                    jeu.getLaby().getPlayer().setaGagne(true);
                }
            }
            return;
        }
        jeu.changeLevel(this.monte);
    }
}