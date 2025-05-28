package gameLaby.laby;

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

    public Rectangle display(){
        // retourne un rectangle représentant la case
        Rectangle rect = new Rectangle(
                x * VariablesGlobales.TAILLE_CASE,
                y * VariablesGlobales.TAILLE_CASE,
                VariablesGlobales.TAILLE_CASE,
                VariablesGlobales.TAILLE_CASE
        );

        return rect;
    }
}
