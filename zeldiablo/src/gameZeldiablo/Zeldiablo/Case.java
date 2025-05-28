package gameZeldiablo.Zeldiablo;

import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;

public abstract class Case {
    private int x;
    private int y;

    private Color couleur;

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

    public void setCouleur(Color c){
        couleur=c;
    }

    //abstract public void steppedOn();
}
