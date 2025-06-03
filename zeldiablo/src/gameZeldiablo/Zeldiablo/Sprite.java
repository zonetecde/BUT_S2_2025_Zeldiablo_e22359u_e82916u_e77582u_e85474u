package gameZeldiablo.Zeldiablo;


import javafx.application.Platform;
import javafx.scene.image.Image;

public abstract class Sprite {
    private Image img;

    public Sprite(String s){
        Platform.runLater(()-> {this.img= new Image(s); });

    }

    public void setImg(Image img){
        this.img = img;
    }

    public Image getImg(){
        return this.img;
    }

}
