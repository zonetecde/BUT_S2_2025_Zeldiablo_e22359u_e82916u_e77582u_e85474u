package gameZeldiablo.Zeldiablo.Cases;

import gameZeldiablo.Zeldiablo.Entities.Entite;
import javafx.scene.paint.Color;

public class CaseOuverture extends Case {
    // Attribut contenant la methode a appeler lorsqu'une entité marche sur la case
    private Runnable action;

    /**
     * Constructeur de CaseOuverture
     *
     * @param x position x de la case
     * @param y position y de la case
     * @param action action à exécuter lorsque l'entité marche sur la case
     */
    public CaseOuverture(int x, int y, Runnable action) {
        super(x, y, Color.ORANGE, true);
        this.action = action;
    }

    /**
     * Méthode exécutée quand une entité marche sur la case.
     */
    @Override
    public void onStepOn(Entite entite) {
        action.run();
    }
}
