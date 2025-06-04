package gameZeldiablo.Zeldiablo.Cases;

import gameZeldiablo.Zeldiablo.Entities.Entite;
import gameZeldiablo.Zeldiablo.VariablesGlobales;
import javafx.scene.paint.Color;

public class CasePancarte extends Case {
    private String message;

    /**
     * Constructeur de CasePancarte
     *
     * @param x position x de la case
     * @param y position y de la case
     * @param message message à afficher lorsque l'entité marche sur la case
     */
    public CasePancarte(int x, int y, String message) {
        super(x, y, true, CaseType.SOL, VariablesGlobales.SPRITE_PANCARTE);
        this.message = message;
    }
    
    /**
     * Méthode exécutée quand une entité marche sur la case.
     */
    @Override
    public void onStepOn(Entite entite) {
        entite.setMsgToSay("Je lis : " + message);
    }
}
