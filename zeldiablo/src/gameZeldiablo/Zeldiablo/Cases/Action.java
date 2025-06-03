package gameZeldiablo.Zeldiablo.Cases;

import gameZeldiablo.Zeldiablo.ZeldiabloJeu;
import gameZeldiablo.Zeldiablo.Entities.Entite;

public interface Action {
    public abstract void onStepOn(Entite entite);
    public abstract void onAction(Entite entite, ZeldiabloJeu jeu);
}
