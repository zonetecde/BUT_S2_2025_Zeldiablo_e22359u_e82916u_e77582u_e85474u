package Zeldiablo.Cases;
import Zeldiablo.VariablesGlobales;

public class CaseExplosion extends CasePiege {


    public CaseExplosion(int i){
        super(Double.MAX_VALUE);
        this.setSprite(VariablesGlobales.SPRITE_EXPLOSION[i]);
    }

}
