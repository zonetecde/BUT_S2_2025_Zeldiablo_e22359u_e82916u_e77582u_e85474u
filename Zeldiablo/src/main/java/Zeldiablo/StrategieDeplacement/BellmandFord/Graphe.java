package Zeldiablo.StrategieDeplacement.BellmandFord;

import java.util.List;

/**
 * Interface de Graphes contenant des noeuds reliés par des arcs
 */
public interface Graphe {
    List<String> listeNoeuds(); // Renvoie la liste des noeuds du graph
    List<Arc> suivants(String n); // Renvoie la liste des arcs partants du noeud n
}
