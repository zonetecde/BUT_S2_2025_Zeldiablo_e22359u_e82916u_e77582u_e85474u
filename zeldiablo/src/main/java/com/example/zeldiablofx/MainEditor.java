package main.java.com.example.zeldiablofx;

import gameZeldiablo.Zeldiablo.Cases.*;
import gameZeldiablo.Zeldiablo.Entities.Entite;
import gameZeldiablo.Zeldiablo.Entities.Intelligence;
import gameZeldiablo.Zeldiablo.Entities.Monstre;
import gameZeldiablo.Zeldiablo.Labyrinthe;
import gameZeldiablo.Zeldiablo.VariablesGlobales;
import javafx.application.Application;
import javafx.event.Event;
import javafx.event.EventHandler;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.*;
import javafx.stage.Stage;


import java.io.*;
import java.util.ArrayList;
import java.util.List;

public class MainEditor extends Application {

    Labyrinthe l;
    String fileName;
    Case brushTile = new CaseMur(0,0);
    Monstre brushEntite = new Monstre(0,0);
    ImageView brushImage = new ImageView(brushTile.getSprite());

    public static void main(String[] args){
        launch(args);
    }


    @Override
    public void start(Stage stage) throws Exception {
        brushImage.setFitHeight(VariablesGlobales.TAILLE_CASE);
        brushImage.setFitWidth(VariablesGlobales.TAILLE_CASE);
        launcher(stage);
        stage.show();
    }


    public void editor(Stage stage){
        TabPane root = new TabPane();
        root.setTabClosingPolicy(TabPane.TabClosingPolicy.UNAVAILABLE);
        Scene scene = new Scene(root/*,(this.l.getLongueur()+1)* VariablesGlobales.TAILLE_CASE+30,this.l.getHauteur()*VariablesGlobales.TAILLE_CASE+30*/);

        root.getTabs().addAll(this.tabTile(),this.tabEnnemy());
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
            fileName = input2.getText().split("/")[input2.getText().split("/").length-1];
            try {
                l = new Labyrinthe(input2.getText(), null);
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
                l = new Labyrinthe(xi,yi);
                editor(stage);
            }catch (Exception e){
                System.out.println(e.getMessage());
                System.out.println("Valeurs mauvaises");
            }

        });

        root.getChildren().addAll(lignex,ligney,create);
        stage.setScene(scene);
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

    public GridPane grille(boolean tile, boolean ennemy){
        int colnum = l.getLongueur();
        int linenum = l.getHauteur();
        GridPane root = new GridPane();
        root.setAlignment(Pos.CENTER);


        for (int i=0;i<colnum;i++){
            for (int j=0;j<linenum;j++){
                //set de l'affichage
                if (tile) {
                    Image img = l.getCase(j,i).getSprite();
                    ImageView imageView = new ImageView(img);
                    imageView.setFitHeight(VariablesGlobales.TAILLE_CASE);
                    imageView.setFitWidth(VariablesGlobales.TAILLE_CASE);
                    if (!ennemy) {
                        imageView.setOnMouseClicked(new CaseHandler(l, j, i, imageView));
                    }
                    root.add(imageView,i,j);
                }
                if (ennemy){
                    for (int k=0;k<l.getMonstres().size();k++){
                        Monstre m = l.getMonstres().get(k);
                        if (m.getY()==i && m.getX()==j){
                            Image img = m.getSprite();
                            ImageView imageView = new ImageView(img);
                            imageView.setFitHeight(VariablesGlobales.TAILLE_CASE);
                            imageView.setFitWidth(VariablesGlobales.TAILLE_CASE);
                            imageView.setOnMouseClicked(new EntityHandler(l, j, i, imageView));
                            root.add(imageView,i,j);
                        }
                    }
                }


            }
        }

        return root;
    }

    public Tab tabTile(){
        Tab root = new Tab("Tiles");
        root.setOnSelectionChanged(new EventHandler<Event>() {
            @Override
            public void handle(Event event) {
                HBox main = new HBox(10);
                main.setAlignment(Pos.CENTER);
                //Menu Droite
                VBox menuRight = new VBox(30);
                menuRight.setAlignment(Pos.BOTTOM_RIGHT);
                menuRight.getChildren().add(brushImage);

                //TabTiles
                GridPane carte = grille(true,false);

                //Menu selection case
                ScrollPane menu = new ScrollPane(menuCases());



                main.getChildren().addAll(menu,carte,menuRight);
                root.setContent(main);
            }
        });

        return root;
    }

    public Tab tabEnnemy(){
        Tab root = new Tab("Ennemy");
        root.setOnSelectionChanged(new EventHandler<Event>() {
            @Override
            public void handle(Event event) {
               HBox main = new HBox(10);
               main.setAlignment(Pos.CENTER);
                //Menu Droite
                VBox menuRight = new VBox(30);
                menuRight.setAlignment(Pos.BOTTOM_RIGHT);
                menuRight.getChildren().add(brushImage);

               //TabTiles
               GridPane carte = grille(true,true);
               //Menu selection case
               ScrollPane menu = new ScrollPane(menuEnnemy());

               main.getChildren().addAll(menu,carte,menuRight);
               root.setContent(main);
           }
       });

        return root;
    }

    public VBox menuCases(){
        VBox menu = new VBox();
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
            menu.getChildren().add(button);
            button.setOnMouseClicked(new MenuButtonHandler<Case>(aCase));
        }

        return menu;
    }

    public VBox menuEnnemy(){
        VBox menu = new VBox();
        List<Monstre> typeMonstre = new ArrayList<>();
        for (int i=0;i<Intelligence.values().length;i++) {
            typeMonstre.add(new Monstre(0,0,Intelligence.values()[i]));
        }

        //Bouton de save
        Button save = new Button("Save");
        save.setOnMouseClicked(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent mouseEvent) {
                save();
            }
        });
        menu.getChildren().add(save);


        for (Monstre m : typeMonstre) {
            //Creation du bouton
            Button button = new Button();
            //Cration d'une imageview adaptee
            ImageView imageView = new ImageView(m.getSprite());
            imageView.setFitWidth(VariablesGlobales.TAILLE_CASE);
            imageView.setFitHeight(VariablesGlobales.TAILLE_CASE);
            button.setGraphic(imageView);
            //Ajout du bouton à la liste de boutons
            menu.getChildren().add(button);
            button.setOnMouseClicked(new MenuButtonHandler<Monstre>(m));
        }

        return menu;
    }

    class MenuButtonHandler<MenuType> implements EventHandler<MouseEvent> {
        MenuType aCase;
        public MenuButtonHandler(MenuType c){
            aCase = c;
        }

        @Override
        public void handle(MouseEvent mouseEvent) {
            if (aCase instanceof Case) {
                brushTile = (Case)aCase;
                brushImage.setImage(brushTile.getSprite());

            } else if(aCase instanceof Monstre){
                brushEntite = (Monstre) aCase;
                brushImage.setImage(brushEntite.getSprite());
            }

            System.out.println(brushTile);
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
            l.getGameBoard()[x][y]= brushTile;
            button.setImage(brushTile.getSprite());

        }
    }

    class EntityHandler implements EventHandler<MouseEvent>{
        Labyrinthe laby;
        int x,y;
        ImageView button;
        public EntityHandler(Labyrinthe l,int x,int y,ImageView button){
            laby=l;
            this.x=x;
            this.y=y;
            this.button= button;
        }

        @Override
        public void handle(MouseEvent mouseEvent) {
            Monstre m = (Monstre)brushEntite.clone(y,x);
            l.getMonstres().add(m);
            button.setImage(brushEntite.getSprite());

        }
    }


}
