package Zeldiablo;


import javafx.scene.image.Image;

import java.net.URISyntaxException;
import java.nio.file.Files;
import java.nio.file.Path;
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
     * @throws URISyntaxException 
     */
    public static void setImg(String img){


        try {
            Path imagePath = Path.of("resource", "assets", img).toAbsolutePath().normalize();
            if (!Files.isRegularFile(imagePath)) {
                throw new IllegalArgumentException("Image file not found: " + imagePath);
            }

            String url = imagePath.toUri().toString();
            Image image = new Image(url);
            // Ajouter l'image au dict si elle a bien été créée
            imageCache.put(img, image);
        }
        catch(IllegalArgumentException e){
            System.out.println("Asset non trouvé : " + img);
        } 
        catch (Exception e) {
            // JavaFX n'a toujours pas été initialisé
            System.out.println(e.toString());
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
