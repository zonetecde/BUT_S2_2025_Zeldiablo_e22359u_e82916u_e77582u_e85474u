package moteurJeu;

import javafx.scene.input.KeyEvent;

import java.io.Serializable;

public class Clavier implements Serializable,Cloneable {

    /**
     * controle appuyes
     */
    public boolean haut, bas, gauche, droite, interactionKey, tab, space, x;

    /**
     * stocke les commandes
     *
     * @param event evenement clavier
     */
    public void appuyerTouche(KeyEvent event) {

        switch (event.getCode()) {

            // si touche bas
            case S:
                this.bas = true;
                break;

            // si touche haut
            case Z:
                this.haut = true;
                break;

            // si touche gauche
            case Q:
                this.gauche = true;
                break;

            // si touche droite
            case D:
                this.droite = true;
                break;

            case E:
                this.interactionKey = true;
                break;

            case TAB:
                this.tab=true;
                break;

            case SPACE:
                this.space=true;
                break;

            case X:
                this.x = true;
                break;
        }

    }

    /**
     * stocke les commandes
     *
     * @param event evenement clavier
     */
    public void relacherTouche(KeyEvent event) {

        switch (event.getCode()) {

            // si touche bas
            case S:
                this.bas = false;
                break;

            // si touche haut
            case Z:
                this.haut = false;
                break;

            // si touche gauche
            case Q:
                this.gauche = false;
                break;

            // si touche droite
            case D:
                this.droite = false;
                break;

            case TAB:
                this.tab=false;
                break;

            case E:
                this.interactionKey = false;
                break;

            case SPACE:
                this.space=false;
                break;

            case X:
                this.x = false;
                break;
        }
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder("Touches pressées : ");
        if (haut) sb.append("haut ");
        if (bas) sb.append("bas ");
        if (gauche) sb.append("gauche ");
        if (droite) sb.append("droite ");
        if (interactionKey) sb.append("interaction ");
        if (tab) sb.append("tab ");
        if (space) sb.append("space ");
        if (x) sb.append("x ");
        return sb.toString();
    }

    @Override
    public Clavier clone(){
        try {
            return (Clavier)super.clone();
        } catch (CloneNotSupportedException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public boolean equals(Object clavier){
        if (clavier==null){return false;}
        Clavier c = (Clavier) clavier;
        boolean returne = true;
        returne = this.haut == c.haut;
        returne = returne & this.bas == c.bas;
        returne = returne & this.gauche == c.gauche;
        returne = returne & this.droite == c.droite;
        returne = returne & this.interactionKey == c.interactionKey;
        returne = returne & this.x == c.x;
        returne = returne & this.space == c.space;
        returne = returne & this.tab == c.tab;

        return returne;
    }

}
