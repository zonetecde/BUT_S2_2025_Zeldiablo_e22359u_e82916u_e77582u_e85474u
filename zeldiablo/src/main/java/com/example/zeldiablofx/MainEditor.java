package main.java.com.example.zeldiablofx;

import gameZeldiablo.Zeldiablo.Cases.*;
import gameZeldiablo.Zeldiablo.Labyrinthe;
import gameZeldiablo.Zeldiablo.VariablesGlobales;
import javafx.application.Application;
import javafx.event.EventHandler;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;


import java.io.*;
import java.util.ArrayList;
import java.util.List;

public class MainEditor extends Application {

    Labyrinthe l;
    String fileName;
    Case brush;

    public static void main(String[] args){
        launch(args);
    }


    @Override
    public void start(Stage stage) throws Exception {
        launcher(stage);
        stage.show();
    }


    public void editor(Stage stage){
        int colnum = l.getLongueur();
        int linenum = l.getHauteur();

        HBox root = new HBox();
        root.setAlignment(Pos.CENTER);
        Scene scene = new Scene(root,(this.l.getLongueur()+1)* VariablesGlobales.TAILLE_CASE+20,this.l.getHauteur()*VariablesGlobales.TAILLE_CASE);
        GridPane carte = new GridPane();


        //Creation des cases
        ImageView[][] map  = new ImageView[colnum][linenum];

        for (int i=0;i<colnum;i++){
            for (int j=0;j<linenum;j++){
                Image img = l.getCase(j,i).getSprite();
                //set de l'affichage
                ImageView imageView = new ImageView(img);
                imageView.setFitHeight(VariablesGlobales.TAILLE_CASE);
                imageView.setFitWidth(VariablesGlobales.TAILLE_CASE);

                imageView.setOnMouseClicked(new CaseHandler(l,j,i,imageView));
                map[i][j] = imageView;
                carte.add(imageView,i,j);
            }
        }

        //Menu selection case
        ScrollPane menu = new ScrollPane(menuCases());


        root.getChildren().addAll(menu,carte);
        stage.setScene(scene);
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
        input2.setText("Laby/labyJeu/labyjeu1.txt");
        Button launch2 = new Button("Launch");
        hBox2.getChildren().addAll(input2,launch2);

        //Create
        HBox hBox3 = new HBox();
        hBox3.setAlignment(Pos.CENTER);
        TextField input3 = new TextField();
        input3.setPromptText("Name of the new file");
        Button launch3 = new Button("Launch");
        hBox3.getChildren().addAll(input3,launch3);

        launch1.setOnMouseClicked(mouseEvent -> {
            fileName = input.getText();
            try {
                ObjectInputStream ois = new ObjectInputStream(new FileInputStream("Laby/LabyBin/"+fileName));
                l = (Labyrinthe) ois.readObject();
                editor(stage);
            } catch (IOException e) {
                System.out.println(e.getMessage());
                input.setText("Chemin invalide");
            } catch (ClassNotFoundException e) {
                System.out.println(e.getMessage());
                input.setText("Fichier Corrompu");
            }
        });

        launch2.setOnMouseClicked(mouseEvent -> {
            fileName = input2.getText();
            try {
                l = new Labyrinthe(fileName, null);
                editor(stage);
            } catch (IOException e) {
                System.out.println(e.getMessage());
                input2.setText("Chemin invalide");
            }
        });

        launch3.setOnMouseClicked(mouseEvent -> {
            fileName = input3.getText();
            creator(stage);
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

        create.setOnMouseClicked(mouseEvent -> {
            try{
                int xi = Integer.parseInt(x.getText());
                int yi = Integer.parseInt(y.getText());
                l = new Labyrinthe(xi,yi,fileName);
                editor(stage);
            }catch (Exception e){
                System.out.println(e.getMessage());
                System.out.println("Valeurs mauvaises");
            }

        });

        root.getChildren().addAll(lignex,ligney,create);
        stage.setScene(scene);
    }

    public VBox menuCases(){
        VBox menu = new VBox();
        List<Button> casebutton = new ArrayList<>();
        List<Case> typeCase = new ArrayList<>();
        typeCase.add(new CaseMur(0,0));
        typeCase.add(new CaseVide(0,0));

        //Bouton de save
        Button save = new Button("Save");
        save.setOnMouseClicked(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent mouseEvent) {
                save();
            }
        });
        menu.getChildren().add(save);


        for (Case aCase : typeCase) {
            //Creation du bouton
            Button button = new Button();
            //Cration d'une imageview adaptee
            ImageView imageView = new ImageView(aCase.getSprite());
            imageView.setFitWidth(VariablesGlobales.TAILLE_CASE);
            imageView.setFitHeight(VariablesGlobales.TAILLE_CASE);
            button.setGraphic(imageView);
            //Ajout du bouton à la liste de boutons
            casebutton.add(button);
            menu.getChildren().add(button);
            button.setOnMouseClicked(new MenuButtonHandler(aCase));
        }

        return menu;
    }

    public void save(){
        try {
            File file = new File("Laby/LabyBin/" + fileName);
            file.createNewFile();
            ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(file));
            oos.writeObject(l);
            System.out.println("Everything's okay");
        } catch (IOException e) {
            System.out.println(e.getMessage());
            System.out.println("Erreur de sauvegarde");
        }
    }

    class MenuButtonHandler implements EventHandler<MouseEvent> {
        Case aCase;
        public MenuButtonHandler(Case c){
            aCase = c;
        }

        @Override
        public void handle(MouseEvent mouseEvent) {
            brush= aCase;
            System.out.println(brush);
        }
    }

    class CaseHandler implements EventHandler<MouseEvent>{
        Labyrinthe laby;
        int x,y;
        ImageView button;
        public CaseHandler(Labyrinthe l,int x,int y,ImageView button){
            laby=l;
            this.x=x;
            this.y=y;
            this.button= button;
        }

        @Override
        public void handle(MouseEvent mouseEvent) {
            l.getGameBoard()[x][y]=brush;
            button.setImage(brush.getSprite());

        }
    }
}
