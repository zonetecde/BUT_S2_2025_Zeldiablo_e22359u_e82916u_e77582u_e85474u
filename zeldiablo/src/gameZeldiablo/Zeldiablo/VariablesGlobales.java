package gameZeldiablo.Zeldiablo;

import javafx.scene.paint.Color;

public class VariablesGlobales {
        /**
         * Constantes du jeu Zeldiablo
         */
        //Couleurs
        public static final Color COULEUR_VIDE = Color.WHITE;
        public static final Color COULEUR_MUR = Color.BLACK;
        public static final Color COULEUR_PIEGE = Color.PURPLE;
        public static final Color COULEUR_ESCALIER1 = Color.BLUE;
        public static final Color COULEUR_ESCALIER2 = Color.LIGHTBLUE;

        //Images
        public static final String[] SPRITE_MONSTRE = {"Monstre/SlimeBlack.png","Monstre/SlimeBlue.png","Monstre/SlimeGreen.png","Monstre/SlimeOrange.png"};
        public static final String[] SPRITE_JOUEUR =  {"player/PlayerFaceUp.png","player/PlayerFaceDown.png","player/PlayerFaceLeft.png","player/PlayerFaceRight.png"};
        public static final String[] SPRITE_FOOD = {"Food/Baguette.png","Food/EyeBall.png","Food/Infested.png","Food/Meat.png","Food/Turkey.png","Food/Wotahmelon.png"};

        public static final String SPRITE_CASE_VIDE = "Tiles/Sol.png";
        public static final String SPRITE_ESCALIER_HAUT = "Tiles/StairUp.png";
        public static final String SPRITE_ESCALIER_BAS = "Tiles/StairDown.png";
        public static final String SPRITE_MUR = "Tiles/Mur.png";
        public static final String SPRITE_P_P = "Tiles/PressurePlate.png";
        public static final String SPRITE_PIEGE = "Tiles/Piege.png";
        public static final String SPRITE_PORTE_FERMEE = "Tiles/DoorClosed.png";
        public static final String SPRITE_PORTE_OUVERTE = "Tiles/DoorOpened.png";

        public static final String SPRITE_AMULETTE = "Items/Amulette.png";
        public static final String SPRITE_EPEE = "Items/Epee.png";

        //Constantes
        public static final int TAILLE_CASE = 40;
        public static final double PV_BASE = 5;
        public static final double DEGAT_BASE = 1;
        public static final double PROBA_MONSTRE = 0.02; // 2% de chance de générer un monstre dans une case vide
        public static final int DEPLACEMENT_MONSTRE = 1500; // Délai en millisecondes entre les déplacements des monstres

        //VariablesMenu
        public static boolean curseurStart = true;
        public static final int COL_NUM_MENU = 3;
        public static boolean MenuOuvert=false;
        public static int curseur=0;

}
