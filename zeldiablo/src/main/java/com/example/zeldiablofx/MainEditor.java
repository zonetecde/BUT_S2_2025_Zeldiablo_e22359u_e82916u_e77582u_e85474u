package main.java.com.example.zeldiablofx;

import gameZeldiablo.Zeldiablo.Cases.*;
import gameZeldiablo.Zeldiablo.Entities.Entite;
import gameZeldiablo.Zeldiablo.Entities.Intelligence;
import gameZeldiablo.Zeldiablo.Entities.Monstre;
import gameZeldiablo.Zeldiablo.Items.*;
import gameZeldiablo.Zeldiablo.Labyrinthe;
import gameZeldiablo.Zeldiablo.MapList;
import gameZeldiablo.Zeldiablo.Sprited;
import gameZeldiablo.Zeldiablo.VariablesGlobales;
import javafx.application.Application;
import javafx.event.EventHandler;
import javafx.geometry.HPos;
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
import java.util.ArrayList;
import java.util.List;

public class MainEditor extends Application {

    Labyrinthe l;
    String fileName;
    //Brush
    Case brushTile = new CaseMur();
    Monstre brushEntite = new Monstre(0,0);
    CaseSwitch brushLink;
    Item brushItem = new Amulette();

    ImageView brushImage = new ImageView(brushTile.getSprite());
    Circle curSel;

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

        root.getTabs().addAll(this.tab(false,false)
                ,this.tab(true,false)
                ,this.tab(false,true)
                ,this.linkTab(),this.stairTab(),
                launchOther,
                saveTab());

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

    /**
     * Methode de sauvegarde du niveau dans LabyBin
     */
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

