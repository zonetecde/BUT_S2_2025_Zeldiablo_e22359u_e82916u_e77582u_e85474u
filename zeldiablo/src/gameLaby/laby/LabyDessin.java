package gameLaby.laby;

import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;
import moteurJeu.DessinJeu;
import moteurJeu.Jeu;

public class LabyDessin implements DessinJeu {
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
        for (int y = 0; y < laby.getHauteur(); y++) {
            // affiche la ligne
            for (int x = 0; x < laby.getLongueur(); x++) {
                // Couleur des murs - noir
                gc.setFill(laby.getCase(y,x).getCouleur());
                gc.fillRect(x * VariablesGlobales.TAILLE_CASE, y * VariablesGlobales.TAILLE_CASE, VariablesGlobales.TAILLE_CASE, VariablesGlobales.TAILLE_CASE);

                // affichage du joueur
                if (laby.joueur.x == x && laby.joueur.y == y) {
                    // Couleur du joueur - rouge (cercle)
                    gc.setFill(Color.RED);
                    gc.fillOval(x * VariablesGlobales.TAILLE_CASE, y * VariablesGlobales.TAILLE_CASE, VariablesGlobales.TAILLE_CASE, VariablesGlobales.TAILLE_CASE);
                }

            }
        }
    }
}
