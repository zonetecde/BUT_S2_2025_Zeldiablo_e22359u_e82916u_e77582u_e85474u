package Zeldiablo.Main;

import Zeldiablo.Entities.Player;
import Zeldiablo.Equipement;
import Zeldiablo.VariablesGlobales;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import moteurJeu.DessinJeu;
import moteurJeu.Jeu;

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
            Join.logUI(player,gc,canvas);
        } else if (player.estMort()){
            Start.startUI(player,gc,canvas);
        } else if (player.aGagne()){
            Start.startUI(player,gc,canvas);
            afficherEcranVictoire(gc,canvas);
            javafx.animation.Timeline timeline = new javafx.animation.Timeline(
                new javafx.animation.KeyFrame(
                    javafx.util.Duration.seconds(3),
                        _ -> {
                        player.setaGagne(false);
                        player.setEnVie(false);
                    }
                )
            );
            timeline.setCycleCount(1);
            timeline.play();
            
        } else {
            // Dessine le laby
            Laby.labyUI(player, gc, canvas);

            // Dessin les infos sur le joueur
            heroUI(player, gc);

            // Affiche les instructions
            instructionUI(gc);

            // Affiche l'item actuellement sélectionné dans l'inventaire
            statsUI(player, gc);

            if (player.menuOuvert) {
                Inv.invUI(player, gc);
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
            imageJoueur = new Image("joueur/PlayerFaceDown.png");
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
    
    private void statsUI(Player joueur, GraphicsContext gc) {
        Equipement equipement = joueur.getEquipement();
        int baseXPlayer = VariablesGlobales.DISTANCE_VUE * VariablesGlobales.TAILLE_CASE;
        gc.setFill(Color.BLACK);
        gc.setFont(Font.font("Arial", FontWeight.BOLD, 12));
        String itemText = "Attaque : " + equipement.AttaqueGet();
        String itemText2 = "Defense : "+ equipement.DefenseGet();
        gc.fillText(itemText, baseXPlayer + 5, 125);
        gc.fillText(itemText2, baseXPlayer + 5, 140);
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

}