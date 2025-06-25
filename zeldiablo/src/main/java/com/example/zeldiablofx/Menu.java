package main.java.com.example.zeldiablofx;

import gameZeldiablo.Zeldiablo.Cases.*;
import gameZeldiablo.Zeldiablo.Entities.Intelligence;
import gameZeldiablo.Zeldiablo.Entities.Monstre;
import gameZeldiablo.Zeldiablo.Items.*;
import gameZeldiablo.Zeldiablo.Sprite;
import gameZeldiablo.Zeldiablo.Sprited;
import gameZeldiablo.Zeldiablo.VariablesGlobales;
import javafx.event.EventHandler;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundFill;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;

import java.util.ArrayList;
import java.util.List;

public class Menu {

    /**
     * Menus de selection des brush
     * @return une liste de bouton brush
     */
    public static VBox menu(MainEditor m , int concerne){
        VBox menu = new VBox();
        List<Sprited[]> type;

        //Creation du bouton
        Button delbutton = new Button();
        //Creation d'une imageview adaptee
        ImageView delImageView = new ImageView(Sprite.getImg(VariablesGlobales.SPRITE_CROIX));
        delImageView.setFitWidth(VariablesGlobales.TAILLE_CASE);
        delImageView.setFitHeight(VariablesGlobales.TAILLE_CASE);
        delbutton.setGraphic(delImageView);


        //Liste des brush
        type = switch (concerne){
            case 4 -> items(m, delbutton, menu);
            case 1 -> entities(m, delbutton, menu);
            case 0 -> cases();
            default -> throw new IllegalStateException("Unexpected value: " + concerne);
        };

        for (Sprited[] s : type) {
            HBox closedHBox = new HBox();
            closedHBox.setAlignment(Pos.CENTER_LEFT);
            menu.getChildren().add(closedHBox);
            Button button = createButton(m,s[0]);
            if(s.length==1) {
                button.setOnMouseClicked(m.new MenuButtonHandler<>(s[0]));
            }else{
                button.setBackground(new Background(new BackgroundFill(Color.BLUE,null,null)));
                Button button2 = createButton(m,s[0]);
                button2.setBackground(new Background(new BackgroundFill(Color.GREEN ,null,null)));
                HBox openedHbox = new HBox();
                openedHbox.getChildren().add(button2);
                for (Sprited ss : s){
                    Button b2 = createButton(m,ss);
                    b2.setOnMouseClicked(m.new MenuButtonHandler<>(ss));
                    openedHbox.getChildren().add(b2);
                }
                openedHbox.setVisible(false);
                openedHbox.setManaged(false);
                menu.getChildren().add(openedHbox);

                for (Button b : new Button[] {button,button2}) {
                    b.setOnMouseClicked(mouseEvent -> {
                        openedHbox.setVisible(!openedHbox.isVisible());
                        openedHbox.setManaged(!openedHbox.isManaged());
                        closedHBox.setManaged(!closedHBox.isManaged());
                        closedHBox.setVisible(!closedHBox.isVisible());
                    }
                    );}

            }
            closedHBox.getChildren().add(button);
        }

        return menu;
    }


    private static Button createButton(MainEditor m, Sprited s) {
        //Creation d'une imageview adaptee
        ImageView imageView = new ImageView(s.getSprite());
        imageView.setFitWidth(VariablesGlobales.TAILLE_CASE);
        imageView.setFitHeight(VariablesGlobales.TAILLE_CASE);
        Button button = new Button();
        button.setGraphic(imageView);
        button.setOnMouseEntered(mouseEvent -> {button.setText(s.toString());});
        button.setOnMouseExited(mouseEvent -> {button.setText("");});
        button.setAccessibleText(s.toString());
        return button;
    }

    private static List<Sprited[]> cases() {
        List<Sprited[]> type;
        type = new ArrayList<>();
        type.add(new Sprited[]{new CaseMur()});
        type.add(new Sprited[]{new CaseVide()});
        type.add(new Sprited[]{new CaseEscalier(false)});
        type.add(new Sprited[]{new CaseEscalier(true)});
        type.add(new Sprited[]{new CaseSwitch()});
        type.add(new Sprited[]{new CasePorte()});
        type.add(new Sprited[]{new CasePancarte("Default")});
        type.add(new Sprited[]{new CasePiege(5)});
        type.add(new Sprited[]{new CaseSpawn()});
        return type;
    }

    private static List<Sprited[]> entities(MainEditor m, Button delbutton, VBox menu) {
        List<Sprited[]> type;
        type = new ArrayList<>();
        for (int i = 0; i < Intelligence.values().length; i++) {
            type.add(new Sprited[]{new Monstre(0, 0, Intelligence.values()[i],null)});
        }

        //Del Button
        delbutton.setOnMouseClicked(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent mouseEvent) {
                m.brushEntite = null;
            }
        });
        menu.getChildren().add(delbutton);
        return type;
    }

    private static List<Sprited[]> items(MainEditor m, Button delbutton, VBox menu) {
        List<Sprited[]> type;
        type = new ArrayList<>();
        type.add(new Sprited[] {new Amulette()});
        type.add(new Sprited[] {new Food()});
        type.add(new Sprited[] {new Epee(),new Hache(),new Baton()});
        type.add(new Sprited[] {new Bombe()});
        type.add(new Sprited[] {new Recette()});

        //Del button
        delbutton.setOnMouseClicked(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent mouseEvent) {
                m.brushItem = null;
            }
        });
        menu.getChildren().add(delbutton);
        return type;
    }
}
