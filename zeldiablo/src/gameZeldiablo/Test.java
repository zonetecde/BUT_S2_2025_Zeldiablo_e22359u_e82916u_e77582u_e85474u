package gameZeldiablo;

import javafx.application.Application;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.stage.Stage;

public class Test extends Application {

    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage primaryStage) {
        ZeldiabloDessin test=new ZeldiabloDessin();
        Canvas c=new Canvas(500,500);
        Group g=new Group(c);
        Scene sc=new Scene(g,500,500);
        test.dessinerJeu(new Zeldiablo(),c);
        primaryStage.setScene(sc);
        primaryStage.show();
    }
}
