package gameZeldiablo.Zeldiablo.StrategieDeplacement;

import gameZeldiablo.Zeldiablo.Direction;
import gameZeldiablo.Zeldiablo.Labyrinthe;
import gameZeldiablo.Zeldiablo.Entities.Monstre;

public class DeplacementRapprochement implements DeplacementStrategie {

    /**
     * Déplace le monstre vers le joueur en utilisant une stratégie de rapprochement.
     * Le monstre se déplace dans la direction où il y a le plus de différence
     * entre sa position et celle du joueur.
     *
     * @param labyrinthe Le labyrinthe dans lequel se trouve le monstre.
     * @param monstre    Le monstre qui utilise cette stratégie de déplacement.
     */
    @Override
    public void deplacement(Labyrinthe labyrinthe, Monstre monstre) {
        // Récupère la position du joueur
        int joueurX = labyrinthe.getPlayer().getX();
        int joueurY = labyrinthe.getPlayer().getY();
        
        int monstreX = monstre.getX();
        int monstreY = monstre.getY();
        
        Direction direction = null;
        
        // Récupère la différence entre la position du monstre et celle du joueur
        // pour savoir dans quelle direction se déplacer
        int deltaX = joueurX - monstreX;
        int deltaY = joueurY - monstreY;
        
        // Regarde là où il y a le plus de différence
        if (Math.abs(deltaX) > Math.abs(deltaY)) {
            direction = (deltaX > 0) ? Direction.DROITE : Direction.GAUCHE;
        } else {
            direction = (deltaY > 0) ? Direction.BAS : Direction.HAUT;
        }
        
        // Tente le déplacement
        int[] prochainePosition = Labyrinthe.getSuivant(monstreY, monstreX, direction);
        if (labyrinthe.canEntityMoveTo(prochainePosition[0], prochainePosition[1])) {
            monstre.setPosition(prochainePosition[0], prochainePosition[1]);
        }
        // Si impossible, le monstre ne bouge pas
    }
}
