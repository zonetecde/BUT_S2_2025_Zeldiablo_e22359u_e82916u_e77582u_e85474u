package org.example;

import Zeldiablo.*;
import Zeldiablo.Cases.*;
import Zeldiablo.Entities.Entite;
import Zeldiablo.Entities.Monstre;
import Zeldiablo.Items.*;
import javafx.application.Application;
import javafx.event.EventHandler;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.stage.Stage;
import javafx.stage.StageStyle;


import java.io.*;
import java.util.Objects;

public class Editor extends Application {

    Labyrinthe l;
    String fileName;
    //Brush
    Case brushTile = new CaseMur();
    Monstre brushEntite = new Monstre(0,0);
    CaseSwitch brushLink;
    Item brushItem = new Amulette();

    ImageView brushImage = new ImageView(brushTile.getSprite());
    Circle curSel;

    //OKAY??
    public static void launch(boolean huh){
        launch();
    }

    /**
     * Lancement de l'app
     * @param args args fournis
     */
    public static void main(String[] args){
        launch(args);
    }

    /**
     * Methode de début lançant la selection de niveau
     * @param stage Fenetre affichant l'app
     */
    @Override
    public void start(Stage stage){
        brushImage.setFitHeight(VariablesGlobales.TAILLE_CASE);
        brushImage.setFitWidth(VariablesGlobales.TAILLE_CASE);
        stage.setScene(new Scene(launcher(stage)));
        stage.show();
    }

    /**
     * Editeur de niveaux
     * @param stage fenetre
     */
    public void editor(Stage stage){
        TabPane root = new TabPane();
        root.setTabClosingPolicy(TabPane.TabClosingPolicy.UNAVAILABLE);
        Scene scene = new Scene(root);

        Tab launchOther = new Tab("launcher",launcher(stage));

        root.getTabs().addAll(Tabs.tab(0,this)
                ,Tabs.tab(1,this)
                ,Tabs.tab(4,this)
                ,Tabs.tab(2,this)
                ,Tabs.tab(3,this),
                launchOther,
                Tabs.saveTab(this));

        stage.setScene(scene);
    }

    /**
     * Methode se chargeant de trouver sur quelle base lancer l'editeur
     * @param stage fenetre
     */
    public VBox launcher(Stage stage){
        VBox root = new VBox(30);
        root.setAlignment(Pos.CENTER);
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
        input2.setText("Laby/labyJeu/FirstMap");
        Button launch2 = new Button("Launch");
        hBox2.getChildren().addAll(input2,launch2);

        //Create
        HBox hBox3 = new HBox();
        hBox3.setAlignment(Pos.CENTER);
        TextField input3 = new TextField("FirstMap");
        input3.setPromptText("Name of the new file");
        Button launch3 = new Button("Launch");
        hBox3.getChildren().addAll(input3,launch3);

        launch1.setOnMouseClicked(mouseEvent -> {
            fileName = input.getText();
            try {

                /*JSon
                ObjectMapper mapper = new ObjectMapper();
                l =  mapper.readValue(new File("Laby/LabyBin/"+fileName), Labyrinthe.class);
                 */
                ObjectInputStream ois = new ObjectInputStream(new FileInputStream(new File("Laby/LabyBin/"+fileName)));
                l = (Labyrinthe) ois.readObject();
                editor(stage);
            } catch (IOException | ClassNotFoundException  e) {
                System.out.println(e.getMessage());
                input.setText("Chemin invalide");
            }
        });

        launch2.setOnMouseClicked(mouseEvent -> {
            fileName = input2.getText().split("/")[input2.getText().split("/").length-1];
            try {
                l = new Labyrinthe(input2.getText());
                editor(stage);
            } catch (IOException e) {
                System.out.println(e.getMessage());
                input2.setText("Chemin invalide");
            }
        });

        launch3.setOnMouseClicked(mouseEvent -> {
            fileName = input3.getText();
            if (!fileName.isEmpty()) {
                creator(stage);
            }else{
                input3.setText("Nom vide");
            }
        });

        root.getChildren().addAll(title,hBox1,hBox2,hBox3);

        return root;
    }

    /**
     * Fenetre de creation de niveau avec ses dimensions
     * @param stage fenetre
     */
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

