package Zeldiablo.StrategieDeplacement;

import Zeldiablo.Direction;
import Zeldiablo.Entities.Entite;
import Zeldiablo.Entities.Monstre;
import Zeldiablo.Entities.Player;
import Zeldiablo.Labyrinthe;

import java.io.Serializable;

public class DeplacementIntelligent implements DeplacementStrategie, Serializable {
    public static final int MAXDEPTH = 3;

    @Override
    public void deplacement(Player joueur, Monstre monstre) {
        AStar app = new AStar();
        Labyrinthe laby = monstre.getLabyrinthe();

        //Creation de la grille
        int[][] grid = new int[laby.getLongueur()][laby.getHauteur()];
        for (int i=0 ; i<laby.getLongueur(); i++){
            for (int j=0 ; j< laby.getHauteur(); j++) {
                if (laby.getCase(j, i).getIsWalkable()) {
                    grid[i][j] = 1;
                }else{
                    grid[i][j] = 0;
                }
            }
        }

        //Source
        AStar.Pair source = new AStar.Pair(monstre.getX(),monstre.getY());
        //Dest
        AStar.Pair dest   = new AStar.Pair(joueur.getX(),joueur.getY());

        AStar.Pair nextTile = app.aStarSearch(grid, laby.getLongueur(), laby.getHauteur(),source,dest);
        if (nextTile!=null) {
            Direction NextStep = getDirection(monstre, nextTile  );

            monstre.animationDep(NextStep);
        }
    }

    private Direction getDirection(Entite monstre,AStar.Pair pair){
        AStar.Pair caseMonstre = new AStar.Pair(monstre.getX(), monstre.getY());

        if (caseMonstre.x > pair.x){return Direction.GAUCHE;} else if (caseMonstre.x < pair.x) return Direction.DROITE;
        if (caseMonstre.y > pair.y){return Direction.HAUT;} else if (caseMonstre.y < pair.y) return Direction.BAS;
        return null;

    }

    public String toString(){return "Intelligent";}
}