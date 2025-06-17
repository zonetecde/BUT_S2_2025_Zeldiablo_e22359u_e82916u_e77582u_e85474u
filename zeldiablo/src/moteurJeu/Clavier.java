package moteurJeu;

import javafx.scene.input.KeyEvent;

import java.io.Serializable;

public class Clavier implements Serializable {

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
}
