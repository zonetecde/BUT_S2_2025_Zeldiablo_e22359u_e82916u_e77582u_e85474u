package Zeldiablo.Main;

import Zeldiablo.Entities.Player;
import Zeldiablo.Inventaire;
import Zeldiablo.Items.ItemsList;
import Zeldiablo.VariablesGlobales;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Text;
import moteurJeu.Clavier;

class Inv {


    /**
     * Affiche l'interface utilisateur de l'inventaire du joueur.
     * @param joueur actuel
     * @param gc Le contexte graphique pour dessiner sur le canvas.
     */
    static void invUI(Player joueur, GraphicsContext gc){
        Inventaire inv = joueur.getInventory();
        Canvas c = gc.getCanvas();
        // Paramètres de base
        double menuX = c.getWidth() / 3;
        double menuY = c.getHeight() / 4;
        double craftX = c.getWidth() / 30;
        double craftWidth = c.getWidth()/4;
        double menuWidth = c.getWidth() / 2;
        double menuHeight = c.getHeight() / 2;
        int caseWidth = (int)(menuWidth / VariablesGlobales.COL_NUM_MENU);
        int caseHeight = (int)(menuHeight / 6);

        Menu(gc, menuX, menuY, menuWidth, menuHeight, inv, caseWidth, caseHeight,true);
        Menu(gc,craftX,menuY,craftWidth,menuHeight,inv,(int)craftWidth,caseHeight,false);

    }

    private static void Menu(GraphicsContext gc, double menuX, double menuY, double menuWidth, double menuHeight, Inventaire inv, int caseWidth, int caseHeight, boolean items) {
        // Dessin du fond du menu
        gc.setFill(Color.rgb(14, 33, 72,0.85)); // Fond sombre semi-transparent
        gc.fillRoundRect(menuX - 10, menuY - 10, menuWidth + 20, menuHeight + 20, 20, 20); // Ajoute un fond arrondi

        gc.setStroke(Color.LIGHTGRAY);
        gc.setLineWidth(3);
        gc.strokeRoundRect(menuX - 10, menuY - 10, menuWidth + 20, menuHeight + 20, 20, 20); // Bordure claire

        // Dessin des cases d'inventaire
        int x = 0;
        int y = 0;
        boolean couleurcases = true;


        int len;
        if (items) {
            len = inv.size();
        }
        else{
            len = inv.getCraft().size();
        }

        for (int i = 0; i < len ; i++) {
            double caseX = menuX + caseWidth * x;
            double caseY = menuY + caseHeight * y;

            // Couleur de fond de case
            if (i == inv.getCurseurInt() && (items == inv.getCurrentCurseur()) ) {
                gc.setFill(Color.rgb(227, 208, 149,0.9)); // Case sélectionnée
            } else if (couleurcases) {
                gc.setFill(Color.rgb(72, 58, 160,0.9));
            } else {
                gc.setFill(Color.rgb(121, 101, 193,0.9));
            }
            couleurcases = !couleurcases;

            // Dessin de la case
            gc.fillRoundRect(caseX, caseY, caseWidth - 5, caseHeight - 5, 10, 10);

            // Texte de l'item centré
            String itemText;
            if (items){
                ItemsList item = inv.getListe().get(i);
                itemText = item.toString() + "x" + inv.getNumber(item);
                gc.drawImage(inv.get(item).getFirst().getSprite(),caseX+10,caseY,24,24);
            }else{
                itemText = inv.getCraft().get(i).toString();
            }

            gc.setFill(Color.WHITE);
            gc.setFont(Font.font("Arial", FontWeight.BOLD, 14));

            Text text = new Text(itemText);
            text.setFont(gc.getFont());
            double textWidth = text.getLayoutBounds().getWidth();
            double textX = caseX + (caseWidth - textWidth) / 2;
            double textY = caseY + (double) caseHeight / 2 + 5;

            gc.fillText(itemText, textX, textY);


            x += 1;
            int nbCol;
            if (items){nbCol = VariablesGlobales.COL_NUM_MENU;}
            else{nbCol=1;};
            if (x >= nbCol) {
                x = 0;
                y += 1;
            }
        }
    }

    static void inputInv(Clavier clavier,Player joueur){
        Inventaire inv = joueur.getInventory();

        int nbCol;
        if (inv.getCurrentCurseur()){nbCol = VariablesGlobales.COL_NUM_MENU;}
        else{nbCol=1;};

        if (clavier.droite){
            inv.moveCurseur(+1);
        } else if (clavier.gauche){
            inv.moveCurseur(-1);
        } else if (clavier.haut){
            inv.moveCurseur(-nbCol);
        } else if (clavier.bas){
            inv.moveCurseur(nbCol);
        } else if (clavier.space) {
            if (inv.getCurrentCurseur()) {
                inv.use(inv.getCurseur());
            }else{
                inv.getCraft().get(inv.getCurseurInt()).menuUse(joueur);
            }
        }
    }
}
