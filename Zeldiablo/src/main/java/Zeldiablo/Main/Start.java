package Zeldiablo.Main;

import Zeldiablo.Cases.CaseSpawn;
import Zeldiablo.Entities.Player;
import Zeldiablo.MapList;
import Zeldiablo.VariablesGlobales;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import moteurJeu.Clavier;

import java.io.File;

class Start {
    static void inputsStart(Clavier clavier, Player joueur, ZeldiabloJeu jeu){
        if (clavier.haut || clavier.bas){joueur.curseurStart=!joueur.curseurStart;}

        else if (clavier.space){
            if (joueur.curseurStart) {
                if (jeu.multiplayer) {
                    jeu.room.setGame(jeu);
                }
                joueur.setLabyrinthe(MapList.getMap("FirstMap"));
                joueur.getLabyrinthe().launchTickLoop();
                joueur.reset();
                //Set du joueur sur un point de spawn
                for (int i=0;i<joueur.getLabyrinthe().getLongueur();i++){
                    for (int j=0;j<joueur.getLabyrinthe().getHauteur();j++){
                        if (joueur.getLabyrinthe().getCase(j,i) instanceof CaseSpawn){
                            joueur.setX(i);
                            joueur.setY(j);
                        }
                    }
                }
                if (!jeu.animationItem.isAlive()) {
                    jeu.animationItem.start();
                }


                joueur.setaGagne(false);
                joueur.setEnVie(true);

                //MediaPLayer
                jeu.getMediaPlayer().play();
            }
            else{
                System.exit(0);
            }
        }
    }

    static void startUI(Player joueur, GraphicsContext gc){
        Canvas c = gc.getCanvas();
        gc.clearRect(0,0,c.getWidth(),c.getHeight());
        //Fond
        gc.setFill(Color.rgb(72, 58, 160));
        gc.fillRect(0,0,c.getWidth(),c.getHeight());
        gc.setFill(Color.rgb(121, 101, 193));
        gc.setFont(Font.font("Caladea" ,44));

        double baseH = c.getHeight()/4;

        for (int i=1 ; i<3 ; i++) {
            gc.strokeRect(c.getWidth() / 3, baseH*i , c.getWidth() / 3, c.getHeight() / 7);
            gc.fillRect(c.getWidth() / 3, baseH*i , c.getWidth() / 3, c.getHeight() / 7);
        }

        //Texte
        gc.setFill(Color.BLACK);
        gc.fillText("Start",(c.getWidth()/2.5),baseH * 1.4);
        gc.fillText("Leave" , c.getWidth()/2.5,baseH * 2.4);

        //Curseur
        double emCursor;
        if (joueur.curseurStart){
            emCursor=baseH * 1.25;
        }
        else{
            emCursor=baseH * 2.25;
        }

        gc.fillOval(c.getWidth()/3-10,emCursor,20,20);
    }
}
