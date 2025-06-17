package gameZeldiablo.Zeldiablo;

import gameZeldiablo.Zeldiablo.Entities.Player;
import moteurJeu.Clavier;

import java.io.Serializable;
import java.util.Map;

public class Encapsulation implements Serializable {
    public int concerne; // 1 Clavier / 2 Player / 3 mapList

    public int place;
    public Clavier clavier;
    public Player player;
    public Map<String,Labyrinthe> map;

    public Encapsulation(Object o,int place){
        this.place = place;
        if (o instanceof Clavier){
            concerne = 1;
            this.clavier= (Clavier) o;
        }else if(o instanceof Player){
            concerne = 2;
            player = (Player) o;
        }else if(o instanceof Map<?,?>){
            concerne = 3;
            map = (Map<String, Labyrinthe>) o;
        }
    }

    public void getData(ServerRoom serverRoom){
        if (concerne==1){
            serverRoom.getClaviers().add(place,clavier);
        }else if(concerne == 2){
            serverRoom.getPlayers().add(place,player);
        }else if(concerne == 3){
            serverRoom.setMaps(map);
        }
    }
}
