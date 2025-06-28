package Zeldiablo.Cases;

import Zeldiablo.Entities.Entite;
import Zeldiablo.Items.Item;
import Zeldiablo.Main.ZeldiabloJeu;
import Zeldiablo.Sprite;
import Zeldiablo.Sprited;
import com.fasterxml.jackson.annotation.*;
import javafx.scene.image.Image;

import java.io.Serializable;

/**
 * Classe abstraite représentant les cases de jeu
 */

@JsonTypeInfo(
        use = JsonTypeInfo.Id.NAME,
        include = JsonTypeInfo.As.PROPERTY,
        property = "@type")
@JsonSubTypes({
        @JsonSubTypes.Type(value = CaseVide.class, name = "CaseVide"),
        @JsonSubTypes.Type(value = CaseMur.class, name = "CaseMur"),
        @JsonSubTypes.Type(value = CasePorte.class, name = "CasePorte"),
        @JsonSubTypes.Type(value = CaseSwitch.class, name = "CaseSwitch"),
        @JsonSubTypes.Type(value = CasePiege.class, name = "CasePiege"),
        @JsonSubTypes.Type(value = CasePancarte.class, name = "CasePancarte"),
        @JsonSubTypes.Type(value = CaseEscalier.class, name = "CaseEscalier"),
        @JsonSubTypes.Type(value = CaseSpawn.class, name = "CaseSpawn")
        // Ajoute ici tous les types spécifiques nécessaires
})
@JsonIdentityInfo(
        generator = ObjectIdGenerators.UUIDGenerator.class,
        property = "@id"
)
@JsonIgnoreProperties(ignoreUnknown = true)
public abstract class Case implements Serializable,Cloneable, Sprited {

    /**
    Permet de dire si on peut marcher sur cette case
     */
    private boolean isWalkable;

    private boolean isActivable = false;

    private boolean activate = false;

    @JsonProperty("sprite")
    private String sprite; // L'image de la case

    private final int id=0;// id eventuel

    /**
     * Constructeur basique de case
     * @param isWalkable peut on marcher dessus
     * @param img l'image reliée
     */
    public Case(@JsonProperty("isWalkable") boolean isWalkable,
                @JsonProperty("sprite") String img) {
        this.isWalkable = isWalkable;
        this.sprite = img;
    }

//Methodes a implanter//

    /**
     * Méthode appelée lorsqu'une entité marche sur la case
     * @param entite L'entité qui marche sur la case
     */
    public void onStepOn(Entite entite) {
        // Action par défaut
    }

    /**
     * Méthode appelée lorsqu'une entité interagit avec la case
     * @param entite L'entité qui interagit avec la case
     */
    public void onAction(Entite entite, ZeldiabloJeu zeldiabloJeu) {
        // Action par défaut
    }

    /**
     * Methode utilisée par les objets activables
     */
    public void activate(){
        //Action par défaut
    }


    /**
     * Méthode pour ajouter un item à la case
     * @param item L'item à ajouter
     */
    public void setItem(Item item) {
        // Action par défaut
    }

    public void updateId(){/*Action par defaut*/}

//Getters//

    /**
     * Getter
     * @return si la case est activable
     */
    public boolean isActivable(){return isActivable;}

    public boolean isActivate(){return activate;}

    /**
     * Getter utilisé pour savoir si une entité peut marcher sur la case
     * @return boolean
     */
    public boolean getIsWalkable(){return this.isWalkable;}

    /**
     * Retourne le sprite de la case
     * @return Sprite de la case
     */
    @JsonIgnore
    public Image getSprite() {return Sprite.getImg(sprite);}

    /**
     * Méthode pour obtenir l'item de la case
     * @return L'item de la case, ou null si aucun item n'est présent
     */
    public Item getItem() {/*Action par défaut*/return null;}


    /**
     * Vérifie si la case contient un objet
     * @return true si la case contient un objet, false sinon
     */
    public boolean hasItem() {
        // Action par défaut
        return false;
    }

    public int getId(){
        return id;
    }

    @JsonIgnore
    public boolean isLinked(){
        return false;
    }

    public Case clone(){
        Case o = null;
        try {
            // On récupère l'instance à renvoyer par l'appel de la
            // méthode super.clone()
            o = (Case)super.clone();
            o.updateId();
        } catch(CloneNotSupportedException cnse) {
            // Ne devrait jamais arriver, car nous implémentons
            // l'interface Cloneable
            cnse.printStackTrace(System.err);
        }
        // on renvoie le clone
        return o;
    }


//Setters//

    /**
     * Setter de isActivable
     * @param a is activable
     */
    public void setActivable(boolean a){this.isActivable=a;}

    /**
     * Setter de activate
     * @param a is activator
     */
    public void setActivate(boolean a){this.activate=a;}

    /**
     * Remplace le sprite
     * @param s nouvelle image
     */
    @JsonProperty("sprite")
    public void setSprite(String s){sprite=s;}

    @JsonProperty("sprite")
    public String getSpriteName(){return sprite;}

    /**
     * Méthode pour retirer un item de la case
     */
    public void removeItem() {/*Action par défaut*/}


    /**
     * Setter de si la case est marchable
     * @param isWalkable true si la case est marchable, false sinon
     */
    public void setIsWalkable(boolean isWalkable) {
        this.isWalkable = isWalkable;
    }

//TOSTRING

    /**
     * ToString format : Case{isWalkable=true}
     * @return String représentant la case
     */
    public String toString() {
        String[] out = this.getClass().toString().split("\\.");
        return out[out.length-1];
    }
}
