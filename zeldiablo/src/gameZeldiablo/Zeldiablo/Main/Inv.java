package gameZeldiablo.Zeldiablo.Main;

import gameZeldiablo.Zeldiablo.Entities.Player;
import gameZeldiablo.Zeldiablo.Inventaire;
import gameZeldiablo.Zeldiablo.Items.ItemsList;
import gameZeldiablo.Zeldiablo.VariablesGlobales;
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
        double menuX = c.getWidth() / 4;
        double menuY = c.getHeight() / 4;
        double menuWidth = c.getWidth() / 2;
        double menuHeight = c.getHeight() / 2;
        int caseWidth = (int)(menuWidth / VariablesGlobales.COL_NUM_MENU);
        int caseHeight = (int)(menuHeight / 6);

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

        for (int i = 0; i < inv.size(); i++) {
            double caseX = menuX + caseWidth * x;
            double caseY = menuY + caseHeight * y;

            // Couleur de fond de case
            if (i == inv.getCurseurInt()) {
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
            ItemsList item = inv.getListe().get(i);
            gc.setFill(Color.WHITE);
            gc.setFont(Font.font("Arial", FontWeight.BOLD, 14));
            String itemText = item.toString() + "x" + inv.getNumber(item);
            Text text = new Text(itemText);
            text.setFont(gc.getFont());
            double textWidth = text.getLayoutBounds().getWidth();
            double textX = caseX + (caseWidth - textWidth) / 2;
            double textY = caseY + (double) caseHeight / 2 + 5;

            gc.fillText(itemText, textX, textY);
            gc.drawImage(inv.get(item).getFirst().getSprite(),caseX+10,caseY,24,24);

            x += 1;
            if (x >= VariablesGlobales.COL_NUM_MENU) {
                x = 0;
                y += 1;
            }
        }
    }

    static void inputInv(Clavier clavier,Player joueur){
        Inventaire inv = joueur.getInventory();

        if (clavier.droite){
            inv.movecurseur(+1);
        } else if (clavier.gauche){
            inv.movecurseur(-1);
        } else if (clavier.haut){
            inv.movecurseur(-VariablesGlobales.COL_NUM_MENU);
        } else if (clavier.bas){
            inv.movecurseur(-VariablesGlobales.COL_NUM_MENU);
        } else if (clavier.space) {
            inv.use(inv.getCurseur());
        }
    }
}
