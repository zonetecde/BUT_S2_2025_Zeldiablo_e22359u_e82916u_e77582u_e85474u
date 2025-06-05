package gameZeldiablo.Zeldiablo;

import javafx.scene.image.Image;

import java.io.Serializable;
import java.util.HashMap;

/**
 * Classe abstraite pour les objets ayant un affichage
 */
public class Sprite implements Serializable {
    // Dictionnaire pour éviter de créer plusieurs fois la même image
    private static HashMap<String, Image> imageCache = new HashMap<>();
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
        this.imgFile = img;
        // Vérifier si l'image est déjà dans le dict
        if (imageCache.containsKey(img)) {
            this.img = imageCache.get(img);
        } else {
            try {
                this.img = new Image(imgFile);
                // Ajouter l'image au dict si elle a bien été créée
                if (this.img != null) {
                    imageCache.put(img, this.img);
                }
            } catch (Exception e) {
                // JavaFX n'a toujours pas été initialisé
            }
        }
    }

    /**
     * getter de l'image
     * @return image actuelle
     */
    public Image getImg(){
        if(this.img == null){
            // Vérifier si l'image est déjà en cache
            if (imageCache.containsKey(this.imgFile)) {
                this.img = imageCache.get(this.imgFile);
            } else {
                this.setImg(imgFile);
                
            }
        }
        return this.img;
    }

    public String getImgFileName(){
        return this.imgFile;
    }

    /**
     * Nettoie le cache d'images (utile pour libérer la mémoire)
     */
    public static void clearImageCache() {
        imageCache.clear();
    }

    /**
     * Retourne la taille du cache d'images
     * @return le nombre d'images en cache
     */
    public static int getCacheSize() {
        return imageCache.size();
    }
}
