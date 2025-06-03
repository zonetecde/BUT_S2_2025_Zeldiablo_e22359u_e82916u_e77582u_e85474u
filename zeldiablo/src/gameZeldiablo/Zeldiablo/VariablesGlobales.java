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
        public static final Color COULEUR_ESCALIER = Color.BLUE;

        //Constantes
        public static final int TAILLE_CASE = 40;
        public static final double PV_BASE = 5;
        public static final double DEGAT_BASE = 1;
        public static final int PROBA_MONSTRE = 40; // 1/40 de chance de générer un monstre

        //VariablesMenu
        public static boolean curseurStart = true;
        public static final int COL_NUM_MENU = 3;
        public static boolean MenuOuvert=false;
        public static int curseur=0;

}
