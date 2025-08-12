package Zeldiablo;

/**
 * Constantes du jeu Zeldiablo
 */
public class VariablesGlobales {
        public static final String MUSIQUE = "assets/Sound/BGMusic.mp3";
        public static final String DOSSIER_LABY = "Laby/LabyBin"; // labyJeu ou labyIntro
        public static final String RECETTE_FOLDER = "Recettes";


        //Images
        public static final String SPRITE_CROIX = "Cross.png";

        //Monstres
        public static final String[] SPRITE_MONSTRE_I0 = {"Monstre/SlimeGreen.png","Monstre/SlimeGreenSquished.png"};
        public static final String[] SPRITE_MONSTRE_I1 = {"Monstre/SlimeOrange.png","Monstre/SlimeOrangeSquished.png"};
        public static final String[] SPRITE_MONSTRE_I2 = {"Monstre/SlimeRed.png","Monstre/SlimeRedSquished.png"};
        public static final String[] SPRITE_MONSTRE_I3 = {"Monstre/SlimeBlue.png","Monstre/SlimeBlueSquished.png"};
        public static final String[][] SPRITE_MONSTRE = {SPRITE_MONSTRE_I0,SPRITE_MONSTRE_I1,SPRITE_MONSTRE_I2,SPRITE_MONSTRE_I3};

        public static final String[] SPRITE_EXPLOSION = {"Tiles/Explosion/Explosion_1.png","Tiles/Explosion/Explosion_2.png","Tiles/Explosion/Explosion_3.png"};
        public static final String[] SPRITE_JOUEUR =  {"joueur/PlayerFaceUp.png", "joueur/PlayerFaceDown.png", "joueur/PlayerFaceLeft.png", "joueur/PlayerFaceRight.png", "joueur/PlayerStabUp.png", "joueur/PlayerStabDown.png", "joueur/PlayerStabLeft.png", "joueur/PlayerStabRight.png"};
        public static final String[] SPRITE_FOOD = {"Food/Baguette.png","Food/EyeBall.png","Food/Infested.png","Food/Meat.png","Food/Turkey.png","Food/Wotahmelon.png"};

        //Sprites de cases
        public static final String SPRITE_SORTIE = "Tiles/Exit.png";
        public static final String SPRITE_SPAWN = "Tiles/Spawn.png";
        public static final String SPRITE_CASE_VIDE = "Tiles/Sol.png";
        public static final String SPRITE_ESCALIER_HAUT = "Tiles/StairUp.png";
        public static final String SPRITE_ESCALIER_BAS = "Tiles/StairDown.png";
        public static final String SPRITE_MUR = "Tiles/Mur.png";
        public static final String SPRITE_SWITCH_OFF = "Tiles/SwitchOff.png";
        public static final String SPRITE_SWITCH_ON = "Tiles/SwitchOn.png";
        public static final String SPRITE_PIEGE = "Tiles/Piege.png";
        public static final String SPRITE_PORTE_FERMEE = "Tiles/DoorClosed.png";
        public static final String SPRITE_PORTE_OUVERTE = "Tiles/DoorOpened.png";
        public static final String SPRITE_PANCARTE = "Tiles/Pancarte.png";
        public static final String SPRITE_CASE_BOMBE = "Tiles/Bomb.png";
        public static final String SPRITE_CASE_PIEDESTAL = "Tiles/Pedestal.png";

        //Sprite Items
        public static final String SPRITE_RECETTE = "Items/Note.png";
        public static final String SPRITE_AMULETTE = "Items/Amulette.png";
        public static final String SPRITE_EPEE = "Items/Epee.png";
        public static final String SPRITE_BATON = "Items/Baton.png";
        public static final String SPRITE_HACHE = "Items/hache.png";
        public static final String SPRITE_BOMBE = "Items/Bomb.png";

        //Sprite Armures
        public static final String SPRITE_CHAINMAIL = "Items/Armors/ChestPlates/0_ChainMail.png";

        //Constantes
        public static final int DISTANCE_VUE = 15;//Mettre valeur impaire
        public static final int TAILLE_CASE = 40;
        public static final double DEGAT_BASE = 1;
        public static final int TICK_SPEED = 100; // Délai en millisecondes entre les déplacements des monstres
        public static final double HEAL_FOOD = 3;

        //VariablesMenu
        public static final int COL_NUM_MENU = 3;

}
