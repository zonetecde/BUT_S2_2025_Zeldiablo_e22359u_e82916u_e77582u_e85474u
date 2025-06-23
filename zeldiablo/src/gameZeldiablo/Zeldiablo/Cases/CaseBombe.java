package gameZeldiablo.Zeldiablo.Cases;

import gameZeldiablo.Zeldiablo.Labyrinthe;
import gameZeldiablo.Zeldiablo.Tickable;
import gameZeldiablo.Zeldiablo.VariablesGlobales;

public class CaseBombe extends Case implements Tickable {
    private int tics = 0;
    private int explosionStep;
    private final int tickPlosion = 30;
    private final int range = 3 ;

    private final Labyrinthe laby;
    int[] coord;

    public CaseBombe(Labyrinthe l,int[] coord){
        super(false, VariablesGlobales.SPRITE_CASE_BOMBE);
        this.laby=l;
        this.coord = coord;
    }

    @Override
    public void tick() {
        if (tics<tickPlosion+20){
            tics++;
        }
        if (tics>=tickPlosion && tics<=tickPlosion+20){
            explosion(false);
        }
        if (tics==tickPlosion+20){
            explosion(true);
            laby.getTics().remove(this);
        }
        if (tics>=tickPlosion && tics%5==0){
            explosionStep++;
        }
    }

    public void explosion(boolean fin){
        for (int i = -range ; i<range ; i++) {
            try {
                if (fin){
                    laby.getGameBoard()[coord[0]][coord[1] - i] = new CaseVide();
                    laby.getGameBoard()[coord[0] - i][coord[1]] = new CaseVide();
                }else {
                    laby.getGameBoard()[coord[0]][coord[1] - i] = new CaseExplosion(explosionStep);
                    laby.getGameBoard()[coord[0] - i][coord[1]] = new CaseExplosion(explosionStep);
                }
            } catch (Exception ignore) {}
        }
    }
}
