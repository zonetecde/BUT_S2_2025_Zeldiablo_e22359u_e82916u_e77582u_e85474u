package gameZeldiablo.Zeldiablo.StrategieDeplacement;

import gameZeldiablo.Zeldiablo.Entities.Monstre;
import gameZeldiablo.Zeldiablo.Entities.Player;
import gameZeldiablo.Zeldiablo.Labyrinthe;
import gameZeldiablo.Zeldiablo.StrategieDeplacement.BellmandFord.*;

public class DeplacementIntelligent implements DeplacementStrategie {
    private static String labyActuel;
    private static GrapheListe graphe;

    @Override
    /**
     * Déplace l'entité selon la stratégie de déplacement intelligente.
     * Le monstre se déplace vers le joueur en minimisant la distance.
     *
     * @param labyrinthe Le labyrinthe dans lequel se trouve le monstre.
     * @param monstre    Le monstre qui utilise cette stratégie de déplacement.
     */
    public void deplacement(Labyrinthe labyrinthe, Monstre monstre) {
        // Récupération de la position du joueur
        Player joueur = labyrinthe.getJoueur();
        int posMonstreX = monstre.getX();
        int posMonstreY = monstre.getY();
        int posJoueurX = joueur.getX();
        int posJoueurY = joueur.getY();

        String caseJoueur = labyrinthe.getCase(posJoueurY, posJoueurX).toString();
        String caseMonstre = labyrinthe.getCase(posMonstreY, posMonstreX).toString();

        // Créer un graphe du labyrinthe
        if(graphe == null || !labyActuel.equals(labyrinthe.getNomDuLab())) {
            labyActuel = labyrinthe.getNomDuLab();
            graphe = new GrapheListe(labyrinthe);
        }

        // Calculer le chemin le plus court vers le joueur
        Algorithme bellmanFord = new BellmanFord();
        var resultat = bellmanFord.resoudre(graphe, caseMonstre);
        var chemin = resultat.calculerChemin(caseJoueur);

        // Si le chemin n'est pas vide, déplacer le monstre vers la prochaine case
        if (chemin.size() > 1) {
            String prochaineCase = chemin.get(1);

            int[] coordonnees = getCaseCoordinates(prochaineCase);
            int prochaineY = coordonnees[0];
            int prochaineX = coordonnees[1];

            // Vérifier si la case est walkable avant de déplacer le monstre
            if (labyrinthe.getCase(prochaineY, prochaineX).getIsWalkable() && labyrinthe.monstreSurCase(prochaineY, prochaineX) && !labyrinthe.joueurSurCase(prochaineY, prochaineX)) {
                monstre.setPosition(prochaineY, prochaineX);
            }
        }
    }

    private int[] getCaseCoordinates(String caseString) {
        // Extrait les coordonnées du format "Case{x=1, y=2, isWalkable=true}"
        String[] parts = caseString.split("x=")[1].split(", y=");
        int x = Integer.parseInt(parts[0]);
        int y = Integer.parseInt(parts[1].split(",")[0]);
        return new int[]{y, x};
    }
}