    public Tab saveTab(){
        VBox root = new VBox(20);
        root.setAlignment(Pos.TOP_CENTER);
        Tab tab = new Tab("Save",root);

        Button button = new Button("Save");
        TextField textField = new TextField(this.fileName);
        textField.setAlignment(Pos.CENTER);
        root.getChildren().addAll(textField,button);

        button.setOnMouseClicked(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent mouseEvent) {
                fileName= textField.getText();
                save();
            }
        });

        return tab;

    }

    /**
     * GridPane gerant l'affichage de la map
     * @param tile affichage des tiles?
     * @param ennemy affichage des ennemis?
     * @return Grille du niveau
     */
    public GridPane grille(boolean tile, boolean ennemy, boolean link, boolean items,boolean stairs){
        int colnum = l.getLongueur();
        int linenum = l.getHauteur();
        GridPane root = new GridPane();
        root.setAlignment(Pos.CENTER);


        for (int i=0;i<colnum;i++){
            for (int j=0;j<linenum;j++){
                //set de l'affichage
                //Affichage des Tiles
                if (tile) {
                    Image img = l.getCase(j,i).getSprite();
                    ImageView imageView = new ImageView(img);
                    imageView.setFitHeight(VariablesGlobales.TAILLE_CASE);
                    imageView.setFitWidth(VariablesGlobales.TAILLE_CASE);

                    //Set de l'action onClick des cases
                    if (!ennemy && !items && !link) {
                        imageView.setOnMouseClicked(new CaseHandler(l, j, i, imageView));
                    }else if(!items && !link){
                        imageView.setOnMouseClicked(new EntityHandler(l, i, j, imageView));
                    }else if (!link){
                        imageView.setOnMouseClicked(new ItemHandler(l.getCase(j,i),imageView));
                    }
                    root.add(imageView,i,j);
                }

                //Affichage des ennemis
                if (ennemy){
                    for (int k = 0; k<l.getEntites().size(); k++){
                        Entite m = l.getEntites().get(k);
                        if (m.getY()==j && m.getX()==i){
                            Image img = m.getSprite();
                            ImageView imageView = new ImageView(img);
                            imageView.setFitHeight(VariablesGlobales.TAILLE_CASE);
                            imageView.setFitWidth(VariablesGlobales.TAILLE_CASE);
                            root.add(imageView,i,j);
                        }
                    }
                }
                //Affichage des liens
                if (link) {
                    if (l.getCase(j, i).isActivable()) {
                        Circle circle = new Circle((double) VariablesGlobales.TAILLE_CASE / 2, Color.RED);
                        root.add(circle, i, j);
                        circle.setOnMouseClicked(new AcivableHandler(l.getCase(j, i)));

                        CasePorte casePorte = (CasePorte) (l.getCase(j, i));
                        Label label = new Label(casePorte.getId() + "");
                        GridPane.setHalignment(label, HPos.CENTER);
                        root.add(label, i, j);
                    }
                    if (l.getCase(j, i).isActivate()) {
                        //Couleur differente si deja lié ou pas
                        Color c;
                        if (l.getCase(j, i).isLinked()) {
                            c = Color.YELLOW;
                        } else {
                            c = Color.BLUE;
                        }
                        Circle circle = new Circle((double) VariablesGlobales.TAILLE_CASE / 2, c);
                        root.add(circle, i, j);
                        //Texte
                        try {
                            CaseSwitch caseSwitch = (CaseSwitch) (l.getCase(j, i));
                            Label label = new Label(caseSwitch.getLink().getId() + "");
                            GridPane.setHalignment(label, HPos.CENTER);
                            root.add(label, i, j);
                        } catch (Exception ignore) {
                        }
                        circle.setOnMouseClicked(new SwitchHandler((CaseSwitch) l.getCase(j, i), circle));
                    }
                }

                if (stairs){
                    if(l.getCase(j,i) instanceof CaseEscalier){
                        Color c;
                        if(l.getCase(j,i).isLinked()){c = Color.DARKVIOLET;}
                        else{c = Color.VIOLET;}
                        Circle circle = new Circle((double) VariablesGlobales.TAILLE_CASE /2, c);
                        root.add(circle,i,j);
                        circle.setOnMouseClicked(new StairsHandler((CaseEscalier) l.getCase(j,i)));
                    }
                }

                //Affichage des Items
                if (items){
                    Case cur = l.getCase(j,i);
                    if(cur.hasItem()){
                        ImageView imageView = new ImageView(cur.getItem().getSprite());
                        imageView.setFitWidth(VariablesGlobales.TAILLE_CASE);
                        imageView.setFitHeight(VariablesGlobales.TAILLE_CASE);
                        root.add(imageView,i,j);
                    }
                }
            }
        }

        return root;
    }

    /**
     * Creation des tab de l'editeur
     * @param monstre si le tab concerne les monstres
     * @return un Tab fonctionnel
     */
    public Tab tab(boolean monstre,boolean item){
        Tab root;
        if (item){
            root = new Tab("Items");
        } else if (monstre) {
            root = new Tab("Monstre");
        }else{
            root = new Tab("Tiles");
        }
        root.setOnSelectionChanged(event -> {
            HBox main = new HBox(10);
            main.setAlignment(Pos.CENTER);
            //Menu Droite
            VBox menuRight = new VBox(30);
            menuRight.setAlignment(Pos.BOTTOM_RIGHT);
            menuRight.getChildren().add(brushImage);

            //TabTiles
            ScrollPane carte = new ScrollPane(grille(true,monstre,false,item,false));

            //Menu selection case
            ScrollPane menu = new ScrollPane(menu(monstre,item));



            main.getChildren().addAll(menu,carte,menuRight);
            root.setContent(main);
        });

        return root;
    }

    /**
     * Creation du tab des link
     * @return tab
     */
    public Tab linkTab(){
        Tab root = new Tab("Links");
        root.setOnSelectionChanged(event -> {
            root.setContent(new ScrollPane(grille(true, false, true,false,false)));
        });

        return root;
    }

    /**
     * Creation du tab des stairs
     * @return tab
     */
    public Tab stairTab(){
        Tab root = new Tab("Stairs");
        root.setOnSelectionChanged(event -> {
            root.setContent(new ScrollPane(grille(true, false, false,false,true)));
        });

        return root;
    }

    /**
     * Menus de selection des brush
     * @param monstre si la brush concerne monstre
     * @return une liste de bouton brush
     */
    public VBox menu(boolean monstre,boolean item){
        VBox menu = new VBox();
        List<Sprited> type;

        //Liste des brush
        if (item){
            type = new ArrayList<>();
            type.add(new Amulette());
            type.add(new Food());
            type.add(new Epee());
            type.add(new Hache());
            type.add(new ItemDefault());


        } else if (monstre) {
            type = new ArrayList<>();
            for (int i = 0; i < Intelligence.values().length; i++) {
                type.add(new Monstre(0, 0, Intelligence.values()[i],null));
            }

        }else {
            type = new ArrayList<>();
            type.add(new CaseMur());
            type.add(new CaseVide());
            type.add(new CaseEscalier(false));
            type.add(new CaseEscalier(true));
            type.add(new CaseSwitch());
            type.add(new CasePorte());
            type.add(new CasePancarte("Default"));
            type.add(new CasePiege(5));
            type.add(new CaseSpawn());
        }

        for (Sprited s : type) {
            //Creation du bouton
            Button button = new Button();
            //Cration d'une imageview adaptee
            ImageView imageView = new ImageView(s.getSprite());
            imageView.setFitWidth(VariablesGlobales.TAILLE_CASE);
            imageView.setFitHeight(VariablesGlobales.TAILLE_CASE);
            button.setGraphic(imageView);
            //Ajout du bouton à la liste de boutons
            menu.getChildren().add(button);
            button.setOnMouseClicked(new MenuButtonHandler<>(s));
        }

        return menu;
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
                    circle.setOnMouseClicked(new StairLink(i, j, stairs, labyrinthe, stage));
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
            } else if(aCase instanceof Item){
                brushItem = (Item) aCase;
                brushImage.setImage(brushEntite.getSprite());
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
            l.getGameBoard()[x][y]= brushTile.clone();
            button.setImage(brushTile.getSprite());

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
            Entite m = brushEntite.clone(x,y);
            m.setLabyrinthe(l);
            button.setImage(brushEntite.getSprite());

        }
    }

    class ItemHandler implements EventHandler<MouseEvent>{
        Case c;
        ImageView button;

        public ItemHandler(Case c,ImageView button){
            this.c=c;
            this.button=button;
        }

        @Override
        public void handle(MouseEvent mouseEvent) {
            c.addItem(brushItem);
            button.setImage(brushItem.getSprite());
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

    class AcivableHandler implements EventHandler<MouseEvent>{
        Case activable;

        public AcivableHandler(Case activable){
            this.activable = activable;
        }

        @Override
        public void handle(MouseEvent mouseEvent) {
            if (brushLink!=null) {
                brushLink.createLink(activable);
                curSel.setFill(Color.YELLOW);
                curSel = null;
                brushLink = null;
            }
        }
    }

    static class StairLink implements EventHandler<MouseEvent>{

        int x,y;
        CaseEscalier stairs;
        String labyrinthe;
        Stage stage;

        public StairLink(int x, int y, CaseEscalier stairs, String labyrinthe, Stage stage){
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

    class StairsHandler implements EventHandler<MouseEvent>{

        CaseEscalier stairs;

        public StairsHandler(CaseEscalier stairs){
            this.stairs=stairs;
        }

        /**
         * @param mouseEvent clic
         */
        @Override
        public void handle(MouseEvent mouseEvent) {
            TextField textField = new TextField();
            textField.setPromptText("A quelle map relier l'escalier?");
            Button button = new Button("Go");
            button.setDefaultButton(true);

            Stage stage= new Stage(StageStyle.DECORATED);
            VBox root = new VBox(20);
            root.getChildren().addAll(textField,button);
            stage.setScene(new Scene(root));
            stage.show();

            button.setOnMouseClicked(new EventHandler<MouseEvent>() {
                @Override
                public void handle(MouseEvent mouseEvent) {
                    stage.setScene(getStairOutput(textField.getText(),stairs,stage));
                }
            });
        }
    }




}
