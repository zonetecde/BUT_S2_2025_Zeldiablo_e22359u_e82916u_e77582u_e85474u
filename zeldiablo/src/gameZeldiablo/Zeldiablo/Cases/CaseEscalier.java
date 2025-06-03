package gameZeldiablo.Zeldiablo.Cases;

import gameZeldiablo.Zeldiablo.Entities.Entite;
import gameZeldiablo.Zeldiablo.Items.Item;
import gameZeldiablo.Zeldiablo.ZeldiabloJeu;
import javafx.scene.paint.Color;

/**
 * Case d'escalier pour changer de niveau
 */
public class CaseEscalier extends Case{
    /**
     * Constructeur de Case
     *
     * @param x position
     * @param y position
     */
    public CaseEscalier(int x, int y) {
        super(x, y, Color.LIGHTBLUE, true);
    }

    @Override
    public void onAction(Entite entite, ZeldiabloJeu jeu) {
        jeu.nextLevel();
    }
}