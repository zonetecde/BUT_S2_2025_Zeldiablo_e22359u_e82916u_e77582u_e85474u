package gameZeldiablo.Zeldiablo.Items;

import gameZeldiablo.Zeldiablo.Entities.Player;
import gameZeldiablo.Zeldiablo.Sprite;
import gameZeldiablo.Zeldiablo.Sprited;
import javafx.scene.image.Image;
import java.io.Serializable;

public abstract class Item implements Serializable, Sprited {
    private final String name;
    private final TypeItem type;

    private final String sprite; // L'image de l'item

    /**
     * Constructeur de la classe Item
     * @param name Nom de l'item
     * @param img Chemin de l'image de l'item
     * @param type Type de l'item (objet ou arme)
     */
    public Item(String name, String img, TypeItem type) {
        this.name = name;
        this.sprite = img;
        this.type = type;
    }

    public boolean use(Player p){return false;}

    /**
     * Retourne le sprite de l'item
     * @return Sprite de l'item
     */
    public Image getSprite() {
        return Sprite.getImg(sprite);
    }

    public String getSpriteName(){return sprite;}

    public String getName() {
        return name;
    }

    public TypeItem getType() {
        return type;
    }

    public String toString() {
        return name;
    }

    /**
     * Getter pour les dégâts de l'item
     * @return Les dégâts de l'item
     */
    public double getDegat(){return -1;}
}
