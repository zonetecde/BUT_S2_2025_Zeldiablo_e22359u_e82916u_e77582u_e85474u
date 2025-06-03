package gameZeldiablo.Zeldiablo;


import javafx.application.Platform;
import javafx.scene.image.Image;

public abstract class Sprite extends ZeldiabloDessin {
    private String imgFile = null;
    private Image img;

    public Sprite(String s){
        this.imgFile = s;
    }

    public void setImg(String img){
        this.imgFile=img;
        try{
            this.img = new Image(imgFile);
        } catch (Exception e){
            // JavaFX n'a toujours pas été initialisé
        }
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
