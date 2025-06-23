package gameZeldiablo.Zeldiablo;

/**
 * Constantes du jeu Zeldiablo
 */
public class VariablesGlobales {
        public static final String DOSSIER_LABY = "Laby/LabyBin"; // labyJeu ou labyIntro

        //Images
        //Monstres
        public static final String[] SPRITE_MONSTRE_I0 = {"Monstre/SlimeGreen.png","Monstre/SlimeGreenSquished.png"};
        public static final String[] SPRITE_MONSTRE_I1 = {"Monstre/SlimeOrange.png","Monstre/SlimeOrangeSquished.png"};
        public static final String[] SPRITE_MONSTRE_I2 = {"Monstre/SlimeRed.png","Monstre/SlimeRedSquished.png"};
        public static final String[] SPRITE_MONSTRE_I3 = {"Monstre/SlimeBlue.png","Monstre/SlimeBlueSquished.png"};
        public static final String[][] SPRITE_MONSTRE = {SPRITE_MONSTRE_I0,SPRITE_MONSTRE_I1,SPRITE_MONSTRE_I2,SPRITE_MONSTRE_I3};

        public static final String[] SPRITE_EXPLOSION = {"Tiles/Explosion/Explosion_1.png","Tiles/Explosion/Explosion_2.png","Tiles/Explosion/Explosion_3.png","Tiles/Sol.png"};
        public static final String[] SPRITE_JOUEUR =  {"player/PlayerFaceUp.png","player/PlayerFaceDown.png","player/PlayerFaceLeft.png","player/PlayerFaceRight.png","player/PlayerStabUp.png","player/PlayerStabDown.png","player/PlayerStabLeft.png","player/PlayerStabRight.png"};
        public static final String[] SPRITE_FOOD = {"Food/Baguette.png","Food/EyeBall.png","Food/Infested.png","Food/Meat.png","Food/Turkey.png","Food/Wotahmelon.png"};

        //Sprites de cases
        public static final String SPRITE_SPAWN = "Tiles/Spawn.png";
        public static final String SPRITE_CASE_VIDE = "Tiles/Sol.png";
        public static final String SPRITE_ESCALIER_HAUT = "Tiles/StairUp.png";
        public static final String SPRITE_ESCALIER_BAS = "Tiles/StairDown.png";
        public static final String SPRITE_MUR = "Tiles/Mur.png";
        public static final String SPRITE_P_P = "Tiles/PressurePlate.png";
        public static final String SPRITE_PIEGE = "Tiles/Piege.png";
        public static final String SPRITE_PORTE_FERMEE = "Tiles/DoorClosed.png";
        public static final String SPRITE_PORTE_OUVERTE = "Tiles/DoorOpened.png";
        public static final String SPRITE_PANCARTE = "Tiles/Pancarte.png";
        public static final String SPRITE_CASE_BOMBE = "Tiles/Bomb.png";

        //Sprite Items
        public static final String SPRITE_AMULETTE = "Items/Amulette.png";
        public static final String SPRITE_EPEE = "Items/Epee.png";
        public static final String SPRITE_BATON = "Items/Baton.png";
        public static final String SPRITE_HACHE = "Items/hache.png";
        public static final String SPRITE_BOMBE = "Items/Bomb.png";

        //Constantes
        public static final int DISTANCE_VUE = 15;//Mettre valeur impaire
        public static final int TAILLE_CASE = 40;
        public static final double PV_BASE = 5;
        public static final double DEGAT_BASE = 1;
        public static final double PROBA_MONSTRE = 0.08; // 8% de chance de générer un monstre dans une case vide
        public static final int TICK_SPEED = 100; // Délai en millisecondes entre les déplacements des monstres
        public static final int NBRE_MONSTRES_MAX = 120; // Nombre maximum de monstres dans le labyrinthe
        public static final double HEAL_FOOD = 3;

        //VariablesMenu
        public static final int COL_NUM_MENU = 3;

}
