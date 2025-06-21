package gameZeldiablo.Zeldiablo;

import gameZeldiablo.Zeldiablo.Entities.Player;
import moteurJeu.Clavier;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class Encapsulation implements Serializable {
    public static Encapsulation ASK_DATA = new Encapsulation(5) ;
    public static Encapsulation ASK_PLAYERS = new Encapsulation(6) ;

    public int concerne; // 1 Clavier / 2 Player / 3 mapList /  4 All / 5 askData

    public int place;
    public Clavier clavier;
    public Map<String,Labyrinthe> map;
    public List<Player> players;
    public List<Clavier> claviers;

    public Encapsulation(Clavier c,int place){
        this.place = place;
        concerne = 1;
        this.clavier = c;
    }

    public Encapsulation(List<Player> p){
        concerne = 2;
        this.players = p;
    }

    public Encapsulation(List<Clavier> c, List<Player> p,Map<String,Labyrinthe> m){
        this.claviers=c;
        this.players=p;
        this.map=m;
        this.concerne=4;
    }

    public Encapsulation(int use){
        this.concerne = use;
    }

    public void getData(ServerRoom serverRoom,ZeldiabloJeu jeu){
        switch (concerne) {
            case 1 -> {
                try {
                    serverRoom.getClaviers().set(place, clavier);
                    serverRoom.logServer("Clavier reçu du joueur " + place + " : " + clavier.toString());
                }catch (Exception ignore){}
            }
            case 2 -> serverRoom.setPlayers(players);
            case 4 -> {
                if (!serverRoom.isServer()) {
                    serverRoom.setGame(jeu);
                    serverRoom.setDataReceived(true);
                    System.out.println("Sync du jeu");
                }else{
                    serverRoom.logServer("Sync du server");
                }

                serverRoom.setMaps(map);
                serverRoom.setClaviers(this.claviers);
                serverRoom.setPlayers(players);
            }
            case 6 -> serverRoom.sendData(new Encapsulation(jeu.getJoueurs()),0);
        }
    }

    public String toString(){
        switch (concerne){
            case 1 -> {
                return "Contient Clavier";
            }
            case 2 -> {
                return "Contient joueurs";
            }
            case 4 -> {
                return "Contient info gen";
            }
            case 5 -> {
                return "Demande de données";
            }
            default -> {
                return "Donnees inconnues";
            }
        }
    }
}
