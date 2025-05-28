package gameZeldiablo.Zeldiablo;

import moteurJeu.MoteurJeu;

public class MainZeldiablo {
    public static void main(String[] args) {
        int level = 0;

        if(args.length > 0){
            // Le premier arg est le nom du niveau à charger
            try {
                level = Integer.parseInt(args[0]);
            } catch(NumberFormatException ex){
                System.out.println("Numéro de niveau invalide. Chargement du niveau 1.");
            }
        }

        int pFPS = 100;        // creation des objets
        ZeldiabloJeu zeldiabloJeu = new ZeldiabloJeu(level);
        ZeldiabloDessin zeldiabloDessin = new ZeldiabloDessin();

        zeldiabloJeu.init();

        // parametrage du moteur de jeu
        MoteurJeu.setTaille(zeldiabloJeu.getLaby().getLongueur() * VariablesGlobales.TAILLE_CASE+100, zeldiabloJeu.getLaby().getHauteur() * VariablesGlobales.TAILLE_CASE + 20);
        MoteurJeu.setFPS(pFPS);

        // lancement du jeu
        MoteurJeu.launch(zeldiabloJeu, zeldiabloDessin);
    }
}
