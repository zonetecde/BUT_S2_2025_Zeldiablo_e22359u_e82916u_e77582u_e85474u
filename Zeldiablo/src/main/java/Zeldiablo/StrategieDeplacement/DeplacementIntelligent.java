package Zeldiablo.StrategieDeplacement;

import Zeldiablo.Entities.Monstre;
import Zeldiablo.Entities.Player;
import Zeldiablo.StrategieDeplacement.BellmandFord.Algorithme;
import Zeldiablo.StrategieDeplacement.BellmandFord.BellmanFord;
import Zeldiablo.StrategieDeplacement.BellmandFord.GrapheListe;

import java.io.Serializable;

public class DeplacementIntelligent implements DeplacementStrategie, Serializable {

    /**
     * Déplace l'entité selon la stratégie de déplacement intelligente.
     * Le monstre se déplace vers le joueur en minimisant la distance.
     *
     * @param monstre    Le monstre qui utilise cette stratégie de déplacement.
     */
    @Override
    public void deplacement(Player joueur , Monstre monstre) {
        // Récupération de la position du joueur
        int posMonstreX = monstre.getX();
        int posMonstreY = monstre.getY();
        int posJoueurX = joueur.getX();
        int posJoueurY = joueur.getY();

        String caseJoueur = joueur.getLabyrinthe().getCase(posJoueurY, posJoueurX).toString();
        String caseMonstre = joueur.getLabyrinthe().getCase(posMonstreY, posMonstreX).toString();

        // Créer un graphe du labyrinthe
        GrapheListe graphe = new GrapheListe(joueur.getLabyrinthe());

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
            if (joueur.getLabyrinthe().getCase(prochaineY, prochaineX).getIsWalkable() && joueur.getLabyrinthe().entiteSurCase(prochaineY, prochaineX)) {
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

    public String toString(){return "Intelligent";}
}