package gameZeldiablo.Zeldiablo;

import gameZeldiablo.Zeldiablo.Entities.Entite;
import gameZeldiablo.Zeldiablo.Entities.Monstre;
import gameZeldiablo.Zeldiablo.Entities.Player;
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

public class ZeldiabloDessin implements DessinJeu {
    private Image imageJoueur;
    private ZeldiabloJeu jeu;


    /**
     * Constructeur de la classe ZeldiabloDessin.
     * Il initialise les ressources nécessaires pour le dessin du jeu.
     */
    @Override
    public void dessinerJeu(Jeu jeu, Canvas canvas) {
        this.jeu= (ZeldiabloJeu) jeu;
        Player player = this.jeu.getJoueur(this.jeu.idComp);

        // recupere un pinceau pour dessiner
        final GraphicsContext gc = canvas.getGraphicsContext2D();
        if (!this.jeu.lance){
            logUI(player,gc,canvas);
        } else if (player.estMort()){
            startUI(player,gc,canvas);
        } else if (player.aGagne()){
            startUI(player,gc,canvas);
            afficherEcranVictoire(gc,canvas);
            javafx.animation.Timeline timeline = new javafx.animation.Timeline(
                new javafx.animation.KeyFrame(
                    javafx.util.Duration.seconds(3),
                    event -> {
                        player.setaGagne(false);
                        player.setEnVie(false);
                    }
                )
            );
            timeline.setCycleCount(1);
            timeline.play();
            
        } else {
            // Dessine le laby
            labyUI(player, gc, canvas);

            // Dessin les infos sur le joueur
            heroUI(player, gc);

            // Affiche les instructions
            instructionUI(gc);

            // Affiche l'item actuellement sélectionné dans l'inventaire
            itemActuellementSelectionneUI(player, gc);

            if (player.menuOuvert) {
                invUI(player, gc, canvas);
            }
        }
    }

    private void logUI(Player joueur,GraphicsContext gc,Canvas c){
        //Fond
        gc.setFill(Color.rgb(72, 58, 160));
        gc.fillRect(0,0,c.getWidth(),c.getHeight());
        gc.setFill(Color.rgb(121, 101, 193));
        gc.setFont(Font.font("Caladea" ,44));

        double baseH = c.getHeight()/5;

        for (int i=1 ; i<4 ; i++) {
            gc.strokeRect(c.getWidth() / 3, baseH*i , c.getWidth() / 3, c.getHeight() / 7);
            gc.fillRect(c.getWidth() / 3, baseH*i , c.getWidth() / 3, c.getHeight() / 7);
        }


        //Texte
        gc.setFill(Color.BLACK);
        gc.fillText("Solo",(c.getWidth()/2.5),(baseH * 1.5));
        gc.fillText("Join" , c.getWidth()/2.5,baseH * 2.5);
        gc.fillText("Host" , c.getWidth()/2.5,baseH * 3.5);

        //Curseur
        double emCursor = switch (joueur.curseurLog) {
            case 0 -> baseH *  1.25;
            case 1 -> baseH *  2.25;
            default -> baseH * 3.25;
        };

        gc.fillOval(c.getWidth()/3-10,emCursor,20,20);
    }

