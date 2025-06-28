package Zeldiablo.StrategieDeplacement;

import Zeldiablo.Entities.Monstre;
import Zeldiablo.Entities.Player;


public interface DeplacementStrategie {
    /**
     * Déplace l'entité selon la stratégie de déplacement définie.
     **/
    void deplacement(Player joueur, Monstre monstre);
 }

