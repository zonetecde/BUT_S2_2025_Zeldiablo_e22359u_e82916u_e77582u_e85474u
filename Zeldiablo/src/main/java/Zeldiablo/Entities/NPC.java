package Zeldiablo.Entities;

import Zeldiablo.Direction;
import Zeldiablo.Labyrinthe;

import javax.lang.model.type.NullType;
import java.util.ArrayList;
import java.util.List;
import java.util.function.Function;

public class NPC extends Player{

    boolean isTrader;

    /**
     * Constructeur
     *
     * @param dx    posx
     * @param dy    posy
     * @param maxHp hp Max
     * @param degat attaque du joueur
     */
    public NPC(int dx, int dy, double maxHp, double degat) {
        super(dx, dy, maxHp, degat);
    }


    public void interact(Entite e){
        if (isTrader){
            ((Player)e).trade(this);
        }

        setMsgToSay("Oh Hello Adventurer");
        new Thread(()->{
            try {
                Thread.sleep(3000);
            } catch (InterruptedException ex) {
                throw new RuntimeException(ex);
            }
            setMsgToSay("");
        }).start();

    }



    @Override
    public void deplacer(Direction action) {}

}
