package gameLaby.laby;

import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;
import moteurJeu.DessinJeu;
import moteurJeu.Jeu;

public class LabyDessin implements DessinJeu {
    private final static int CELL_SIZE = 50;

    @Override
    public void dessinerJeu(Jeu jeu, Canvas canvas) {
        Labyrinthe laby = ((LabyJeu) jeu).getLaby();

        // recupere un pinceau pour dessiner
        final GraphicsContext gc = canvas.getGraphicsContext2D();

        // dessin fond
        gc.setFill(Color.LIGHTGRAY);
        gc.fillRect(0, 0, canvas.getWidth(), canvas.getHeight());
         
        if(laby == null){
            System.out.println("Erreur dessinerJeu");
        }

        //affiche le labyrinthe charge
        for (int y = 0; y < laby.getLongueur(); y++) {
            // affiche la ligne
            for (int x = 0; x < laby.getHauteur(); x++) {
                if (laby.getMur(x, y)) {
                    // Couleur des murs - noir
                    gc.setFill(Color.BLACK);
                    gc.fillRect(x * CELL_SIZE, y * CELL_SIZE, CELL_SIZE, CELL_SIZE);
                } else {
                    // Couleur des chemins - blanc
                    gc.setFill(Color.WHITE);
                    gc.fillRect(x * CELL_SIZE, y * CELL_SIZE, CELL_SIZE, CELL_SIZE);
                }

                // affichage du joueur
                if (laby.joueur.x == x && laby.joueur.y == y) {
                    // Couleur du joueur - rouge (cercle)
                    gc.setFill(Color.RED);
                    gc.fillOval(x * CELL_SIZE, y * CELL_SIZE, CELL_SIZE, CELL_SIZE);
                }
                // affichage du monstre
                if (laby.monstre.x == x && laby.monstre.y == y) {
                    // Couleur du monstre - bleu (cercle)
                    gc.setFill(Color.PURPLE);
                    gc.fillOval(x * CELL_SIZE, y * CELL_SIZE, CELL_SIZE, CELL_SIZE);
                }
            }
        }
    }
}
