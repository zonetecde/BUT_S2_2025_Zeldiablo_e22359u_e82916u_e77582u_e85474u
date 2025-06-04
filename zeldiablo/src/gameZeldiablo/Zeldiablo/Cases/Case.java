package gameZeldiablo.Zeldiablo.Cases;

import gameZeldiablo.Zeldiablo.Entities.Entite;
import gameZeldiablo.Zeldiablo.Items.Item;
import gameZeldiablo.Zeldiablo.Sprite;
import gameZeldiablo.Zeldiablo.ZeldiabloJeu;

/**
 * Classe abstraite représentant les cases de jeu
 */
public abstract class Case extends Sprite implements Action{
    /**
     * Emplacement x de la case
     */
    private final int x;
    /**
     * Emplacement y de la case
     */
    private final int y;

    /**
    Permet de dire si on peut marcher sur cette case
     */
    private boolean isWalkable;

    /**
     * Constructeur de Case
     * @param x pos x
     * @param y pos y
     */
    public Case(int x, int y, boolean isWalkable, String img) {
        super(img);
        this.x = x;
        this.y = y;
        this.isWalkable = isWalkable;
    }

    /**
     * Getter utilisé pour savoir si une entité peut marcher sur la case
     * @return boolean
     */
    public boolean getIsWalkable(){
        return this.isWalkable;
    }

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
     * Méthode pour ajouter un item à la case
     * @param item L'item à ajouter
     */
    public void addItem(Item item) {
        // Action par défaut
    }

    /**
     * Vérifie si la case contient un objet
     * @return true si la case contient un objet, false sinon
     */
    public boolean hasItem() {
        // Action par défaut
        return false;
    }

    /**
     * Méthode pour retirer un item de la case
     */
    public void removeItem() {
        // Action par défaut
    }

    /**
     * Méthode pour obtenir l'item de la case
     * @return L'item de la case, ou null si aucun item n'est présent
     */
    public Item getItem() {
        // Action par défaut
        return null;
    }
   
    /**
     * Setter de si la case est marchable
     * @param isWalkable true si la case est marchable, false sinon
     */
    public void setIsWalkable(boolean isWalkable) {
        this.isWalkable = isWalkable;
    }

    /**
     * ToString format : Case{x=0, y=0, isWalkable=true}
     * @return String représentant la case
     */
    public String toString() {
        return "Case{" +
                "x=" + x +
                ", y=" + y +
                ", isWalkable=" + isWalkable +
                '}';
    }
}
