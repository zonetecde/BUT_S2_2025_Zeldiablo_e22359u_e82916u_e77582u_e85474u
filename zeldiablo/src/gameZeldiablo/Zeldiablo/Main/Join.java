package gameZeldiablo.Zeldiablo.Main;

import gameZeldiablo.Zeldiablo.Entities.Player;
import gameZeldiablo.Zeldiablo.ServerRoom;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import moteurJeu.Clavier;

import java.util.Scanner;

class Join {

    static void inputJoin(Clavier clavier, Player joueur,ZeldiabloJeu jeu){
        if (clavier.bas) {
            joueur.curseurLog = (joueur.curseurLog + 1) % 3;
        } else if (clavier.haut) {
            if (joueur.curseurLog > 0) {
                joueur.curseurLog--;
            } else {
                joueur.curseurLog = 2;
            }
        } else if (clavier.space) {
            //Creation d'une nouvelle room
            switch (joueur.curseurLog) {
                case 0:
                    //Lancement en solo sans room
                    jeu.lance = true;
                    break;
                case 1:
                    //rejoindre une room
                    //Demande l'adresse
                    jeu.room = new ServerRoom();
                    Scanner sc = new Scanner(System.in);
                    System.out.println("Adresse");
                    String ad = sc.nextLine();
                    jeu.idComp = jeu.room.logIn(jeu, clavier,ad);
                    jeu.multiplayer = true;
                    jeu.lance = true;
                    break;
                case 2:
                    //Heberger une room
                    jeu.room = new ServerRoom();
                    new Thread(() -> new ServerRoom().createServer()).start();
            }
        }
    }

    static void logUI(Player joueur, GraphicsContext gc, Canvas c){
        //Fond
        gc.setFill(Color.rgb(72, 58, 160));
        gc.fillRect(0,0,c.getWidth(),c.getHeight());
        gc.setFill(Color.rgb(121, 101, 193));
        gc.setFont(Font.font("Caladea" ,44));

        double baseH = c.getHeight()/5;

        for (int i=1 ; i<4 ; i++) {
            gc.strokeRect(c.getWidth() / 3, baseH*i , c.getWidth() / 3, c.getHeight() / 7);
            gc.fillRect(c.getWidth() / 3, baseH*i , c.getWidth() / 3, c.getHeight() / 7);
        }


        //Texte
        gc.setFill(Color.BLACK);
        gc.fillText("Solo",(c.getWidth()/2.5),(baseH * 1.5));
        gc.fillText("Join" , c.getWidth()/2.5,baseH * 2.5);
        gc.fillText("Host" , c.getWidth()/2.5,baseH * 3.5);

        //Curseur
        double emCursor = switch (joueur.curseurLog) {
            case 0 -> baseH *  1.25;
            case 1 -> baseH *  2.25;
            default -> baseH * 3.25;
        };

        gc.fillOval(c.getWidth()/3-10,emCursor,20,20);
    }
}
