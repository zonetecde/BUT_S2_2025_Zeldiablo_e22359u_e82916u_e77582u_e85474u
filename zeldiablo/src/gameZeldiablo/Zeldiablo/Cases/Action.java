package gameZeldiablo.Zeldiablo.Cases;

import gameZeldiablo.Zeldiablo.ZeldiabloJeu;
import gameZeldiablo.Zeldiablo.Entities.Entite;

public interface Action {
    /**
     * Action lorsque une entité marche sur la case
     * @param entite entité marchant sur la case
     */
    void onStepOn(Entite entite);

    /**
     * Lorsque l'action (e) est utilisée sur la case
     * @param entite entité utilisant la case
     * @param jeu Jeu contenant la case
     */
    void onAction(Entite entite, ZeldiabloJeu jeu);
}
