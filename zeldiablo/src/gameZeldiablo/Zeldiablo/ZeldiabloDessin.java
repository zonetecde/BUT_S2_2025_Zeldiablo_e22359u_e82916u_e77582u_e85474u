package gameZeldiablo.Zeldiablo;

import gameZeldiablo.Zeldiablo.Entities.Monstre;
import gameZeldiablo.Zeldiablo.Items.Item;
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
import java.util.Objects;

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

        if (laby.getPlayer().estMort()){
            startUI(gc,canvas);
        }
        else if (laby.getPlayer().aGagne()){
            startUI(gc,canvas);
        }
        else {
            // Dessine le laby
            labyUI(laby, gc, canvas);

            // Dessin les infos sur le joueur
            heroUI(laby, gc);

            // Affiche les instructions
            instructionUI(laby, gc);

            // Affiche l'item actuellement sélectionné dans l'inventaire
            itemActuellementSelectionneUI(laby, gc);

            if (VariablesGlobales.MenuOuvert) {
                invUI(laby, gc, canvas);
            }
        }
    }

    /**
     * Affiche du labyrinthe dans le canvas (chaque case, les entités, etc.)
     * @param laby Le labyrinthe actuel à dessiner.
     * @param gc Le contexte graphique pour dessiner sur le canvas.
     * @param canvas Le canvas sur lequel dessiner le labyrinthe.
     */
    private void labyUI(Labyrinthe laby, GraphicsContext gc, Canvas canvas) {
        // dessin fond
        gc.setFill(Color.LIGHTGRAY);
        gc.fillRect(0, 0, canvas.getWidth(), canvas.getHeight());

        if (laby == null) {
            System.out.println("Erreur dessinerJeu");
        }

        for (int y = 0; y < Objects.requireNonNull(laby).getHauteur(); y++) {
            for (int x = 0; x < laby.getLongueur(); x++) {
                // Couleur des murs - noir
                gc.drawImage(laby.getCase(y,x).getImg(),x * VariablesGlobales.TAILLE_CASE, y * VariablesGlobales.TAILLE_CASE, VariablesGlobales.TAILLE_CASE, VariablesGlobales.TAILLE_CASE);                
                
                
                ArrayList<Monstre> entites = laby.getMonstres();
                for (Monstre m : entites){
                    if (m.getX()==x && m.getY()==y){
                        gc.drawImage(m.getImg(),x * VariablesGlobales.TAILLE_CASE, y * VariablesGlobales.TAILLE_CASE, VariablesGlobales.TAILLE_CASE, VariablesGlobales.TAILLE_CASE);
                        // Dessiner la barre de vie au-dessus du monstre
                        dessinerBarreVieMonstre(gc, m, x, y);
                    }
                }

                // affichage du joueur
                if (laby.joueurSurCase(y, x)){
                    // Dessiner le joueur
                    gc.drawImage(laby.getPlayer().getImg(),x * VariablesGlobales.TAILLE_CASE, y * VariablesGlobales.TAILLE_CASE, VariablesGlobales.TAILLE_CASE, VariablesGlobales.TAILLE_CASE);

                    // Dessine ce qu'il a à dire au-dessus de lui
                    if(laby.getPlayer().getMsgToSay() != null && !laby.getPlayer().getMsgToSay().isEmpty()) {
                        String msg = laby.getPlayer().getMsgToSay();
                        // Calcule la largeur du texte pour le centrer
                        Text text = new Text(msg);
                        text.setFont(Font.font("Arial", FontWeight.BOLD, 12));
                        double textWidth = text.getLayoutBounds().getWidth();
                        double textHeight = text.getLayoutBounds().getHeight();
                        double textX = x * VariablesGlobales.TAILLE_CASE + (VariablesGlobales.TAILLE_CASE - textWidth) / 2;
                        double textY = y * VariablesGlobales.TAILLE_CASE - 10;
                        
                        // Dessine le fond blanc
                        gc.setFill(Color.WHITE);
                        gc.fillRoundRect(textX - 5, textY - textHeight, textWidth + 10, textHeight + 5, 5, 5);
                        
                        // Dessine le texte en noir
                        gc.setFill(Color.BLACK);
                        gc.setFont(Font.font("Arial", FontWeight.BOLD, 12));
                        gc.fillText(msg, textX, textY);
                    }
                }

                // Affichage des items
                try {
                    gc.drawImage(laby.getCase(y, x).getItem().getImg(), x * VariablesGlobales.TAILLE_CASE, y * VariablesGlobales.TAILLE_CASE, VariablesGlobales.TAILLE_CASE, VariablesGlobales.TAILLE_CASE);
                } catch (Exception none) {
                    // L'img n'a pas encore chargé
                }
            }
        }
    }

    /**
     * Affiche l'interface utilisateur du joueur
     * @param laby Le labyrinthe actuel contenant le joueur.
     * @param gc Le contexte graphique pour dessiner sur le canvas.
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
     * Affiche les instructions de jeu sur l'UI
     * @param gc  Le contexte graphique pour dessiner sur le canvas.
     */
    private void instructionUI(Labyrinthe laby, GraphicsContext gc) {
        int baseXPlayer = laby.getLongueur() * VariablesGlobales.TAILLE_CASE;
        
        gc.setFill(Color.BLACK);
        gc.setFont(Font.font("Arial", FontWeight.BOLD, 12));
        gc.fillText("ZQSD pour se déplacer", baseXPlayer + 5, 115 + 125);
        gc.fillText("ou pour naviguer dans l'inventaire", baseXPlayer + 5, 130 + 125);
        gc.fillText("E pour prendre un item", baseXPlayer + 5, 145 + 125);
        gc.fillText("Tab pour ouvrir l'inventaire", baseXPlayer + 5, 160 + 125);
        gc.fillText("X pour attaquer un monstre", baseXPlayer + 5, 175 + 125);
        gc.fillText("Espace dans l'inventaire pour consommer", baseXPlayer + 5, 190 + 125);
    }   
    
    private void itemActuellementSelectionneUI(Labyrinthe laby, GraphicsContext gc) {
        ArrayList<Item> inv = laby.getPlayer().getInventory();
        if (!inv.isEmpty()) {
            Item item = inv.get(VariablesGlobales.curseur);
            int baseXPlayer = laby.getLongueur() * VariablesGlobales.TAILLE_CASE;
            gc.setFill(Color.BLACK);
            gc.setFont(Font.font("Arial", FontWeight.BOLD, 12));
            String itemText = "Item sélectionné : " + item.toString();
            gc.fillText(itemText, baseXPlayer + 5, 125);
        }
    }

    /**
     * Affiche l'interface utilisateur de l'inventaire du joueur.
     * @param laby Le labyrinthe actuel contenant le joueur et son inventaire.
     * @param gc Le contexte graphique pour dessiner sur le canvas.
     * @param c Le canvas sur lequel dessiner l'inventaire.
     */    
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
            double textY = caseY + (double) caseHeight / 2 + 5;

            gc.fillText(itemText, textX, textY);

            x += 1;
            if (x >= VariablesGlobales.COL_NUM_MENU) {
                x = 0;
                y += 1;
            }
        }
    }

    public void startUI(GraphicsContext gc, Canvas c){
        //Fond
        gc.setFill(Color.SLATEGREY);
        gc.fillRect(0,0,c.getWidth(),c.getHeight());
        gc.setFill(Color.GREY);
        gc.setFont(Font.font("Caladea" ,44));

        //Premiere Case
        gc.rect(c.getWidth()/3,c.getHeight()/3,c.getWidth()/3,c.getHeight()/7);
        gc.stroke();
        gc.fill();

        //Deuxieme case
        gc.rect(c.getWidth()/3,c.getHeight()/2,c.getWidth()/3,c.getHeight()/7);
        gc.stroke();
        gc.fill();

        //Texte
        gc.setFill(Color.BLACK);
        gc.fillText("Start",(c.getWidth()/2.5),(c.getHeight()/2.5+10));
        gc.fillText("Leave" , c.getWidth()/2.5,c.getHeight()/1.75+10);

        //Curseur
        double emCursor;
        if (VariablesGlobales.curseurStart){
            emCursor=c.getHeight()/2.5-5;
        }
        else{
            emCursor=c.getHeight()/1.75-5;
        }

        gc.fillOval(c.getWidth()/3-10,emCursor,20,20);
    }

    /*
     * Calcul la largeur de la barre de vie du joueur
     * @param actualHp les points de vie actuels du joueur
     * @param maxHp les points de vie maximum du joueur
     */
    private double getLifeBarWidth(double actualHp, double maxHp) {
        return (actualHp / maxHp) * 90;
    }

    /**
     * Dessine la barre de vie d'un monstre au-dessus de sa position
     * @param gc Le contexte graphique pour dessiner
     * @param monstre Le monstre dont on veut afficher la barre de vie
     * @param x Position x du monstre sur le plateau
     * @param y Position y du monstre sur le plateau
     */
    private void dessinerBarreVieMonstre(GraphicsContext gc, Monstre monstre, int x, int y) {
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