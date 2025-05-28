package gameZeldiablo.Zeldiablo.Cases;

import javafx.scene.paint.Color;

/**
 * Classe abstraite représentant les cases de jeu
 */
public abstract class Case {
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

    /**
     * Constructeur de Case
     * @param x
     * @param y
     * @param couleur
     */
    public Case(int x, int y, Color couleur) {
        this.x = x;
        this.y = y;
        this.couleur = couleur;
    }

    /**
     * Getter de la variable couleur
     * @return couleur Couleur de la case
     */
    public Color getCouleur() {
        return couleur;
    }

    /**
     * Setter de couleurs
     * @param c
     */
    public void setCouleur(Color c){
        couleur=c;
    }

    //abstract public void steppedOn();
}
