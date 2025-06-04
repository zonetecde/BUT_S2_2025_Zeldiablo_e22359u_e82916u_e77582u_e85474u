package gameZeldiablo.Zeldiablo.StrategieDeplacement.BellmandFord;

/**
 * Algorithme de BellmanFord
 */
public class BellmanFord  implements Algorithme {
    /**
     * Algorithme de Bellman-ford (Point Fixe)
     * @param g Graphe étudié
     * @param depart Noeud de départ
     * @return Objet Valeurs
     */
    public Valeurs resoudre(Graphe g, String depart){
        Valeurs v = new Valeurs();

        // Init les valeurs de départ (Double.MaxValue pour tout les sommets
        // sauf le départ) et set les parents/enfants de chaque noeud correctement
        for(String noeud : g.listeNoeuds()){
            // Si c'est le noeud de départ
            if(noeud.equals(depart)) {
                v.setValeur(noeud, 0);
                v.setParent(noeud, null); // On définit explicitement que le parent du départ est null
            }
            else {
                v.setValeur(noeud, Double.MAX_VALUE);
            }
        }

        // Ici on a donc nos valeurs pour chaque sommet,
        // mtn on procède à l'itération jusqu'à trouver le point fixe

        // Itère jusqu'à trouver le point fixe
        int affectionRealise;
        do {
            affectionRealise = 0;
            for(String noeud : g.listeNoeuds()){
                // Si la valeur du noeud est inf alors il y a pas de chemin vers lui pour l'instant
                if (v.getValeur(noeud) == Double.MAX_VALUE) continue;
                
                              
                // récupère tout les enfants du parent (parmis eux se trouve le noeud)
                for(Arc suivant : g.suivants(noeud)){
                    // recup la destination et le cout de l'arc
                    String destination = suivant.getDest();
                    double nouveauCout = v.getValeur(noeud) + suivant.getCout();

                    // si il est meilleur que la valeur du parent
                    if(nouveauCout < v.getValeur(destination)) {
                        v.setValeur(destination, nouveauCout);
                        v.setParent(destination, noeud);
                        affectionRealise++;
                    }
                }
            }

        } while(affectionRealise > 0);

        return v;
    }
}
