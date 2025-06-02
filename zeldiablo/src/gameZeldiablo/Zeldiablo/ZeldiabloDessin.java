package gameZeldiablo.Zeldiablo;

import gameZeldiablo.Zeldiablo.Items.Item;
import gameZeldiablo.Zeldiablo.Entities.Entite;
import gameZeldiablo.Zeldiablo.Entities.MonstreStatique;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import moteurJeu.DessinJeu;
import moteurJeu.Jeu;

import java.util.ArrayList;

public class ZeldiabloDessin implements DessinJeu {
    private Image imageJoueur = new Image("player/PlayerFaceDown.png");

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
            menuUI(laby,gc,canvas);
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
                if (laby.getPlayer().getX() == x && laby.getPlayer().getY() == y) {
                    // Couleur du joueur - bleu (cercle)
                    gc.setFill(Color.BLUE);
                    gc.fillOval(x * VariablesGlobales.TAILLE_CASE, y * VariablesGlobales.TAILLE_CASE, VariablesGlobales.TAILLE_CASE, VariablesGlobales.TAILLE_CASE);
                }

                // Affichage des monstres
                for (Entite monstre : laby.getMonstres()) {
                    if (monstre.getX() == x && monstre.getY() == y) {
                        // Couleur des monstres - cercle rouge
                        gc.setFill(Color.RED);
                        gc.fillOval(x * VariablesGlobales.TAILLE_CASE, y * VariablesGlobales.TAILLE_CASE, VariablesGlobales.TAILLE_CASE, VariablesGlobales.TAILLE_CASE);
                    }
                }

            }
        }
    }

    /*
     * Affiche l'interface utilisateur du joueur
     */
    private void heroUI(Labyrinthe laby, GraphicsContext gc) {
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
    private void menuUI(Labyrinthe laby, GraphicsContext gc, Canvas c){
        ArrayList<Item> inv =laby.getPlayer().getInventory();
        // dessin fond
        int x=0;
        int y=1;
        int caseWidth= (int)c.getWidth()/VariablesGlobales.COL_NUM_MENU;
        int caseHeight = (int)(c.getHeight()/6);
        gc.setFill(Color.WHITE);
        gc.fillRect(0,0,c.getWidth(),caseHeight);
        boolean couleurcases=true;
        for (int i=0;i<inv.size();i++) {
            if (i==VariablesGlobales.curseur){
                gc.setFill(Color.RED);
            }
            else if (couleurcases) {
                gc.setFill(Color.LIGHTGRAY);
            }
            else{
                gc.setFill(Color.GREY);
            }

            couleurcases=!couleurcases;
            gc.fillRect(caseWidth * x, caseHeight * y, caseWidth, caseHeight);
            gc.setFill(Color.BLACK);
            gc.fillText(inv.get(i).toString(), caseWidth * x + (double) caseWidth / 3, caseHeight * y + (double) caseHeight /2);

            x += 1;
            if (x > VariablesGlobales.COL_NUM_MENU-1) {
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