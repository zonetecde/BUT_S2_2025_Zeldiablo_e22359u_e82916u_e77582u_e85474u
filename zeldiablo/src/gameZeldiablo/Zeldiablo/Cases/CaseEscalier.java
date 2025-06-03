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
     * jeu dans lequel est contenu la case
     */
    ZeldiabloJeu jeu;

    /**
     * Constructeur de Case
     *
     * @param x position
     * @param y position
     */
    public CaseEscalier(int x, int y, boolean monte, ZeldiabloJeu jeu) {
        super(x, y, Color.BLUE, true);
        this.jeu=jeu;
        if (!monte) {
            this.setCouleur(Color.LIGHTBLUE);
        }
    }

    @Override
    public boolean hasItem() {
        return true;
    }

    /**
     * Methode executée quand une entité marche sur la case
     * Changement de niveau
     */
    @Override
    public Item getItem() {
        jeu.nextLevel();
        return null;
    }

}