    /**
     * Affiche du labyrinthe dans le canvas (chaque case, les entités, etc.)
     * @param joueur concerné
     * @param gc Le contexte graphique pour dessiner sur le canvas.
     * @param canvas Le canvas sur lequel dessiner le labyrinthe.
     */
    private void labyUI(Player joueur, GraphicsContext gc, Canvas canvas) {
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
                    // Couleur des murs - noir
                    gc.drawImage(joueur.getLabyrinthe().getCase(y, x).getSprite(), (x-pX) * VariablesGlobales.TAILLE_CASE, (y-pY) * VariablesGlobales.TAILLE_CASE, VariablesGlobales.TAILLE_CASE, VariablesGlobales.TAILLE_CASE);


                    ArrayList<Entite> entites = joueur.getLabyrinthe().getEntites();
                    for (Entite m : entites) {
                        if (m.getX() == x && m.getY() == y && m.getEnVie()) {
                            gc.drawImage(m.getSprite(), (pos[1]-pX) * VariablesGlobales.TAILLE_CASE, (pos[0]-pY) * VariablesGlobales.TAILLE_CASE, VariablesGlobales.TAILLE_CASE, VariablesGlobales.TAILLE_CASE);
                            // Dessiner la barre de vie au-dessus du monstre
                            dessinerBarreVieMonstre(gc, m, (x-pX), (y-pY));

                            // Dessine ce qu'il a à dire au-dessus de lui
                            if (m.getMsgToSay() != null && !m.getMsgToSay().isEmpty()) {
                                String msg = m.getMsgToSay();
                                // Calcule la largeur du texte pour le centrer
                                Text text = new Text(msg);
                                text.setFont(Font.font("Arial", FontWeight.BOLD, 12));
                                double textWidth = text.getLayoutBounds().getWidth();
                                double textHeight = text.getLayoutBounds().getHeight();
                                double textX = (x-pX) * VariablesGlobales.TAILLE_CASE + (VariablesGlobales.TAILLE_CASE - textWidth) / 2;
                                double textY = (y-pY) * VariablesGlobales.TAILLE_CASE - 10;

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

                    // Affichage des items
                    try {
                        gc.drawImage(joueur.getLabyrinthe().getCase(y, x).getItem().getSprite(), (x-pX) * VariablesGlobales.TAILLE_CASE, (y-pY) * VariablesGlobales.TAILLE_CASE, VariablesGlobales.TAILLE_CASE, VariablesGlobales.TAILLE_CASE);
                    } catch (Exception none) {
                        // L'img n'a pas encore chargé
                    }

                }catch (Exception ignore){}
            }
        }
    }

    /**
     * Affiche l'interface utilisateur du joueur
     * @param joueur le joueur concerné
     * @param gc Le contexte graphique pour dessiner sur le canvas.
     */
    private void heroUI(Player joueur, GraphicsContext gc) {
        if(imageJoueur == null){
            imageJoueur = new Image("player/PlayerFaceDown.png");
        }

        int baseXPlayer = VariablesGlobales.DISTANCE_VUE * VariablesGlobales.TAILLE_CASE;
        gc.setFill(Color.rgb(72, 58, 160));
        gc.fillRect(baseXPlayer,0,100,VariablesGlobales.DISTANCE_VUE*VariablesGlobales.TAILLE_CASE);
        gc.drawImage(imageJoueur, baseXPlayer + 25, 0, 50, 75);
        gc.setFill(Color.GREY);
        gc.fillRect(baseXPlayer + 5, 75, 90, 20);
        gc.setFill(Color.RED);
        gc.fillRect(baseXPlayer + 5, 75, getLifeBarWidth(joueur.getHp(), joueur.getMaxHp()), 20);
    }

    /**
     * Affiche les instructions de jeu sur l'UI
     * @param gc  Le contexte graphique pour dessiner sur le canvas.
     */
    private void instructionUI(GraphicsContext gc) {
        int baseXPlayer = VariablesGlobales.DISTANCE_VUE * VariablesGlobales.TAILLE_CASE;
        
        gc.setFill(Color.BLACK);
        gc.setFont(Font.font("Arial", FontWeight.BOLD, 12));
        String displayLog = "Not logged in";
        if (jeu.roomID != null){
            displayLog=jeu.roomID;
        }
        gc.fillText("ZQSD : Move", baseXPlayer + 5, 130 + 125);
        gc.fillText("E : Take", baseXPlayer + 5, 145 + 125);
        gc.fillText("Tab : Inv", baseXPlayer + 5, 160 + 125);
        gc.fillText("X : Attack", baseXPlayer + 5, 175 + 125);
        gc.fillText("Space : Use", baseXPlayer + 5, 190 + 125);
        gc.fillText(displayLog, baseXPlayer + 5, 205 + 125);
    }   
    
