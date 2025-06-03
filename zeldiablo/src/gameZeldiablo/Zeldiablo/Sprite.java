package gameZeldiablo.Zeldiablo;


import javafx.application.Platform;
import javafx.scene.image.Image;

public abstract class Sprite {
    private String imgFile = null;
    private Image img;

    public Sprite(String s){
        this.imgFile = s;
    }

    public void setImg(Image img){
        this.img = img;
    }

    public Image getImg(){
        if(this.img == null){
            try{
                this.img = new Image(imgFile);
            } catch (Exception e){
                // JavaFX n'a toujours pas été initialisé
            }
        }

        return this.img;
    }

}
