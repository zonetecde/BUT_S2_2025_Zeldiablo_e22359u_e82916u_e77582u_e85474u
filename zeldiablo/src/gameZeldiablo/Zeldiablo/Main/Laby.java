package gameZeldiablo.Zeldiablo.Main;

import gameZeldiablo.Zeldiablo.Cases.Case;
import gameZeldiablo.Zeldiablo.Direction;
import gameZeldiablo.Zeldiablo.Entities.Entite;
import gameZeldiablo.Zeldiablo.Entities.Player;
import gameZeldiablo.Zeldiablo.Labyrinthe;
import gameZeldiablo.Zeldiablo.VariablesGlobales;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Text;
import moteurJeu.Clavier;

import java.util.ArrayList;
import java.util.List;


class Laby {

    public static double itemFloat = 20;

    static void inputLaby(Clavier clavier, Player joueur, ZeldiabloJeu jeu){
        if (clavier.interactionKey) {
            // Ramasse l'objet si possible
            joueur.ramasserItem();
            // Intéragit avec la case
            Labyrinthe currentLaby = joueur.getLabyrinthe();
            Case playerCase = currentLaby.getCase(joueur.getY(), joueur.getX());
            playerCase.onAction(joueur, jeu);
        }

        if (clavier.droite) {
            joueur.deplacer(Direction.DROITE);
            joueur.facing = Direction.DROITE;
            joueur.setSpriteInt(3);
        } else if (clavier.gauche) {
            joueur.deplacer(Direction.GAUCHE);
            joueur.facing = Direction.GAUCHE;
            joueur.setSpriteInt(2);
        } else if (clavier.haut) {
            joueur.deplacer(Direction.HAUT);
            joueur.facing = Direction.HAUT;
            joueur.setSpriteInt(0);
        } else if (clavier.bas) {
            joueur.deplacer(Direction.BAS);
            joueur.facing = Direction.BAS;
            joueur.setSpriteInt(1);
        }
        else if (clavier.x) {
            joueur.attaque();
            if (joueur.getSpriteInt()< VariablesGlobales.SPRITE_JOUEUR.length/2) {
                joueur.setSpriteInt(joueur.getSpriteInt() + 4);
            }
        }
    }

    /**
     * Affiche du labyrinthe dans le canvas (chaque case, les entités, etc.)
     * @param joueur concerné
     * @param gc Le contexte graphique pour dessiner sur le canvas.
     * @param canvas Le canvas sur lequel dessiner le labyrinthe.
     */
    static void labyUI(Player joueur, GraphicsContext gc, Canvas canvas) {
        // dessin fond
        gc.setFill(Color.rgb(121, 101, 193));
        gc.fillRect(0, 0, canvas.getWidth(), canvas.getHeight());

        if (joueur.getLabyrinthe() == null) {
            System.out.println("Erreur dessinerJeu");
        }

        double[] pos = joueur.getDoublePos();
        double pX = pos[1]- (double) VariablesGlobales.DISTANCE_VUE /2;
        double pY = pos[0]- (double) VariablesGlobales.DISTANCE_VUE /2;

        for (int y = (int) (pY-VariablesGlobales.DISTANCE_VUE); y < pY+VariablesGlobales.DISTANCE_VUE; y++) {
            for (int x = (int) (pX-VariablesGlobales.DISTANCE_VUE); x < pX+VariablesGlobales.DISTANCE_VUE; x++) {
                try {
                    // Affichage des murs
                    gc.drawImage(joueur.getLabyrinthe().getCase(y, x).getSprite(), (x-pX) * VariablesGlobales.TAILLE_CASE, (y-pY) * VariablesGlobales.TAILLE_CASE, VariablesGlobales.TAILLE_CASE, VariablesGlobales.TAILLE_CASE);

                    // Affichage des items
                    try {
                        gc.drawImage(joueur.getLabyrinthe().getCase(y, x).getItem().getSprite(), (x-pX) * VariablesGlobales.TAILLE_CASE, ((y-pY) * VariablesGlobales.TAILLE_CASE)-itemFloat, VariablesGlobales.TAILLE_CASE, VariablesGlobales.TAILLE_CASE);
                    } catch (Exception none) {
                        // L'img n'a pas encore chargé
                    }

                    affichageEntites(joueur, gc, x, y, pX, pY);

                }catch (Exception ignore){}
            }
        }
    }