        create.setOnMouseClicked(_ -> {
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

    /**
     * Methode de sauvegarde du niveau dans LabyBin
     */
    public void save(){
            try {
                File file = new File("Laby/LabyBin/" + fileName);
                file.createNewFile();

                ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(file));
                oos.writeObject(l);

                /* JSon
                ObjectMapper mapper = new ObjectMapper();
                mapper.enable(SerializationFeature.INDENT_OUTPUT);
                mapper.writeValue(file,l);
                */

                System.out.println("Everything's okay");
            } catch (IOException e) {
                System.out.println(e.getMessage());
                System.out.println("Erreur de sauvegarde");
            }
    }




    public Scene getStairOutput(String labyrinthe,CaseEscalier stairs,Stage stage) {
        Labyrinthe laby = MapList.getMap(labyrinthe);
        int colnum = laby.getLongueur();
        int linenum = laby.getHauteur();
        GridPane root = new GridPane();
        root.setAlignment(Pos.CENTER);


        for (int i = 0; i < colnum; i++) {
            for (int j = 0; j < linenum; j++) {
                //set de l'affichage
                //Affichage des Tiles
                Image img = laby.getCase(j, i).getSprite();
                ImageView imageView = new ImageView(img);
                imageView.setFitHeight(VariablesGlobales.TAILLE_CASE);
                imageView.setFitWidth(VariablesGlobales.TAILLE_CASE);
                root.add(imageView, i, j);

                //Set de l'action onClick des cases
                if (laby.getCase(j,i) instanceof CaseEscalier) {
                    Color c;
                    if(laby.getCase(j,i).isLinked()){c = Color.DARKVIOLET;}
                    else{c = Color.VIOLET;}
                    Circle circle = new Circle((double)VariablesGlobales.TAILLE_CASE/2,c);
                    circle.setOnMouseClicked(new Stair_Link(i, j, stairs, labyrinthe, stage));
                    root.add(circle,i,j);
                }

            }
        }

        return new Scene(root);
    }

    /**
     * Handler influant sur la case cliquée
     * @param <MenuType> type d'objet manipulé
     */
    public class MenuButtonHandler<MenuType> implements EventHandler<MouseEvent> {
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
            } else if(aCase instanceof Item){
                brushItem = (Item) aCase;
                brushImage.setImage(brushItem.getSprite());
            }

            System.out.println(brushTile);
        }
    }

    /**
     * Handler changeant la brush en une case
     */
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
            if(mouseEvent.getEventType() == MouseEvent.MOUSE_ENTERED){System.out.println("Hover");}
            if ((mouseEvent.getEventType() == MouseEvent.MOUSE_PRESSED || mouseEvent.getEventType() == MouseEvent.MOUSE_ENTERED) && mouseEvent.isPrimaryButtonDown()) {
                laby.getGameBoard()[x][y] = brushTile.clone();
                button.setImage(brushTile.getSprite());
            }
        }
    }

    /**
     * handler changeant la brush en une entite
     */
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
            if (mouseEvent.isPrimaryButtonDown()) {
                if (brushEntite != null) {
                    Entite m = brushEntite.clone(x, y);
                    m.setLabyrinthe(l);
                    button.setImage(brushEntite.getSprite());
                } else {
                    button.setImage(Sprite.getImg(VariablesGlobales.SPRITE_CASE_VIDE));
                    for (Entite e : l.getEntites()) {
                        if (e.getX() == x && e.getY() == y) {
                            l.getEntites().remove(e);
                            l.getTicks().remove(e);
                        }
                    }
                }
            }

        }
    }

    class ItemHandler implements EventHandler<MouseEvent>{
        CaseVide c;
        ImageView button;

        public ItemHandler(Case c,ImageView button){
            this.c=(CaseVide) c;
            this.button=button;
        }

        @Override
        public void handle(MouseEvent mouseEvent) {
            if (mouseEvent.isPrimaryButtonDown()) {
                if (brushItem != null) {
                    c.setItem(brushItem);
                    button.setImage(brushItem.getSprite());
                } else {
                    c.setItem(null);
                    c.setGotItem(false);
                    button.setImage(Sprite.getImg(VariablesGlobales.SPRITE_CASE_VIDE));
                }
            }
        }
    }

    class SwitchHandler implements EventHandler<MouseEvent>{
        CaseSwitch caseS;
        Circle show;

        public SwitchHandler(CaseSwitch caseS,Circle circle){
            this.caseS=caseS;
            show = circle;
        }

        @Override
        public void handle(MouseEvent mouseEvent) {
            if (curSel!=null){
                curSel.setFill(Color.BLUE);
            }
            brushLink = caseS;
            curSel=show;
            curSel.setFill(Color.GREEN);

        }
    }

    class ActivableHandler implements EventHandler<MouseEvent>{
        Case activable;

        public ActivableHandler(Case activable){
            this.activable = activable;
        }

        @Override
        public void handle(MouseEvent mouseEvent) {
            if (brushLink!=null) {
                brushLink.setLink(activable);
                curSel.setFill(Color.YELLOW);
                curSel = null;
                brushLink = null;
            }
        }
    }

    static class Stair_Link implements EventHandler<MouseEvent>{

        int x,y;
        CaseEscalier stairs;
        String labyrinthe;
        Stage stage;

        public Stair_Link(int x, int y, CaseEscalier stairs, String labyrinthe, Stage stage){
            this.x=x;
            this.y=y;
            this.stairs=stairs;
            this.labyrinthe=labyrinthe;
            this.stage=stage;
        }

        @Override
        public void handle(MouseEvent mouseEvent) {
            stairs.setNext(labyrinthe,x,y);
            stage.close();
        }
    }

    class Stairs_OpenMap implements EventHandler<MouseEvent>{

        String map;
        CaseEscalier stairs;
        Stage stage;


        public Stairs_OpenMap(String map,CaseEscalier stairs,Stage stage){
            this.map = map;
            this.stairs = stairs;
            this.stage = stage;
        }

        @Override
        public void handle(MouseEvent mouseEvent) {
            stage.setScene(getStairOutput(map,stairs,stage));
        }
    }

    class Stairs_Handler implements EventHandler<MouseEvent>{

        CaseEscalier stairs;

        public Stairs_Handler(CaseEscalier stairs){
            this.stairs=stairs;
        }

        /**
         * @param mouseEvent clic
         */
        @Override
        public void handle(MouseEvent mouseEvent) {
            VBox root = new VBox(20);
            Stage stage= new Stage(StageStyle.DECORATED);
            stage.setScene(new Scene(root,300,400));

            VBox vBox = new VBox(10);
            File file = new File("Laby/LabyBin");
            for (File f : Objects.requireNonNull(file.listFiles())){
                Button linkButton = new Button(f.getName());
                linkButton.setOnMouseClicked(new Stairs_OpenMap(f.getName(),stairs,stage));
                vBox.getChildren().add(linkButton);
            }
            ScrollPane scrollPane = new ScrollPane(vBox);

            root.getChildren().addAll(scrollPane);
            stage.show();
        }
    }




}
