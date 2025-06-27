package gameZeldiablo.Zeldiablo.Items;

import com.fasterxml.jackson.annotation.*;
import gameZeldiablo.Zeldiablo.Entities.Player;
import gameZeldiablo.Zeldiablo.Sprite;
import gameZeldiablo.Zeldiablo.Sprited;
import javafx.scene.image.Image;
import java.io.Serializable;

@JsonTypeInfo(
        use = JsonTypeInfo.Id.NAME,
        include = JsonTypeInfo.As.PROPERTY,
        property = "@type"
)
@JsonSubTypes({
        @JsonSubTypes.Type(value = Amulette.class, name = "Amulette"),
        @JsonSubTypes.Type(value = gameZeldiablo.Zeldiablo.Items.Baton.class, name = "Baton"),
        @JsonSubTypes.Type(value = gameZeldiablo.Zeldiablo.Items.Armors.ChestPlate.class, name = "ChestPlate"),
        @JsonSubTypes.Type(value = gameZeldiablo.Zeldiablo.Items.Bombe.class, name = "Bombe"),
        @JsonSubTypes.Type(value = gameZeldiablo.Zeldiablo.Items.Epee.class, name = "Epee"),
        @JsonSubTypes.Type(value = gameZeldiablo.Zeldiablo.Items.Food.class, name = "Food"),
        @JsonSubTypes.Type(value = gameZeldiablo.Zeldiablo.Items.Hache.class, name = "Hache"),
        @JsonSubTypes.Type(value = gameZeldiablo.Zeldiablo.Items.Recette.class, name = "Recette")

        // Ajoute ici les autres types d'items spécifiques si tu en as
})

public abstract class Item implements Serializable, Sprited {
    private final ItemsList name;
    private final TypeItem type;
    private final String sprite; // L'image de l'item

    /**
     * Constructeur de la classe Item
     * @param name Nom de l'item
     * @param img Chemin de l'image de l'item
     * @param type Type de l'item (objet ou arme)
     */
    public Item(ItemsList name, String img, TypeItem type) {
        this.name = name;
        this.sprite = img;
        this.type = type;
    }

    public boolean use(Player p){return false;}

    /**
     * Retourne le sprite de l'item
     * @return Sprite de l'item
     */
    @Override
    public Image getSprite() {
        return Sprite.getImg(sprite);
    }

    @JsonIgnore
    public String getSpriteName(){return sprite;}

    public ItemsList getName() {
        return name;
    }

    public TypeItem getType() {
        return type;
    }

    public String toString() {
        return this.getName().toString().toLowerCase();
    }

    /**
     * Getter pour les dégâts de l'item
     * @return Les dégâts de l'item
     */
    @JsonIgnore
    public double getDegat(){return -1;}
}
