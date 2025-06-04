package gameZeldiablo.Zeldiablo.Items;

import gameZeldiablo.Zeldiablo.Labyrinthe;
import gameZeldiablo.Zeldiablo.Sprite;

public abstract class Item extends Sprite {
    private String name;
    private TypeItem type;

    private Object[] params; // En fonction des items, on peut avoir besoin de paramètres supplémentaires (arme = dégâts, etc.)

    /**
     * Constructeur de la classe Item
     * @param name Nom de l'item
     * @param img Chemin de l'image de l'item
     * @param type Type de l'item (objet ou arme)
     */
    public Item(String name, String img, TypeItem type, Object... params) {
        super(img);
        this.name = name;
        this.type = type;
        this.params = params;
    }

    public boolean use(Labyrinthe laby){return false;};

    public String getName() {
        return name;
    }

    public TypeItem getType() {
        return type;
    }

    public String toString() {
        return name;
    }

    public Object[] getParams() {
        return params;
    }
}
