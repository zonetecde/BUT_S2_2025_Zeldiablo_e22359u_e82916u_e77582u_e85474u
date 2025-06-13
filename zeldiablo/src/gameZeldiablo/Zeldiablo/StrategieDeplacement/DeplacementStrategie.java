package gameZeldiablo.Zeldiablo.StrategieDeplacement;
import gameZeldiablo.Zeldiablo.Entities.Player;
import gameZeldiablo.Zeldiablo.Labyrinthe;
import gameZeldiablo.Zeldiablo.Entities.Monstre;


public interface DeplacementStrategie {
    /**
     * Déplace l'entité selon la stratégie de déplacement définie.
     **/
    public void deplacement(Player joueur, Monstre monstre);
 }

