package Zeldiablo.Items;

import Zeldiablo.Cases.CaseBombe;
import Zeldiablo.Cases.CaseVide;
import Zeldiablo.Entities.Player;
import Zeldiablo.Labyrinthe;
import Zeldiablo.VariablesGlobales;

public class Bombe extends Item{

    public Bombe(){
        super(ItemsList.BOMBE, VariablesGlobales.SPRITE_BOMBE,TypeItem.OBJET);
    }

    @Override
    public boolean use(Player e){
        Labyrinthe laby = e.getLabyrinthe();
        int[] coord =  {e.getY(),e.getX()};
        System.out.println((e.getLabyrinthe().getCase(e.getY(),e.getX())));
        if (e.getLabyrinthe().getCase(e.getY(),e.getX()) instanceof CaseVide){
            CaseBombe bombe = new CaseBombe(laby,coord);
            laby.getGameBoard()[e.getY()][e.getX()] = bombe;
            laby.getTicks().add(bombe);
            return true;
        }
        return false;
    }
}
