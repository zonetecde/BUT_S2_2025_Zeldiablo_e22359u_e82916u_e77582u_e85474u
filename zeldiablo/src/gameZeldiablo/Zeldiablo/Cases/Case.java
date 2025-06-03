package gameZeldiablo.Zeldiablo.Cases;

import gameZeldiablo.Zeldiablo.Entities.Entite;
import gameZeldiablo.Zeldiablo.Items.Item;
import gameZeldiablo.Zeldiablo.ZeldiabloJeu;
import javafx.scene.paint.Color;

/**
 * Classe abstraite représentant les cases de jeu
 */
public abstract class Case implements Action {
    /**
     * Emplacement x de la case
     */
    private int x;
    /**
     * Emplacement y de la case
     */
    private int y;
    /**
     * Couleur de la case
     */
    private Color couleur;

    /*
    Permet de dire si on peut marcher sur cette case
     */
    private boolean isWalkable;

    /**
     * Constructeur de Case
     * @param x
     * @param y
     * @param couleur
     */
    public Case(int x, int y, Color couleur, boolean isWalkable) {
        this.x = x;
        this.y = y;
        this.couleur = couleur;
        this.isWalkable = isWalkable;
    }

    /**
     * Getter de la variable couleur
     * @return couleur Couleur de la case
     */
    public Color getCouleur() {
        return couleur;
    }

    public boolean getIsWalkable(){
        return this.isWalkable;
    }

    /**
     * Setter de couleurs
     * @param c
     */
    public void setCouleur(Color c){
        couleur=c;
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
    public void ChangeLevel() {

    }
}
