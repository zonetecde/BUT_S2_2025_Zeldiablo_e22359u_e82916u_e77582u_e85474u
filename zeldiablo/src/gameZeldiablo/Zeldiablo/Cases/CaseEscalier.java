package gameZeldiablo.Zeldiablo.Cases;

import gameZeldiablo.Zeldiablo.Entities.Entite;
import gameZeldiablo.Zeldiablo.Items.Item;
import gameZeldiablo.Zeldiablo.VariablesGlobales;
import gameZeldiablo.Zeldiablo.ZeldiabloJeu;
import javafx.scene.paint.Color;

/**
 * Case d'escalier pour changer de niveau
 */
public class CaseEscalier extends Case{
    boolean monte = true;

    public CaseEscalier(int x, int y, boolean monte) {
        super(x, y, Color.DARKBLUE, true, VariablesGlobales.SPRITE_ESCALIER_HAUT);
        this.monte = monte;
        if (!monte) {
            this.setCouleur(Color.LIGHTBLUE);
        }
    }

    /**
     * Methode executée quand une entité interagit avec la case d'escalier.
     * Changement de niveau
     */
    @Override
    public void onAction(Entite entite, ZeldiabloJeu jeu) {
        jeu.changeLevel(this.monte);
    }
}