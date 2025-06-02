package gameZeldiablo.Zeldiablo;

import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import moteurJeu.DessinJeu;
import moteurJeu.Jeu;

public class ZeldiabloDessin implements DessinJeu {

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
                    // Couleur du joueur - rouge (cercle)
                    gc.setFill(Color.RED);
                    gc.fillOval(x * VariablesGlobales.TAILLE_CASE, y * VariablesGlobales.TAILLE_CASE, VariablesGlobales.TAILLE_CASE, VariablesGlobales.TAILLE_CASE);
                }

                // Affichage des monstres
                for (MonstreStatique monstre : laby.getMonstres()) {
                    if (monstre.getX() == x && monstre.getY() == y) {
                        // Couleur des monstres - cercle rouge
                        gc.setFill(Color.BLUE);
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
        gc.drawImage(new Image("player/PlayerFaceDown.png"), baseXPlayer + 25, 0, 50, 75);
        gc.setFill(Color.GREY);
        gc.fillRect(baseXPlayer + 5, 75, 90, 20);
        gc.setFill(Color.RED);
        gc.fillRect(baseXPlayer + 5, 75, getLifeBarWidth(laby.getPlayer().getHp(), laby.getPlayer().getMaxHp()), 20);
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