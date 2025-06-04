package gameZeldiablo.Zeldiablo;

/**
 * Constantes du jeu Zeldiablo
 */
public class VariablesGlobales {
        public static final String DOSSIER_LABY = "labyIntro"; // labyJeu ou labyIntro

        //Images
        public static final String[] SPRITE_MONSTRE = {"Monstre/statique.png","Monstre/faible.png","Monstre/moyen.png","Monstre/intelligent.png"};
        public static final String[] SPRITE_JOUEUR =  {"player/PlayerFaceUp.png","player/PlayerFaceDown.png","player/PlayerFaceLeft.png","player/PlayerFaceRight.png","player/PlayerStabUp.png","player/PlayerStabDown.png","player/PlayerStabLeft.png","player/PlayerStabRight.png"};
        public static final String[] SPRITE_FOOD = {"Food/Baguette.png","Food/EyeBall.png","Food/Infested.png","Food/Meat.png","Food/Turkey.png","Food/Wotahmelon.png"};

        //Sprites de cases
        public static final String SPRITE_CASE_VIDE = "Tiles/Sol.png";
        public static final String SPRITE_ESCALIER_HAUT = "Tiles/StairUp.png";
        public static final String SPRITE_ESCALIER_BAS = "Tiles/StairDown.png";
        public static final String SPRITE_MUR = "Tiles/Mur.png";
        public static final String SPRITE_P_P = "Tiles/PressurePlate.png";
        public static final String SPRITE_PIEGE = "Tiles/Piege.png";
        public static final String SPRITE_PORTE_FERMEE = "Tiles/DoorClosed.png";
        public static final String SPRITE_PORTE_OUVERTE = "Tiles/DoorOpened.png";
        public static final String SPRITE_PANCARTE = "Tiles/Pancarte.png";

        //Sprite Items
        public static final String SPRITE_AMULETTE = "Items/Amulette.png";
        public static final String SPRITE_EPEE = "Items/Epee.png";
        public static final String SPRITE_BATON = "Items/Baton.png";
        public static final String SPRITE_HACHE = "Items/hache.png";

        //Constantes
        public static final int TAILLE_CASE = 40;
        public static final double PV_BASE = 5;
        public static final double DEGAT_BASE = 1;
        public static final double PROBA_MONSTRE = 0.08; // 8% de chance de générer un monstre dans une case vide
        public static final int DEPLACEMENT_MONSTRE = 1500; // Délai en millisecondes entre les déplacements des monstres
        public static final int NBRE_MONSTRES_MAX = 120; // Nombre maximum de monstres dans le labyrinthe
        public static final double HEAL_FOOD = 3;

        //VariablesMenu
        public static boolean curseurStart = true;
        public static final int COL_NUM_MENU = 3;
        public static boolean MenuOuvert=false;
        public static int curseur=0;

}
