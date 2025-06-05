package main.java.com.example.zeldiablofx;

import gameZeldiablo.Zeldiablo.Labyrinthe;
import javafx.application.Application;
import javafx.event.EventHandler;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;


import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.util.Scanner;

public class MainEditor extends Application {
    Labyrinthe l;
    String fileName;

    public static void main(String[] args){
        launch(args);
    }


    @Override
    public void start(Stage stage) throws Exception {
        launcher(stage);
        stage.show();
    }

    public void launcher(Stage stage){
        VBox root = new VBox(30);
        root.setAlignment(Pos.CENTER);
        Scene scene = new Scene(root,800,800);
        Label label = new Label("Editeur de niveau");
        TextField input = new TextField();
        Button go = new Button("Launch");

        go.setOnMouseClicked(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent mouseEvent) {
                fileName = input.getText();
                File file = new File(input.getText());
                try {
                        ObjectInputStream ois = new ObjectInputStream(new FileInputStream(file));
                        l = (Labyrinthe) ois.readObject();
                        System.out.println("Test");
                }catch (Exception e) {
                        creator(stage);
                    }
                }

            });

        root.getChildren().addAll(label,input,go);

        stage.setScene(scene);
    }

    public void creator(Stage stage){
        VBox root = new VBox();
        Scene scene = new Scene(root);

        Button create = new Button("Create");
        TextField x = new TextField();
        x.setPromptText("Largeur");
        TextField y = new TextField();
        y.setPromptText("Hauteur");

        create.setOnMouseClicked(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent mouseEvent) {
                try{
                    l = new Labyrinthe(Integer.parseInt(x.getText()),Integer.parseInt(y.getText()),fileName);

                }
                catch (Exception ignore){}
            }
        });

        root.getChildren().addAll(x,y,create);
        stage.setScene(scene);
    }
}
