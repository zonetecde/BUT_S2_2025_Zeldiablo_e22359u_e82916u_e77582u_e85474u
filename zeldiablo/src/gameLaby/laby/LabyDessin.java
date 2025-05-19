package gameLaby.laby;

import javafx.scene.canvas.Canvas;
import moteurJeu.DessinJeu;
import moteurJeu.Jeu;

public class LabyDessin implements DessinJeu {
    private final static int CELL_SIZE = 50;

    @Override
    public void dessinerJeu(Jeu jeu, Canvas canvas) {
        Labyrinthe laby = ((LabyJeu) jeu).getLaby();


        canvas.setWidth(laby.getLength() * CELL_SIZE);
        canvas.setHeight(laby.getLengthY() * CELL_SIZE);


        //affiche le labyrinthe charge
        for (int y = 0; y < laby.getLengthY(); y++) {
            // affiche la ligne
            for (int x = 0; x < laby.getLength(); x++) {
                if (laby.getMur(x, y))
                    canvas.getGraphicsContext2D().fillRect(x * CELL_SIZE, y * CELL_SIZE, CELL_SIZE, CELL_SIZE);
                else
                    canvas.getGraphicsContext2D().fillRect(x * CELL_SIZE, y * CELL_SIZE, CELL_SIZE, CELL_SIZE);
            }
            // saut de ligne
            System.out.println();
        }
    }
}