    private void itemActuellementSelectionneUI(Player joueur, GraphicsContext gc) {
        ArrayList<Item> inv = joueur.getInventory();
        if (!inv.isEmpty()) {
            Item item = inv.get(joueur.curseur);
            int baseXPlayer = VariablesGlobales.DISTANCE_VUE * VariablesGlobales.TAILLE_CASE;
            gc.setFill(Color.BLACK);
            gc.setFont(Font.font("Arial", FontWeight.BOLD, 12));
            String itemText = "Selected";
            String itemText2 = item.toString();
            gc.fillText(itemText, baseXPlayer + 5, 125);
            gc.fillText(itemText2, baseXPlayer + 5, 140);
        }
    }

    /**
     * Affiche l'interface utilisateur de l'inventaire du joueur.
     * @param joueur actuel
     * @param gc Le contexte graphique pour dessiner sur le canvas.
     * @param c Le canvas sur lequel dessiner l'inventaire.
     */    
    private void invUI(Player joueur, GraphicsContext gc, Canvas c){
        ArrayList<Item> inv = joueur.getInventory();

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
            if (i == joueur.curseur) {
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
            gc.setFill(Color.WHITE);
            gc.setFont(Font.font("Arial", FontWeight.BOLD, 14));
            String itemText = inv.get(i).toString();
            Text text = new Text(itemText);
            text.setFont(gc.getFont());
            double textWidth = text.getLayoutBounds().getWidth();
            double textX = caseX + (caseWidth - textWidth) / 2;
            double textY = caseY + (double) caseHeight / 2 + 5;

            gc.fillText(itemText, textX, textY);
            gc.drawImage(inv.get(i).getSprite(),caseX+10,caseY,24,24);

            x += 1;
            if (x >= VariablesGlobales.COL_NUM_MENU) {
                x = 0;
                y += 1;
            }
        }
    }

    public void startUI(Player joueur,GraphicsContext gc, Canvas c){
        gc.clearRect(0,0,c.getWidth(),c.getHeight());
        //Fond
        gc.setFill(Color.rgb(72, 58, 160));
        gc.fillRect(0,0,c.getWidth(),c.getHeight());
        gc.setFill(Color.rgb(121, 101, 193));
        gc.setFont(Font.font("Caladea" ,44));

        double baseH = c.getHeight()/4;

        for (int i=1 ; i<3 ; i++) {
            gc.strokeRect(c.getWidth() / 3, baseH*i , c.getWidth() / 3, c.getHeight() / 7);
            gc.fillRect(c.getWidth() / 3, baseH*i , c.getWidth() / 3, c.getHeight() / 7);
        }

        //Texte
        gc.setFill(Color.BLACK);
        gc.fillText("Start",(c.getWidth()/2.5),baseH * 1.4);
        gc.fillText("Leave" , c.getWidth()/2.5,baseH * 2.4);

        //Curseur
        double emCursor;
        if (joueur.curseurStart){
            emCursor=baseH * 1.25;
        }
        else{
            emCursor=baseH * 2.25;
        }

        gc.fillOval(c.getWidth()/3-10,emCursor,20,20);
    }

    private void afficherEcranVictoire(GraphicsContext gc, Canvas canvas) {
        gc.setFill(Color.rgb(0, 0, 0, 0.8));
        gc.fillRect(0, 0, canvas.getWidth(), canvas.getHeight());
        gc.setFill(Color.GOLD);
        gc.setFont(Font.font("Arial", FontWeight.BOLD, 48));
        gc.fillText("Victoire !", canvas.getWidth() / 2 - 100, canvas.getHeight() / 2);
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
    private void dessinerBarreVieMonstre(GraphicsContext gc, Entite monstre, double x, double y) {
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