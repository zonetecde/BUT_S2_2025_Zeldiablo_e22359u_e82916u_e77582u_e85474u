package gameZeldiablo.Zeldiablo;

import gameZeldiablo.Zeldiablo.Items.Item;
import gameZeldiablo.Zeldiablo.Entities.Entite;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Text;
import moteurJeu.DessinJeu;
import moteurJeu.Jeu;

import java.util.ArrayList;

public class ZeldiabloDessin implements DessinJeu {
    private Image imageJoueur;

    /**
     * Constructeur de la classe ZeldiabloDessin.
     * Il initialise les ressources nécessaires pour le dessin du jeu.
     */
    @Override
    public void dessinerJeu(Jeu jeu, Canvas canvas) {
        Labyrinthe laby = ((ZeldiabloJeu) jeu).getLaby();

        // recupere un pinceau pour dessiner
        final GraphicsContext gc = canvas.getGraphicsContext2D();

        // Dessine le laby
        labyUI(laby, gc, canvas);

        // Dessin les infos sur le joueur
        heroUI(laby, gc);

        if (VariablesGlobales.MenuOuvert){
            invUI(laby,gc,canvas);
        }
    }

    /*
     * Affiche le labyrinthe
     */
    private void labyUI(Labyrinthe laby, GraphicsContext gc, Canvas canvas) {
        // dessin fond
        gc.setFill(Color.LIGHTGRAY);
        gc.fillRect(0, 0, canvas.getWidth(), canvas.getHeight());

        if (laby == null) {
            System.out.println("Erreur dessinerJeu");
        }

        //affiche le labyrinthe charge
        for (int y = 0; y < laby.getHauteur(); y++) {
            // affiche la ligne
            for (int x = 0; x < laby.getLongueur(); x++) {
                // Couleur des murs - noir
                gc.setFill(laby.getCase(y, x).getCouleur());
                gc.fillRect(x * VariablesGlobales.TAILLE_CASE, y * VariablesGlobales.TAILLE_CASE, VariablesGlobales.TAILLE_CASE, VariablesGlobales.TAILLE_CASE);

                // affichage du joueur
                if (laby.joueurSurCase(y, x)){
                    // Couleur du joueur - bleu (cercle)
                    gc.setFill(Color.BLUE);
                    gc.fillOval(x * VariablesGlobales.TAILLE_CASE, y * VariablesGlobales.TAILLE_CASE, VariablesGlobales.TAILLE_CASE, VariablesGlobales.TAILLE_CASE);
                }

                // Affichage des monstres
                if (laby.monstreSurCase(y, x)) {
                    // Couleur des monstres - cercle rouge
                    gc.setFill(Color.RED);
                    gc.fillOval(x * VariablesGlobales.TAILLE_CASE, y * VariablesGlobales.TAILLE_CASE, VariablesGlobales.TAILLE_CASE, VariablesGlobales.TAILLE_CASE);
                }

                // Affichage des items
                if (laby.getCase(y, x).hasItem()) {
                    gc.setFill(Color.BLACK);
                    gc.fillOval(x * VariablesGlobales.TAILLE_CASE, y * VariablesGlobales.TAILLE_CASE, VariablesGlobales.TAILLE_CASE, VariablesGlobales.TAILLE_CASE);
                }
            }
        }
    }

    /*
     * Affiche l'interface utilisateur du joueur
     */
    private void heroUI(Labyrinthe laby, GraphicsContext gc) {
        if(imageJoueur == null){
            imageJoueur = new Image("player/PlayerFaceDown.png");
        }

        int baseXPlayer = laby.getLongueur() * VariablesGlobales.TAILLE_CASE;
        gc.drawImage(imageJoueur, baseXPlayer + 25, 0, 50, 75);
        gc.setFill(Color.GREY);
        gc.fillRect(baseXPlayer + 5, 75, 90, 20);
        gc.setFill(Color.RED);
        gc.fillRect(baseXPlayer + 5, 75, getLifeBarWidth(laby.getPlayer().getHp(), laby.getPlayer().getMaxHp()), 20);
    }

