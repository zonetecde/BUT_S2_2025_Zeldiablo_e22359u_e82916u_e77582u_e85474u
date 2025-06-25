package gameZeldiablo.Zeldiablo.Items;

import gameZeldiablo.Zeldiablo.Cases.CaseBombe;
import gameZeldiablo.Zeldiablo.Cases.CaseVide;
import gameZeldiablo.Zeldiablo.Entities.Player;
import gameZeldiablo.Zeldiablo.Labyrinthe;
import gameZeldiablo.Zeldiablo.VariablesGlobales;

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
            laby.getTics().add(bombe);
            return true;
        }
        return false;
    }
}
