package org.example;

import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.Tab;
import javafx.scene.control.TextField;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.VBox;

class Tabs {
    //TABS

    static Tab saveTab(MainEditor m){
        VBox root = new VBox(20);
        root.setAlignment(Pos.TOP_CENTER);
        Tab tab = new Tab("Save",root);

        Button button = new Button("Save");
        TextField textField = new TextField(m.fileName);
        textField.setAlignment(Pos.CENTER);
        root.getChildren().addAll(textField,button);

        button.setOnMouseClicked(mouseEvent -> {
            m.fileName= textField.getText();
            m.save();
        });

        return tab;

    }

    //TODO interface recette



    /**
     * Creation des tab de l'editeur
     * @return un Tab fonctionnel
     */
    static Tab tab(int concerne,MainEditor m){
        Tab root = switch (concerne){
            case 0 -> new Tab("Tiles");
            case 1 -> new Tab("Entities");
            case 2 -> new Tab("Link");
            case 3 -> new Tab("Stairs");
            case 4 -> new Tab("Items");
            default -> new Tab("Default");
        };

        root.setOnSelectionChanged(event -> {
            BorderPane main = new BorderPane();
            //Menu Droite
            VBox menuRight = new VBox(30);
            menuRight.setAlignment(Pos.BOTTOM_RIGHT);
            menuRight.getChildren().add(m.brushImage);
            main.setRight(menuRight);

            //TabTiles
            ScrollPane carte = new ScrollPane(Grille.grille(m.l,concerne,m));
            main.setCenter(carte);

            //Menu selection case
            if (concerne != 2 && concerne != 3) {
                ScrollPane menu = new ScrollPane(Menu.menu(m, concerne));
                main.setLeft(menu);
            }


            root.setContent(main);
        });

        return root;
    }
}