    /**
     * Affiche le menu
     * @param laby Laby actuel
     * @param gc Ou print
     * @param c Canvas utilisé
     */
    /*private void invUI(Labyrinthe laby, GraphicsContext gc, Canvas c){
        ArrayList<Item> inv =laby.getPlayer().getInventory();
        // dessin fond
        int x=0;
        int y=1;
        gc.strokeRect(c.getWidth()/4,c.getHeight()/4,c.getWidth()/2,c.getHeight()/2);
        int caseWidth= (int)(c.getWidth()/VariablesGlobales.COL_NUM_MENU);
        int caseHeight = (int)(c.getHeight()/6);
        boolean couleurcases=true;
        for (int i=0;i<inv.size();i++) {
            if (i==VariablesGlobales.curseur){
                gc.setFill(Color.rgb(255,0,0,0.8));
            }
            else if (couleurcases) {
                gc.setFill(Color.rgb(50,50,50,0.8));
            }
            else{
                gc.setFill(Color.rgb(80,80,80,0.8));
            }
            System.out.println(x);
            couleurcases=!couleurcases;
            gc.fillRect(c.getWidth()/4 + caseWidth * x, c.getHeight()/4 + caseHeight * y, caseWidth, caseHeight);
            gc.setFill(Color.BLACK);
            gc.fillText(inv.get(i).toString(), c.getWidth()/4 + caseWidth * x + (double) caseWidth / 3, c.getHeight()/4 + caseHeight * y + (double) caseHeight /2);

            x += 1;
            if (x > VariablesGlobales.COL_NUM_MENU-1) {
                x = 0;
                y += 1;
            }
        }
    }*/

    private void invUI(Labyrinthe laby, GraphicsContext gc, Canvas c){
        ArrayList<Item> inv = laby.getPlayer().getInventory();

        // Paramètres de base
        double menuX = c.getWidth() / 4;
        double menuY = c.getHeight() / 4;
        double menuWidth = c.getWidth() / 2;
        double menuHeight = c.getHeight() / 2;
        int caseWidth = (int)(menuWidth / VariablesGlobales.COL_NUM_MENU);
        int caseHeight = (int)(menuHeight / 6);

        // Dessin du fond du menu
        gc.setFill(Color.rgb(20, 20, 20, 0.85)); // Fond sombre semi-transparent
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
            if (i == VariablesGlobales.curseur) {
                gc.setFill(Color.rgb(255, 80, 80, 0.9)); // Case sélectionnée
            } else if (couleurcases) {
                gc.setFill(Color.rgb(60, 60, 60, 0.9));
            } else {
                gc.setFill(Color.rgb(90, 90, 90, 0.9));
            }
            couleurcases = !couleurcases;

            // Dessin de la case
            gc.fillRoundRect(caseX, caseY, caseWidth - 5, caseHeight - 5, 10, 10);

            // Texte de l'item centré
            gc.setFill(Color.WHITE);
            gc.setFont(Font.font("Arial", FontWeight.BOLD, 14));
            String itemText = inv.get(i).toString();
            Text text = new Text(itemText);
            text.setFont(gc.getFont());
            double textWidth = text.getLayoutBounds().getWidth();
            double textX = caseX + (caseWidth - textWidth) / 2;
            double textY = caseY + caseHeight / 2 + 5;

            gc.fillText(itemText, textX, textY);

            x += 1;
            if (x >= VariablesGlobales.COL_NUM_MENU) {
                x = 0;
                y += 1;
            }
        }
    }

    /*
     * Calcul la largeur de la barre de vie du joueur
     * @param actualHp les points de vie actuels du joueur
     * @param maxHp les points de vie maximum du joueur
     */
    private double getLifeBarWidth(double actualHp, double maxHp) {
        return ((double) actualHp / (double) maxHp) * 90;
    }
}