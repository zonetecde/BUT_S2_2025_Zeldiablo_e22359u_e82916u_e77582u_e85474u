package main.java.com.example.zeldiablofx;

import gameZeldiablo.Zeldiablo.Cases.*;
import gameZeldiablo.Zeldiablo.Entities.Intelligence;
import gameZeldiablo.Zeldiablo.Entities.Monstre;
import gameZeldiablo.Zeldiablo.Labyrinthe;
import gameZeldiablo.Zeldiablo.VariablesGlobales;
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


import java.io.*;
import java.util.ArrayList;
import java.util.List;

public class MainEditor extends Application {

    Labyrinthe l;
    String fileName;
    Case brushTile = new CaseMur();
    Monstre brushEntite = new Monstre(0,0);
    ImageView brushImage = new ImageView(brushTile.getSprite());
    CaseSwitch brushLink;
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
        Scene scene = new Scene(root/*,(this.l.getLongueur()+1)* VariablesGlobales.TAILLE_CASE+30,this.l.getHauteur()*VariablesGlobales.TAILLE_CASE+30*/);

        Tab launchOther = new Tab("launcher",launcher(stage));

        root.getTabs().addAll(this.tab(false),this.tab(true),this.linkTab(),launchOther);
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

    /**
     * GridPane gerant l'affichage de la map
     * @param tile affichage des tiles?
     * @param ennemy affichage des ennemis?
     * @return Grille du niveau
     */
    public GridPane grille(boolean tile, boolean ennemy, boolean link){
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
                    if (!ennemy) {
                        imageView.setOnMouseClicked(new CaseHandler(l, j, i, imageView));
                    }else{
                        imageView.setOnMouseClicked(new EntityHandler(l, j, i, imageView));
                    }
                    root.add(imageView,i,j);
                }
                //Affichage des ennemis
                if (ennemy){
                    for (int k=0;k<l.getMonstres().size();k++){
                        Monstre m = l.getMonstres().get(k);
                        if (m.getY()==i && m.getX()==j){
                            Image img = m.getSprite();
                            ImageView imageView = new ImageView(img);
                            imageView.setFitHeight(VariablesGlobales.TAILLE_CASE);
                            imageView.setFitWidth(VariablesGlobales.TAILLE_CASE);
                            root.add(imageView,i,j);
                        }
                    }
                }

                if (link){
                    if(l.getCase(j,i).isActivable()){
                        Circle circle = new Circle((double) VariablesGlobales.TAILLE_CASE /2, Color.RED);
                        root.add(circle,i,j);
                        circle.setOnMouseClicked(new AcivableHandler(l.getCase(j,i)));
                    }
                    if(l.getCase(j,i).isActivate()){
                        Circle circle = new Circle((double) VariablesGlobales.TAILLE_CASE /2, Color.BLUE);
                        root.add(circle,i,j);
                        circle.setOnMouseClicked(new SwitchHandler((CaseSwitch) l.getCase(j,i),circle));
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
    public Tab tab(boolean monstre){
        Tab root;
        if (monstre) {
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
            GridPane carte = grille(true,monstre,false);

            //Menu selection case
            ScrollPane menu = new ScrollPane(menu(monstre));



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
            root.setContent(grille(true, false, true));
        });

        return root;
    }

    /**
     * Menus de selection des brush
     * @param monstre si la brush concerne monstre
     * @return une liste de bouton brush
     */
    public VBox menu(boolean monstre){
        VBox menu = new VBox();


        //Bouton de save
        Button save = new Button("Save");
        save.setOnMouseClicked(mouseEvent -> save());
        menu.getChildren().add(save);

        //Liste des brush
        if (monstre) {
            List<Monstre> type = new ArrayList<>();
            for (int i = 0; i < Intelligence.values().length; i++) {
                type.add(new Monstre(0, 0, Intelligence.values()[i]));
            }
            for (Monstre m : type) {
                //Creation du bouton
                Button button = new Button();
                //Cration d'une imageview adaptee
                ImageView imageView = new ImageView(m.getSprite());
                imageView.setFitWidth(VariablesGlobales.TAILLE_CASE);
                imageView.setFitHeight(VariablesGlobales.TAILLE_CASE);
                button.setGraphic(imageView);
                //Ajout du bouton à la liste de boutons
                menu.getChildren().add(button);
                button.setOnMouseClicked(new MenuButtonHandler<>(m));
            }
        }else{
            List<Case> type = new ArrayList<>();
            type.add(new CaseMur());
            type.add(new CaseVide());
            type.add(new CaseEscalier(false));
            type.add(new CaseEscalier(true));
            type.add(new CaseSwitch());
            type.add(new CasePorte());

            for (Case aCase : type) {
                //Creation du bouton
                Button button = new Button();
                //Cration d'une imageview adaptee
                ImageView imageView = new ImageView(aCase.getSprite());
                imageView.setFitWidth(VariablesGlobales.TAILLE_CASE);
                imageView.setFitHeight(VariablesGlobales.TAILLE_CASE);
                button.setGraphic(imageView);
                //Ajout du bouton à la liste de boutons
                menu.getChildren().add(button);
                button.setOnMouseClicked(new MenuButtonHandler<>(aCase));
            }
        }

        return menu;
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
            if (laby.getCase(x, y) instanceof CaseEscalier stairs){
                if (stairs.getMonte()){
                    l.setPositionEscalierSortant(x,y);
                }else{
                    l.setPositionEscalierEntrant(x,y);
                }
            }
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
            Monstre m = (Monstre)brushEntite.clone(x,y);
            l.getMonstres().add(m);
            button.setImage(brushEntite.getSprite());

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
            brushLink.createLink(activable);
            curSel.setFill(Color.BLUE);
            brushLink = null;
        }
    }




}
