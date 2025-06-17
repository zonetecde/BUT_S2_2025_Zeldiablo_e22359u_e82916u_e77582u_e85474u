package gameZeldiablo.Zeldiablo;

import gameZeldiablo.Zeldiablo.Entities.Player;
import moteurJeu.Clavier;

import java.io.*;
import java.net.InetAddress;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.*;

public class ServerRoom {
    private List<Player>  playersInRoom = new ArrayList<>();
    private final List<Clavier> claviers = new ArrayList<>();
    private Map<String,Labyrinthe> maps = new HashMap<>();
    private int nbJoueurs = 0;

    Socket dataSocket;
    ObjectOutputStream out;
    ObjectInputStream in;


    public void sendData(Encapsulation e){
        try {
            out.writeObject(e);
            out.flush();
        } catch (IOException ex) {
            throw new RuntimeException(ex);
        }
    }

    public void gettingData() {
        try {
            while (true) {
                Encapsulation tmp = (Encapsulation) in.readObject();
                tmp.getData(this);
            }
        } catch (EOFException e) {
            System.out.println("Connexion terminée proprement.");
        } catch (IOException | ClassNotFoundException e) {
            System.out.println("Erreur de réception : " + e.getMessage());
        }
    }


    public void updateGame(ZeldiabloJeu jeu){
        MapList.setMapList(maps);
        jeu.setJoueurs(playersInRoom);
        System.out.println(playersInRoom.size());
    }

    public void initServer(ZeldiabloJeu jeu,Clavier clavier){
        maps = MapList.getMapList();
        playersInRoom = jeu.getJoueurs();
        playersInRoom.add(new Player(0,0,5,1));
        claviers.add(clavier);
        updateGame(jeu);
    }

    public void host(ZeldiabloJeu jeu,Clavier clavier){
        MapList.initialisation();
        initServer(jeu,clavier);
        try {
            ServerSocket getSocket = new ServerSocket(8000);
            System.out.println(getSocket.getInetAddress());
            //TODO Thread n'affiche pas le print
            dataSocket = getSocket.accept();
            out = new ObjectOutputStream(dataSocket.getOutputStream());
            in = new ObjectInputStream(dataSocket.getInputStream());
            sendData(new Encapsulation(maps,0));
            sendData(new Encapsulation(jeu.getJoueur(0),0));
            sendData(new Encapsulation(clavier,0));
            new Thread(this::gettingData).start();
            jeu.multiplayer= true;
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public int log(ZeldiabloJeu jeu,Clavier clavier){
        try {
            Scanner sc = new Scanner(System.in);
            System.out.println("Adresse");
            String ad = sc.nextLine();
            InetAddress address = InetAddress.getByName(ad);
            dataSocket = new Socket(address,8000);
            out = new ObjectOutputStream(dataSocket.getOutputStream());
            in = new ObjectInputStream(dataSocket.getInputStream());
            new Thread(this::gettingData).start();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        nbJoueurs++;
        Player tmp = jeu.getJoueur(0);
        playersInRoom.add(tmp);
        claviers.add(clavier);
        sendData(new Encapsulation(tmp,nbJoueurs));
        sendData(new Encapsulation(clavier,nbJoueurs));
        updateGame(jeu);
        return nbJoueurs;
    }

    public List<Clavier> getClaviers() {
        return claviers;
    }

    public List<Player> getPlayers(){
        return playersInRoom;
    }

    public void setMaps(Map<String,Labyrinthe> m){this.maps=m;}

}
