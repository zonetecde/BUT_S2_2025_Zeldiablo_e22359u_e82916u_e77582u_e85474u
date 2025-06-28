package Zeldiablo.StrategieDeplacement;

import Zeldiablo.Entities.Monstre;
import Zeldiablo.Entities.Player;

import java.io.Serializable;

public class DeplacementStatique implements DeplacementStrategie, Serializable {
    @Override
    /**
     * Déplace l'entité selon la stratégie de déplacement statique.
     * Dans ce cas, le monstre ne bouge pas.
     *
     * @param labyrinthe Le labyrinthe dans lequel se trouve le monstre.
     * @param monstre    Le monstre qui utilise cette stratégie de déplacement.
     */
    public void deplacement(Player joueur, Monstre monstre) {
        monstre.switchSprite();
    }

    public String toString(){return "Statique";}
}
