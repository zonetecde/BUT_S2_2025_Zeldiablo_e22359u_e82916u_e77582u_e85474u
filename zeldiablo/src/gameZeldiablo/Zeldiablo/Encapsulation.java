package gameZeldiablo.Zeldiablo;

import gameZeldiablo.Zeldiablo.Entities.Player;
import moteurJeu.Clavier;

import java.io.Serializable;
import java.util.List;
import java.util.Map;

public class Encapsulation implements Serializable {
    public int concerne; // 1 Clavier / 2 Player / 3 mapList /  4 All

    public int place;
    public Clavier clavier;
    public Player player;
    public Map<String,Labyrinthe> map;
    public List<Player> players;
    public List<Clavier> claviers;

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

    public Encapsulation(List<Clavier> c, List<Player> p,Map<String,Labyrinthe> m){
        this.claviers=c;
        this.players=p;
        this.map=m;
        this.concerne=4;
    }

    public void getData(ServerRoom serverRoom,ZeldiabloJeu jeu){
        switch (concerne) {
            case 1 -> serverRoom.getClaviers().set(place, clavier);
            case 2 -> serverRoom.getPlayers().set(place, player);
            case 3 -> serverRoom.setMaps(map);
            case 4 -> {
                serverRoom.setMaps(map);
                serverRoom.setClaviers(this.claviers);
                serverRoom.setPlayers(players);
                serverRoom.setGame(jeu);
            }
        }
    }
}
