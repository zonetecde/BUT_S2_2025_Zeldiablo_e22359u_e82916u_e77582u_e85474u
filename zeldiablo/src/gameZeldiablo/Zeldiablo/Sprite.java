package gameZeldiablo.Zeldiablo;

import javafx.scene.image.Image;

/**
 * Classe abstraite pour les objets ayant un affichage
 */
public abstract class Sprite extends ZeldiabloDessin {
    private String imgFile;
    private Image img;

    /**
     * Constructeur
     * @param s chemin vers l'image
     */
    public Sprite(String s){
        this.imgFile = s;
    }

    /**
     * Creation de l'image
     * @param img nom
     */
    public void setImg(String img){
        this.imgFile=img;
        try{
            this.img = new Image(imgFile);
        } catch (Exception e){
            // JavaFX n'a toujours pas été initialisé
        }
    }

    /**
     * getter de l'image
     * @return image actuelle
     */
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

    public String getImgFileName(){
        return this.imgFile;
    }
}
