package Zeldiablo.Cases;

import Zeldiablo.Entities.Entite;
import Zeldiablo.VariablesGlobales;
import com.fasterxml.jackson.annotation.JsonProperty;

public class CasePancarte extends Case {
    private final String message;

    /**
     * Constructeur de CasePancarte
     *
     * @param message message à afficher lorsque l'entité marche sur la case
     */
    public CasePancarte(@JsonProperty("message") String message) {
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
