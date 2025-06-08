package gameZeldiablo.Zeldiablo.Cases;

import gameZeldiablo.Zeldiablo.Entities.Entite;
import gameZeldiablo.Zeldiablo.VariablesGlobales;

public class CaseSwitch extends Case {

    private Case link;

    /**
     * Constructeur de CaseOuverture
     *
     * @param action action à exécuter lorsque l'entité marche sur la case
     */
    public CaseSwitch( Case action) {
        super(true, VariablesGlobales.SPRITE_P_P);
        this.link = action;
        setActivate(true);
    }

    public CaseSwitch() {
        this(null);
    }

    /**
     * Méthode exécutée quand une entité marche sur la case.
     */
    @Override
    public void onStepOn(Entite entite) {
        if (link != null) {
            link.activate();
        }
    }

    /**
     * Lie la plaque avec un autre objet
     * @param ccase objet activable
     */
    public void createLink(Case ccase){this.link=ccase;}

    @Override
    public boolean isLinked() {
        return (this.link!=null);
    }
}
