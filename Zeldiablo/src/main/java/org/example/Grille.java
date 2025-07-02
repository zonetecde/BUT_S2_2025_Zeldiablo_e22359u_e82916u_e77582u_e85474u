package org.example;

import Zeldiablo.Cases.*;
import Zeldiablo.Entities.Entite;
import Zeldiablo.Labyrinthe;
import Zeldiablo.VariablesGlobales;
import javafx.event.EventHandler;
import javafx.geometry.HPos;
import javafx.geometry.Pos;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.GridPane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;


class Grille {
    static Editor m;
    static Labyrinthe l;
    /**
     * GridPane gerant l'affichage de la map
     * @return Grille du niveau
     */
    static GridPane grille(Labyrinthe laby, int concerne, Editor mE){
        m=mE;
        l=laby;
        int colnum = l.getLongueur();
        int linenum = l.getHauteur();
        GridPane root = new GridPane();
        root.setAlignment(Pos.CENTER);


        for (int i=0;i<colnum;i++){
            for (int j=0;j<linenum;j++){
                cases( j, i, root,concerne);
                switch (concerne) {
                    case 1 :
                        entites(j, i, root);
                        break;
                    case 2:
                        links(j, i, root);
                        break;
                    case 3:
                        stairs(j, i, root);
                        break;

                    case 4:
                        items(j, i, root);
                }
            }
        }

        return root;
    }

    private static void items(int j, int i, GridPane root) {
        Case cur = l.getCase(j, i);
        if(cur.hasItem()){
            ImageView imageView = new ImageView(cur.getItem().getSprite());
            imageView.setFitWidth(VariablesGlobales.TAILLE_CASE);
            imageView.setFitHeight(VariablesGlobales.TAILLE_CASE);
            root.add(imageView, i, j);
        }
    }

    private static void stairs(int j, int i, GridPane root) {
        if(l.getCase(j, i) instanceof CaseEscalier){
            Color c;
            if(l.getCase(j, i).isLinked()){c = Color.DARKVIOLET;}
            else{c = Color.VIOLET;}
            Circle circle = new Circle((double) VariablesGlobales.TAILLE_CASE /2, c);
            root.add(circle, i, j);
            circle.setOnMouseClicked(m.new Stairs_Handler((CaseEscalier) l.getCase(j, i)));
        }
    }

    private static void links(int j, int i, GridPane root) {
        if (l.getCase(j, i).isActivable()) {
            Circle circle = new Circle((double) VariablesGlobales.TAILLE_CASE / 2, Color.RED);
            root.add(circle, i, j);
            circle.setOnMouseClicked(m.new ActivableHandler(l.getCase(j, i)));

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
            circle.setOnMouseClicked(m.new SwitchHandler((CaseSwitch) l.getCase(j, i), circle));
        }
    }

    private static void entites( int j, int i, GridPane root) {
        for (int k = 0; k< l.getEntites().size(); k++){
            Entite m = l.getEntites().get(k);
            if (m.getY()== j && m.getX()== i){
                Image img = m.getSprite();
                ImageView imageView = new ImageView(img);
                imageView.setFitHeight(VariablesGlobales.TAILLE_CASE);
                imageView.setFitWidth(VariablesGlobales.TAILLE_CASE);
                root.add(imageView, i, j);
            }
        }
    }

    private static void cases(int j, int i, GridPane root,int concerne) {
        Image img = l.getCase(j, i).getSprite();
        ImageView imageView = new ImageView(img);
        imageView.setFitHeight(VariablesGlobales.TAILLE_CASE);
        imageView.setFitWidth(VariablesGlobales.TAILLE_CASE);

        //Set de l'action onClick des cases
        EventHandler<MouseEvent> tmp = switch (concerne){
            case 0 -> m.new CaseHandler(l, j, i, imageView);
            case 1 -> m.new EntityHandler(l, i, j, imageView);
            case 4 -> {
                if (l.getCase(j, i) instanceof CaseVide) {
                    yield m.new ItemHandler(l.getCase(j, i), imageView);
                }
                else{yield null;}
            }
            default -> null;
        };

        if (tmp !=null) {
            imageView.setOnMousePressed(tmp);
        }

        root.add(imageView, i, j);
    }
}
