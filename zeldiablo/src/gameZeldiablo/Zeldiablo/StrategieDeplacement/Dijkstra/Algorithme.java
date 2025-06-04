package gameZeldiablo.Zeldiablo.StrategieDeplacement.Dijkstra;

/**
*Interface de Strategie pour utiliser des algorithmes sans supliquer de codes
 */
public interface Algorithme {
    /*
    *  Methode abstraite
    * utiliser pour implementer un algorithme
     */
    public abstract Valeurs resoudre(Graphe g, String depart);
}
