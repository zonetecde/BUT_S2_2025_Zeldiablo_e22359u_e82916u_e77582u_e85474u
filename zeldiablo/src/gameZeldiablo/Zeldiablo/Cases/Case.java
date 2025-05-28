package gameZeldiablo.Zeldiablo.Cases;

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

    public void onStepOn() {
        // Action par défaut
    }
}
