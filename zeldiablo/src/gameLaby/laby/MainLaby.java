package gameLaby.laby;

import moteurJeu.DessinJeu;
import moteurJeu.MoteurJeu;

public class MainLaby {
    public static void main(String[] args) {
        int pFPS = 100;        // creation des objets
        LabyJeu labyJeu = new LabyJeu();
        LabyDessin labyDessin = new LabyDessin();

        labyJeu.init();

        // parametrage du moteur de jeu
        MoteurJeu.setTaille(labyJeu.getLaby().getLongueur() * VariablesGlobales.TAILLE_CASE, labyJeu.getLaby().getHauteur() * VariablesGlobales.TAILLE_CASE + 20);
        MoteurJeu.setFPS(pFPS);

        // lancement du jeu
        MoteurJeu.launch(labyJeu, labyDessin);

    }
}
