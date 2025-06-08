package gameZeldiablo.Zeldiablo.Cases;

import gameZeldiablo.Zeldiablo.Entities.Entite;
import gameZeldiablo.Zeldiablo.VariablesGlobales;

public class CasePancarte extends Case {
    private final String message;

    /**
     * Constructeur de CasePancarte
     *
     * @param message message à afficher lorsque l'entité marche sur la case
     */
    public CasePancarte(String message) {
        super(true, VariablesGlobales.SPRITE_PANCARTE);
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
