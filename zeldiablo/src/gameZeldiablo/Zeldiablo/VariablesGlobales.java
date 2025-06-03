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

        //Constantes
        public static final int TAILLE_CASE = 40;
        public static final double PV_BASE = 5;
        public static final double DEGAT_BASE = 1;
        public static final double PROBA_MONSTRE = 0.02; // 2% de chance de générer un monstre dans une case vide

        //VariablesMenu
        public static boolean curseurStart = true;
        public static final int COL_NUM_MENU = 3;
        public static boolean MenuOuvert=false;
        public static int curseur=0;

}
