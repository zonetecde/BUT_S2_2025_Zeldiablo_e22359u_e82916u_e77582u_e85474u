package gameZeldiablo.Zeldiablo.Items;

import gameZeldiablo.Zeldiablo.Labyrinthe;
import gameZeldiablo.Zeldiablo.Sprite;

public abstract class Item {
    private final String name;
    private final TypeItem type;

    private final Object[] params; // En fonction des items, on peut avoir besoin de paramètres supplémentaires (arme = dégâts, etc.)

    private Sprite sprite; // L'image de l'item

    /**
     * Constructeur de la classe Item
     * @param name Nom de l'item
     * @param img Chemin de l'image de l'item
     * @param type Type de l'item (objet ou arme)
     */
    public Item(String name, String img, TypeItem type, Object... params) {
        this.name = name;
        this.sprite = new Sprite(img);
        this.type = type;
        this.params = params;
    }

    public boolean use(Labyrinthe laby){return false;}

    /**
     * Retourne le sprite de l'item
     * @return Sprite de l'item
     */
    public Sprite getSprite() {
        return this.sprite;
    }

    public String getName() {
        return name;
    }

    public TypeItem getType() {
        return type;
    }

    public String toString() {
        if(type == TypeItem.ARME){
            return this.name + " (" + ((double)this.getParams()[0]) + " pdg)";
        } else return name;
    }

    public Object[] getParams() {
        return params;
    }
}
