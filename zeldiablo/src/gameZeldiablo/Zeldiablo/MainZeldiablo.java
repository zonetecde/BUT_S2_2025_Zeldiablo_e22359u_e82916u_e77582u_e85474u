package gameZeldiablo.Zeldiablo;

import moteurJeu.MoteurJeu;

public class MainZeldiablo {

    /**
     * Point d'entrée du jeu Zeldiablo.
     * Charge le niveau spécifié ou le niveau par défaut si aucun n'est fourni.
     *dxdv
     * @param args Arguments de la ligne de commande, le premier argument est le numéro du niveau à charger.
     */
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
        int hauteurFenetre = zeldiabloJeu.getLaby().getHauteur() * VariablesGlobales.TAILLE_CASE + 100; // +100 pour l'interface
        int largeurFenetre = zeldiabloJeu.getLaby().getLongueur() * VariablesGlobales.TAILLE_CASE + 300; // +300 pour le menu/inventaire

        MoteurJeu.setTaille(largeurFenetre, hauteurFenetre);
        MoteurJeu.setFPS(pFPS);

        // lancement du jeu
        MoteurJeu.launch(zeldiabloJeu, zeldiabloDessin);
    }
}
