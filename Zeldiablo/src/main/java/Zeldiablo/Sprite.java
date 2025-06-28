package Zeldiablo;

import javafx.scene.image.Image;

import java.util.HashMap;

/**
 * Classe abstraite pour les objets ayant un affichage
 */
public class Sprite {
    // Dictionnaire pour éviter de créer plusieurs fois la même image
    private static final HashMap<String, Image> imageCache = new HashMap<>();


    /**
     * Creation de l'image
     * @param img nom
     */
    public static void setImg(String img){
        // Vérifier si l'image est déjà dans le dict
        if (!imageCache.containsKey(img)) {
            try {
                Image image = new Image(img);
                // Ajouter l'image au dict si elle a bien été créée
                imageCache.put(img, image);
            } catch (Exception e) {
                // JavaFX n'a toujours pas été initialisé
            }
        }
    }

    /**
     * getter de l'image
     * @return image actuelle
     */
    public static Image getImg(String img){
            // Vérifier si l'image est déjà en cache
            if (!imageCache.containsKey(img)) {
                setImg(img);
            }

        return imageCache.get(img);
    }

}
