package gameZeldiablo.Zeldiablo.Cases;

import gameZeldiablo.Zeldiablo.Entities.Entite;
import gameZeldiablo.Zeldiablo.Items.Item;
import gameZeldiablo.Zeldiablo.Sprite;
import gameZeldiablo.Zeldiablo.ZeldiabloJeu;
import javafx.scene.image.Image;

import java.io.Serializable;

/**
 * Classe abstraite représentant les cases de jeu
 */
public abstract class Case implements Serializable {

    /**
    Permet de dire si on peut marcher sur cette case
     */
    private boolean isWalkable;

    private boolean isActivable = false;

    private boolean activate = false;

    private String sprite; // L'image de la case

    /**
     * Constructeur basique de case
     * @param isWalkable peut on marcher dessus
     * @param img l'image reliée
     */
    public Case(boolean isWalkable, String img) {
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
    public void addItem(Item item) {
        // Action par défaut
    }

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
    public void setSprite(String s){sprite=s;}

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
        return "Case{" +
                ", isWalkable=" + isWalkable +
                '}';
    }
}
