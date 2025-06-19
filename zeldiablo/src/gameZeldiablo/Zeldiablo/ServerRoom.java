package gameZeldiablo.Zeldiablo;

import gameZeldiablo.Zeldiablo.Entities.Player;
import moteurJeu.Clavier;

import java.io.*;
import java.net.InetAddress;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.*;

/**
 * Classe de gestion du multijoueur
 */
public class ServerRoom {
    //Liste des joueurs dans la room
    private List<Player>  playersInRoom = new ArrayList<>();
    //Liste des claviers associés aux joueurs
    private List<Clavier> claviers = new ArrayList<>();
    //Liste des Cartes <Nom url,Carte>
    private Map<String,Labyrinthe> maps = new HashMap<>();
    //Jeu auxquel est relié le server local
    private ZeldiabloJeu game;

    //Socket de partage d'information entre les joueurs
    Socket dataSocket;
    //OutputStream pour partager des donnees
    ObjectOutputStream out;
    //InputStream pour recuperer les donnees
    ObjectInputStream in;

    /**
     * Methode envoyant des données sous forme d'Encapsulation
     * sur le socket
     * @param e données a envoyer
     */
    public void sendData(Encapsulation e){
        try {
            out.writeObject(e);
            out.flush();
        } catch (IOException ex) {
            throw new RuntimeException(ex);
        }
    }

    /**
     * Boucle infinie recuperant des données sous forme
     * d'Encapsulation et les applique au server
     */
    public void gettingData() {
        try {
            while (true) {
                Encapsulation tmp = (Encapsulation) in.readObject();
                if (tmp!=null) {
                    tmp.getData(this,game);
                }else{
                    sendData(new Encapsulation(claviers,playersInRoom,maps));
                }
            }
        } catch (EOFException e) {
            System.out.println("Connexion terminée proprement.");
        } catch (IOException | ClassNotFoundException e) {
            System.out.println("Erreur de réception : " + e.getMessage());
        }
    }

    /**
     * initialise le jeu coté client
     * @param jeu jeu à initialiser
     */
    public void setGame(ZeldiabloJeu jeu){
        MapList.setMapList(maps);
        jeu.setJoueurs(playersInRoom);
        //TODO
        System.out.println(playersInRoom.size());
    }

    /**
     * Initialise le server à partir du jeu actuel
     * @param jeu jeu actuel
     * @param clavier clavier actuel
     */
    public void initServer(ZeldiabloJeu jeu,Clavier clavier){
        maps = MapList.getMapList();
        playersInRoom = jeu.getJoueurs();
        claviers.add(clavier);
    }

    /**
     * Methode de creation de room depuis les maps existantes
     * @param jeu jeu sur lequel sera basé le server
     * @param clavier clavier de l'host
     */
    public void host(ZeldiabloJeu jeu,Clavier clavier){
        this.game=jeu;
        MapList.initialisation();
        initServer(jeu,clavier);
        try {
            ServerSocket getSocket = new ServerSocket(8000);
            jeu.roomID = getSocket.getInetAddress().toString();
            dataSocket = getSocket.accept();
            out = new ObjectOutputStream(dataSocket.getOutputStream());
            in = new ObjectInputStream(dataSocket.getInputStream());
            new Thread(this::gettingData).start();
            jeu.multiplayer= true;
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * Methode pour rejoindre une room
     * @param jeu jeu actuel
     * @param clavier clavier actuel
     * @return id du nouveau joueur
     */
    public int log(ZeldiabloJeu jeu,Clavier clavier){
        this.game=jeu;
        try {
            //Demande l'adresse
            Scanner sc = new Scanner(System.in);
            System.out.println("Adresse");
            String ad = sc.nextLine();
            InetAddress address = InetAddress.getByName(ad);

            //Se connecte au server
            dataSocket = new Socket(address,8000);
            out = new ObjectOutputStream(dataSocket.getOutputStream());
            in = new ObjectInputStream(dataSocket.getInputStream());

            //lance la reception de données
            new Thread(this::gettingData).start();

            //recupere le server
            sendData(null);
            Thread.sleep(1000);
            //Ajoute un joueur à la room
            Player newPlayer = new Player(0,0,5,2);
            newPlayer.setEnVie(false);
            newPlayer.setLabyrinthe(maps.get("FirstMap"));
            playersInRoom.add(playersInRoom.size(), newPlayer);
            claviers.add(claviers.size(), clavier);

            //syncro le jeu et le serv
            sendData(new Encapsulation(claviers,playersInRoom,maps));
            setGame(jeu);

            //Retourne l'id du nouveau joueur
            return playersInRoom.size()-1;
        } catch (IOException | InterruptedException e) {
            throw new RuntimeException(e);
        }
    }

    //Getters et Setters


    public List<Clavier> getClaviers() {
        return claviers;
    }

    public List<Player> getPlayers(){
        return playersInRoom;
    }

    public void setMaps(Map<String,Labyrinthe> m){this.maps=m;}

    public void setClaviers(List<Clavier> c){this.claviers=c;}

    public void setPlayers(List<Player> p){this.playersInRoom=p;}

}
