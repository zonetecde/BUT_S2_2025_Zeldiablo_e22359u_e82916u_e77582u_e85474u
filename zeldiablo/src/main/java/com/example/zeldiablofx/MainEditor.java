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
import javafx.scene.layout.HBox;
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
        Label title = new Label("Editeur de niveau");

        //Load from Bin
        HBox hBox1 = new HBox();
        hBox1.setAlignment(Pos.CENTER);
        TextField input = new TextField();
        input.setPromptText("BinFile");
        Button launch1 = new Button("Launch");
        hBox1.getChildren().addAll(input,launch1);

        //Load from Text
        HBox hBox2 = new HBox();
        hBox2.setAlignment(Pos.CENTER);
        TextField input2 = new TextField();
        input2.setPromptText("Name of the text file");
        Button launch2 = new Button("Launch");
        hBox2.getChildren().addAll(input2,launch2);

        //Create
        HBox hBox3 = new HBox();
        hBox3.setAlignment(Pos.CENTER);
        TextField input3 = new TextField();
        input3.setPromptText("Name of the new file");
        Button launch3 = new Button("Launch");
        hBox3.getChildren().addAll(input3,launch3);

        launch2.setOnMouseClicked(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent mouseEvent) {
                fileName = input2.getText();
                try {
                    l = new Labyrinthe(fileName, null);

                    //TODO Lancement de l'editeur
                } catch (IOException e) {
                    input2.setText("Chemin invalide");
                }
            }

        });

        launch3.setOnMouseClicked(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent mouseEvent) {
                fileName = input3.getText();
                creator(stage);
            }
        });

        root.getChildren().addAll(title,hBox1,hBox2,hBox3);

        stage.setScene(scene);
    }

    public void creator(Stage stage){
        VBox root = new VBox(30);
        root.setAlignment(Pos.CENTER);
        Scene scene = new Scene(root,800,800);

        //Bouton create
        Button create = new Button("Create");

        //Ligne pour x
        HBox lignex = new HBox();
        lignex.setAlignment(Pos.CENTER);
        Label label1 = new Label("Largeur");
        TextField x = new TextField();
        x.setPromptText("Largeur");
        lignex.getChildren().addAll(label1,x);

        //Ligne pour y
        HBox ligney = new HBox();
        ligney.setAlignment(Pos.CENTER);
        Label label2 = new Label("Hauteur");
        TextField y = new TextField();
        y.setPromptText("Hauteur");
        ligney.getChildren().addAll(label2,y);

        create.setOnMouseClicked(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent mouseEvent) {
                try{
                    int xi = Integer.parseInt(x.getText());
                    int yi = Integer.parseInt(y.getText());
                    l = new Labyrinthe(xi,yi,fileName);
                }catch (Exception e){
                    System.out.println("Valeurs mauvaises");
                }

            }
        });

        root.getChildren().addAll(lignex,ligney,create);
        stage.setScene(scene);
    }
}
