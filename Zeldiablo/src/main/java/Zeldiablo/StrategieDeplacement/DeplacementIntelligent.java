package Zeldiablo.StrategieDeplacement;

import Zeldiablo.Direction;
import Zeldiablo.Entities.Entite;
import Zeldiablo.Entities.Monstre;
import Zeldiablo.Entities.Player;
import Zeldiablo.Labyrinthe;
import Zeldiablo.StrategieDeplacement.BellmandFord.Algorithme;
import Zeldiablo.StrategieDeplacement.BellmandFord.BellmanFord;
import Zeldiablo.StrategieDeplacement.BellmandFord.GrapheListe;

import java.io.Serializable;
import java.util.Arrays;

public class DeplacementIntelligent implements DeplacementStrategie, Serializable {
    public static final int MAXDEPTH = 3;

    @Override
    public void deplacement(Player joueur,Monstre monstre){
        double[] compare = new double[4];
        for (int i = 0 ; i<4 ; i++) {

            int[] nextCase = Labyrinthe.getSuivant(monstre.getY(),monstre.getX(),Direction.values()[i]);
            compare[i] = algoRecursif(monstre.getLabyrinthe(), joueur, 0, nextCase[1] , nextCase[0] );
        }

        int minint = minInt(compare);

        if (compare[minint] == Double.MAX_VALUE){return;}
        monstre.animationDep(Direction.values()[minint]);
        System.out.println(" ");
    }

    public int minInt(double[] liste){
        int iMin = 0;

        for (int i=1 ; i<liste.length ; i++){
            if (liste[iMin] > liste[i]){
                iMin = i;
            }
        }

        return iMin;
    }

    private double algoRecursif(Labyrinthe laby, Entite joueur, int depth, int x, int y){
        if (x == joueur.getX() && y == joueur.getY()){
            System.out.println("found!");
            return 0;
        }

        if (depth >= MAXDEPTH || !laby.canEntityMoveTo(x,y) ){
            return Double.MAX_VALUE;
        }


        double[] compare = new double[4];
        for (int i = 0 ; i<4 ; i++) {
            int[] nextCase = Labyrinthe.getSuivant(y,x,Direction.values()[i]);
            compare[i] = algoRecursif(laby, joueur, depth + 1, nextCase[1], nextCase[0]);
        }

        double fValue = Arrays.stream(compare).min().getAsDouble();

        if (fValue == Double.MAX_VALUE){return fValue;}
        return fValue + 1;

    }

    /**
     * Déplace l'entité selon la stratégie de déplacement intelligente.
     * Le monstre se déplace vers le joueur en minimisant la distance.
     *
     */
    /*
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
        if (!chemin.isEmpty()) {
            String prochaineCase = chemin.get(0);

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
    */

    public String toString(){return "Intelligent";}
}