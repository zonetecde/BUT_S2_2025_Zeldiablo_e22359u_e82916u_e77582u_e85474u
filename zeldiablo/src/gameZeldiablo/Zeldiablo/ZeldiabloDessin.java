package gameZeldiablo.Zeldiablo;

import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;
import javafx.scene.paint.Color;
import moteurJeu.DessinJeu;
import moteurJeu.Jeu;

public class ZeldiabloDessin implements DessinJeu {

    /**
     * Constructeur de la classe ZeldiabloDessin.
     * Il initialise les ressources nécessaires pour le dessin du jeu.
     */
    @Override
    public void dessinerJeu(Jeu jeu, Canvas canvas) {
        Labyrinthe laby = ((ZeldiabloJeu)jeu).getLaby();

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
                if (laby.joueur.getX() == x && laby.joueur.getY() == y) {
                    // Couleur du joueur - rouge (cercle)
                    gc.setFill(Color.RED);
                    gc.fillOval(x * VariablesGlobales.TAILLE_CASE, y * VariablesGlobales.TAILLE_CASE, VariablesGlobales.TAILLE_CASE, VariablesGlobales.TAILLE_CASE);
                }

            }
        }

        //Dessin de l'UI
        int baseXPlayer = laby.getLongueur() * VariablesGlobales.TAILLE_CASE;
        gc.drawImage(new Image("player/PlayerFaceDown.png"),baseXPlayer+25,0, 50, 75);
        gc.setFill(Color.GREY);
        gc.fillRect(baseXPlayer+5,75,90,20);
        gc.setFill(Color.RED);
        double tmp= ((double)laby.getPlayer().getHp()/(double)laby.getPlayer().getMaxHp())*90;
        gc.fillRect(baseXPlayer+5,75,tmp,20);
    }
}