    private static void affichageEntites(Player joueur, GraphicsContext gc, int x, int y, double pX, double pY) {
        List<Entite> entites = joueur.getLabyrinthe().getEntites();
        for (Entite m : entites) {
            if (m.getX() == x && m.getY() == y && m.getEnVie()) {
                double[] pos2 = m.getDoublePos();
                gc.drawImage(m.getSprite(), (pos2[1]- pX) * VariablesGlobales.TAILLE_CASE, (pos2[0]- pY) * VariablesGlobales.TAILLE_CASE, VariablesGlobales.TAILLE_CASE, VariablesGlobales.TAILLE_CASE);
                // Dessiner la barre de vie au-dessus du monstre
                dessinerBarreVieMonstre(gc, m, (pos2[1]- pX), (pos2[0]- pY));

                // Dessine ce qu'il a à dire au-dessus de lui
                if (m.getMsgToSay() != null && !m.getMsgToSay().isEmpty()) {
                    String msg = m.getMsgToSay();
                    // Calcule la largeur du texte pour le centrer
                    Text text = new Text(msg);
                    text.setFont(Font.font("Arial", FontWeight.BOLD, 12));
                    double textWidth = text.getLayoutBounds().getWidth();
                    double textHeight = text.getLayoutBounds().getHeight();
                    double textX = (x - pX) * VariablesGlobales.TAILLE_CASE + (VariablesGlobales.TAILLE_CASE - textWidth) / 2;
                    double textY = (y - pY) * VariablesGlobales.TAILLE_CASE - 10;

                    // Vérifier si le texte sort de la fenêtre à gauche
                    if (textX < 0) {
                        textX = 5;
                    }

                    // Dessine le fond blanc
                    gc.setFill(Color.WHITE);
                    gc.fillRoundRect(textX - 5, textY - textHeight, textWidth + 10, textHeight + 5, 5, 5);

                    // Dessine le texte en noir
                    gc.setFill(Color.BLACK);
                    gc.setFont(Font.font("Arial", FontWeight.BOLD, 12));
                    gc.fillText(msg, textX, textY);
                }
            }
        }
    }


    /**
     * Dessine la barre de vie d'un monstre au-dessus de sa position
     * @param gc Le contexte graphique pour dessiner
     * @param monstre Le monstre dont on veut afficher la barre de vie
     * @param x Position x du monstre sur le plateau
     * @param y Position y du monstre sur le plateau
     */
    private static void dessinerBarreVieMonstre(GraphicsContext gc, Entite monstre, double x, double y) {
        double barreWidth = VariablesGlobales.TAILLE_CASE * 0.8;
        double barreHeight = 4;
        double barreX = x * VariablesGlobales.TAILLE_CASE + (VariablesGlobales.TAILLE_CASE - barreWidth) / 2;
        double barreY = y * VariablesGlobales.TAILLE_CASE - 8; // 8px au dessus du monstre

        // Fond de la barre (gris)
        gc.setFill(Color.DARKGRAY);
        gc.fillRect(barreX, barreY, barreWidth, barreHeight);

        // Barre de vie (rouge à vert selon les HP)
        double pourcentageVie = monstre.getHp() / monstre.getMaxHp();
        double largeurVie = barreWidth * pourcentageVie;

        // Change la couleur en fonction de la vie restante
        if (pourcentageVie > 0.6) {
            gc.setFill(Color.GREEN);
        } else if (pourcentageVie > 0.3) {
            gc.setFill(Color.ORANGE);
        } else {
            gc.setFill(Color.RED);
        }

        gc.fillRect(barreX, barreY, largeurVie, barreHeight);

        // Bordure
        gc.setStroke(Color.BLACK);
        gc.setLineWidth(1);
        gc.strokeRect(barreX, barreY, barreWidth, barreHeight);
    }


}
