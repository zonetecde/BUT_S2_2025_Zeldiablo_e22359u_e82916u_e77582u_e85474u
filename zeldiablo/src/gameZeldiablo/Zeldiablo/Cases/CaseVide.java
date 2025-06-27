package gameZeldiablo.Zeldiablo.Cases;

import com.fasterxml.jackson.annotation.JsonProperty;
import gameZeldiablo.Zeldiablo.Items.Item;
import gameZeldiablo.Zeldiablo.Sprite;
import gameZeldiablo.Zeldiablo.VariablesGlobales;
import javafx.scene.image.Image;


public class CaseVide extends Case {
    private Item item;
    private boolean gotItem;

    /**
     * Constructeur
     */
    public CaseVide(){
        super(true, VariablesGlobales.SPRITE_CASE_VIDE);
        gotItem = false;
    }

    /**
     * Méthode pour ajouter un item à la case
     * @param item L'item à ajouter
     */
    @Override
    public void setItem(Item item) {
        this.item = item;
        if (item!=null) {
            gotItem = true;
        }else{
            gotItem = false;
        }
    }

    /**
     * Vérifie si la case contient un objet
     * @return true si la case contient un objet, false sinon
     */
    @Override
    public boolean hasItem() {
        return item != null;
    }

    /**
     * Méthode pour retirer un item de la case
     */
    @Override
    public void removeItem(){
        this.item = null;
    }


    /**
     * Retourne l'objet contenu dans la case
     * @return L'objet de la case
     */
    public Item getItem() {
        return item;
    }

    public void setGotItem(boolean b){this.gotItem=b;}

    @Override
    public Image getSprite() {
        if (!gotItem) {
            return super.getSprite();
        } else {
            return Sprite.getImg(VariablesGlobales.SPRITE_CASE_PIEDESTAL);
        }
    }
}


