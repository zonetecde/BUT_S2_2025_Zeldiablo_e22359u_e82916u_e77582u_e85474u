package Zeldiablo.Cases;

import Zeldiablo.Entities.Entite;
import Zeldiablo.Main.ZeldiabloJeu;
import Zeldiablo.VariablesGlobales;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.sun.jdi.event.ThreadStartEvent;

import java.util.ArrayList;
import java.util.List;


public class CaseSwitch extends Case {
    private List<Case> link;
    private boolean isOn = false;


    /**
     * Constructeur de CaseOuverture
     *
     * @param action action à exécuter lorsque l'entité marche sur la case
     */
    public CaseSwitch(@JsonProperty("link") Case action) {
        super(true, VariablesGlobales.SPRITE_SWITCH_OFF);
        this.link = new ArrayList<>();
        if (action != null) {
            this.link.add(action);
        }
        setActivate(true);
    }

    public CaseSwitch() {
        this(null);
    }

    /**
     * Méthode exécutée quand une entité marche sur la case.
     */
    @Override
    public void onAction(Entite entite, ZeldiabloJeu jeu) {
        if (!link.isEmpty()) {
            for (Case c : link) {
                c.activate();
                isOn = !isOn;
            }
        }
        if (isOn){this.setSprite(VariablesGlobales.SPRITE_SWITCH_ON);}else{this.setSprite(VariablesGlobales.SPRITE_SWITCH_OFF);}

    }

    /**
     * Lie la plaque avec un autre objet
     * @param ccase objet activable
     */
    public void setLink(List<Case> ccase){this.link=ccase;}

    public void addLink(Case ccase){this.link.add(ccase);}

    @Override
    public boolean isLinked() {
        return (this.link!=null);
    }

    public List<Case> getLink(){return this.link;}

}
