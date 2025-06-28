package Zeldiablo.Main;

import Zeldiablo.VariablesGlobales;
import moteurJeu.MoteurJeu;

public class ZeldiabloMain {

    /**
     * Point d'entrée du jeu Zeldiablo.
     * Charge le niveau spécifié ou le niveau par défaut si aucun n'est fourni.
     * Branche 2
     * @param args Arguments de la ligne de commande, le premier argument est le numéro du niveau à charger.
     */
    public static void main(String[] args) {
        int pFPS = 50;        // creation des objets
        ZeldiabloJeu zeldiabloJeu = new ZeldiabloJeu();
        ZeldiabloDessin zeldiabloDessin = new ZeldiabloDessin();

        zeldiabloJeu.init();

        // parametrage du moteur de jeu
        int hauteurFenetre = VariablesGlobales.DISTANCE_VUE * VariablesGlobales.TAILLE_CASE; // +100 pour l'interface
        int largeurFenetre = VariablesGlobales.DISTANCE_VUE * VariablesGlobales.TAILLE_CASE + 100; // +300 pour le menu/inventaire

        MoteurJeu.setTaille(largeurFenetre, hauteurFenetre);
        MoteurJeu.setFPS(pFPS);

        // lancement du jeu
        MoteurJeu.launch(zeldiabloJeu, zeldiabloDessin);
    }
}
