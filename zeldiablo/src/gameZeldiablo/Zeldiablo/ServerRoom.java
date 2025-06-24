package gameZeldiablo.Zeldiablo;

import gameZeldiablo.Zeldiablo.Entities.Player;
import gameZeldiablo.Zeldiablo.Main.ZeldiabloJeu;
import javafx.application.Platform;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.layout.VBox;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
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
    //le jeu est il initialisé?
    private volatile boolean dataReceived = false;

    //Socket de partage d'information entre les joueurs
    List<Socket> dataSocket = new ArrayList<>();
    //OutputStream pour partager des donnees
    List<ObjectOutputStream> out= new ArrayList<>();
    //InputStream pour recuperer les donnees
    List<ObjectInputStream> in = new ArrayList<>();
    //Zone de log
    TextArea logArea;

    /**
     * Methode envoyant des données sous forme d'Encapsulation
     * sur le socket
     * @param e données a envoyer
     */
    public void sendData(Encapsulation e, int sortie){
        try {
            if (sortie == -1){
                //BroadCast
                for (ObjectOutputStream oos : out) {
                    oos.writeObject(e);
                    oos.flush();
                }
            }else{
                out.get(sortie).writeObject(e);
                out.get(sortie).flush();
            }
            if (isServer()) {
                logServer("Server sent Data to "+sortie);
            }else{
                System.out.println("Client sent data "+ game.idComp);
            }
        } catch (IOException ex) {
            throw new RuntimeException(ex);
        }
    }

    public void logServer(String s){
        if (logArea!=null) {
            Platform.runLater(() -> logArea.appendText(s + "\n"));
        }else{System.out.println("Erreur de log \n" + s);}
    }

    /**
     * Boucle infinie recuperant des données sous forme
     * d'Encapsulation et les applique au server
     */
    public void gettingData(int i) {
        try {
            while (true) {
                Encapsulation tmp = (Encapsulation) in.get(i).readObject();
                if (tmp.concerne==5){
                        logServer("Player "+i+" asked for broad data");
                        if (!playersInRoom.isEmpty()) {
                            sendData(Encapsulation.ASK_PLAYERS, 0);
                            Thread.sleep(100);
                        }
                        sendData(new Encapsulation(claviers, playersInRoom, maps),-1);
                } else {
                    tmp.getData(this, game);
                    if (isServer()) {
                        for (int j = 0 ; j<out.size() ; j++) {
                            if (j!=i) {
                                sendData(tmp, j);
                            }
                        }
                        logServer("Server got Data from " + i + " : " + tmp);
                    }else{
                        System.out.println("Client got data : "+ tmp);
                    }
                }
            }
        } catch (EOFException e) {
            System.out.println("Connexion terminée proprement.");
        } catch (IOException | ClassNotFoundException | InterruptedException e) {
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
    }

    /**
     * Initialise le server à partir du jeu actuel
     */
    public void initServer(){
        maps = MapList.getMapList();
    }

    public void affichageServer(String name){
        Platform.runLater(() -> {
            VBox vBox= new VBox(new Label(name));
            vBox.setAlignment(Pos.CENTER);

            VBox hBox = new VBox(20);
            logArea = new TextArea();
            logArea.setEditable(false);
            logArea.setWrapText(true);
            logServer("Creation server");
            hBox.getChildren().add(logArea);
            vBox.getChildren().add(hBox);
            Scene scene = new Scene(vBox,400,400);

            //Stage
            Stage stage = new Stage(StageStyle.UTILITY);
            stage.initModality(Modality.NONE);
            stage.setScene(scene);
            stage.show();
        });
    }

    /**
     * Methode de creation de room depuis les maps existantes
     */
    public void createServer(){
        MapList.initialisation();
        initServer();
        try {
            ServerSocket getSocket = new ServerSocket(8000);
            //Cherche indefiniment des nouveaux joueurs
            affichageServer(getSocket.getInetAddress().toString());
            new Thread(() -> {
                try {
                    while (true) {
                        Socket socket = getSocket.accept();
                        dataSocket.add(socket);
                        out.add(new ObjectOutputStream(socket.getOutputStream()));
                        in.add(new ObjectInputStream(socket.getInputStream()));
                        logServer("Client Connecté");
                        // Démarrer la réception ici si c’est le 1er client
                        int tmp = in.size()-1;
                        new Thread(() -> gettingData(tmp)).start();
                    }
                } catch (IOException e) {
                    throw new RuntimeException(e);
                }
            }).start();
        } catch(IOException e){
            throw new RuntimeException(e);
        }
    }

    /**
     * Methode pour rejoindre une room
     * @param jeu jeu actuel
     * @param clavier clavier actuel
     * @return id du nouveau joueur
     */
    public int logIn(ZeldiabloJeu jeu, Clavier clavier,String adresse){
        this.game=jeu;
        try {
            InetAddress address = InetAddress.getByName(adresse);

            //Se connecte au server
            Socket tmp = new Socket(address,8000);
            dataSocket.add(tmp);
            out.add(new ObjectOutputStream(tmp.getOutputStream()));
            in.add(new ObjectInputStream(tmp.getInputStream()));
            jeu.roomID = tmp.getInetAddress().toString();

            //lance la reception de données
            new Thread(() -> gettingData(0)).start();

            //recupere le server
            sendData(Encapsulation.ASK_DATA,-1);
            while (!dataReceived){
                Thread.sleep(50);
                System.out.println("waiting in vain");
            }

            //Ajoute un joueur à la room
            Player newPlayer = new Player(0,0,5,2);
            newPlayer.setEnVie(false);
            newPlayer.setLabyrinthe(maps.get("FirstMap"));
            playersInRoom.add(playersInRoom.size(), newPlayer);
            claviers.add(claviers.size(), clavier);

            //syncro le jeu et le serv
            setGame(jeu);
            sendData(new Encapsulation(claviers,playersInRoom,maps),-1);

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

    public void setMaps(Map<String,Labyrinthe> m){this.maps=m;}

    public void setClaviers(List<Clavier> c){this.claviers=c;}

    public void setPlayers(List<Player> p){this.playersInRoom=p;}

    public boolean isServer(){return (this.game==null);}

    public void setDataReceived(boolean d){this.dataReceived=d;}

}
