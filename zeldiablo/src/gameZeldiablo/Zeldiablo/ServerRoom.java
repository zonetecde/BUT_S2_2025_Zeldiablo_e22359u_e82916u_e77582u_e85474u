package gameZeldiablo.Zeldiablo;

import gameZeldiablo.Zeldiablo.Entities.Player;

import java.io.ObjectInputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ServerRoom {
    private List<Player> playersInRoom = new ArrayList<>();
    private Map<String,Labyrinthe> maps = new HashMap<>();
    private int nbJoueurs = 0;

    public void updateGame(ZeldiabloJeu jeu){
        MapList.setMapList(maps);
        jeu.setJoueurs(playersInRoom);
    }

    public void updateServer(ZeldiabloJeu jeu){
        maps = MapList.getMapList();
        playersInRoom = jeu.getJoueurs();
    }

    public int host(ZeldiabloJeu jeu){
        MapList.initialisation();
        updateServer(jeu);
        return 0;
    }

    public int log(ZeldiabloJeu jeu){
        nbJoueurs++;
        updateGame(jeu);
        return nbJoueurs-1;
    }
}
