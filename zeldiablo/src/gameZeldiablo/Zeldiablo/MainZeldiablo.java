package gameZeldiablo.Zeldiablo;

import moteurJeu.MoteurJeu;

public class MainZeldiablo {
    public static void main(String[] args) {
        int pFPS = 100;        // creation des objets
        ZeldiabloJeu zeldiabloJeu = new ZeldiabloJeu();
        ZeldiabloDessin zeldiabloDessin = new ZeldiabloDessin();

        zeldiabloJeu.init();

        // parametrage du moteur de jeu
        MoteurJeu.setTaille(zeldiabloJeu.getLaby().getLongueur() * VariablesGlobales.TAILLE_CASE+100, zeldiabloJeu.getLaby().getHauteur() * VariablesGlobales.TAILLE_CASE + 20);
        MoteurJeu.setFPS(pFPS);

        // lancement du jeu
        MoteurJeu.launch(zeldiabloJeu, zeldiabloDessin);

    }
}
