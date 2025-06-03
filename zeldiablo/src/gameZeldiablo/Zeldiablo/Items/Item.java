package gameZeldiablo.Zeldiablo.Items;

import gameZeldiablo.Zeldiablo.Labyrinthe;
import gameZeldiablo.Zeldiablo.Sprite;

public abstract class Item extends Sprite {
    private String name;
    private TypeItem type;

    /**
     * Constructeur de la classe Item
     * @param name Nom de l'item
     * @param img Chemin de l'image de l'item
     * @param type Type de l'item (objet ou arme)
     */
    public Item(String name, String img, TypeItem type) {
        super(img);
        this.name = name;
        this.type = type;
    }

    public void use(Labyrinthe laby){};

    public String getName() {
        return name;
    }

    public TypeItem getType() {
        return type;
    }

    public String toString() {
        return name;
    }
}
