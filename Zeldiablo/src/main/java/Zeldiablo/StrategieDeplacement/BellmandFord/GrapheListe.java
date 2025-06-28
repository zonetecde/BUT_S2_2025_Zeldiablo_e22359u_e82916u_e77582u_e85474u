package Zeldiablo.StrategieDeplacement.BellmandFord;

import Zeldiablo.Cases.Case;
import Zeldiablo.Labyrinthe;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * Grapheliste implemente Graphe en utilisant des ArrayList
 */
public class GrapheListe implements Graphe, Serializable {
    private final ArrayList<String> noeuds; // Représente la liste des noeuds du graphe
    private final ArrayList<Arcs> adjacence; // Représente la liste des arcs partant de chaque noeud du graphe

    /**
     * Constructeur de la classe GrapheListe
     * Initialise la liste des noeuds et la liste des arcs
     */
    public GrapheListe(){
        this.noeuds = new ArrayList<>();
        this.adjacence = new ArrayList<>();
    }

    public GrapheListe(Labyrinthe lab) {
        this.noeuds = new ArrayList<>();
        this.adjacence = new ArrayList<>();

        // Parcourt le labyrinthe pour ajouter les arcs
        for (int y = 0; y < lab.getHauteur(); y++) {
            for (int x = 0; x < lab.getLongueur(); x++) {
                Case c = lab.getCase(y, x);

                // Ajoute les arcs vers les voisins
                // pour le haut et si la case du haut est walkable
                if (y > 0 && (lab.getCase(y - 1, x).getIsWalkable())) {
                    ajouterArc(c.toString(), lab.getCase(y - 1, x).toString(), 1.0);
                }
                // pour le bas et si la case du bas est walkable
                if (y < lab.getHauteur() - 1 && (lab.getCase(y + 1, x).getIsWalkable())) {
                    ajouterArc(c.toString(), lab.getCase(y + 1, x).toString(), 1.0);
                }
                // pour la gauche et si la case de gauche est walkable
                if (x > 0 && (lab.getCase(y, x - 1).getIsWalkable())) {
                    ajouterArc(c.toString(), lab.getCase(y, x - 1).toString(), 1.0);
                }
                // pour la droite et si la case de droite est walkable
                if (x < lab.getLongueur() - 1 && (lab.getCase(y, x + 1).getIsWalkable())) {
                    ajouterArc(c.toString(), lab.getCase(y, x + 1).toString(), 1.0);
                }

            }
        }
    }

    /**
     * Retourne l'indice du noeud n dans la liste noeud
     * S'il n'existe pas, l'ajoute à la liste et renvoie son indice
     * @param n Le nom du noeud
     * @return L'indice du noeud s'il existe, sinon -1.
     */
    private int getIndice(String n){
        int indice = this.noeuds.indexOf(n);
        if(indice == -1){
            return ajouterNoeud(n);
        }
        return indice;
    }

    /**
     * Renvoie la liste des noeuds du graphe
     * @return L'indice du noeud s'il existe, sinon -1.
     */
    @Override
    public List<String> listeNoeuds() {
        return noeuds;
    }

    /**
     * Renvoie la liste des arcs partant d'un noeud
     * Crée le noeud s'il n'existe pas
     * @param n Le noeud dont on veut les arcs partants
     */
    @Override
    public List<Arc> suivants(String n) {
        // Récupère l'indice du noeud
        int indiceNoeud = getIndice(n);
     
        // Renvoie ses arcs
        return this.adjacence.get(indiceNoeud).getArcs();  
    }

    /**
     * Ajoute un noeud au graphe
     * @param n Le nom du noeud à ajouter
     * @return L'indice du noeud qui vient d'être ajouté
     */
    public int ajouterNoeud(String n){
        this.noeuds.add(n);
        this.adjacence.add(new Arcs());
        return this.noeuds.indexOf(n);
    }

    /**
     * Ajoute un arc au graphe
     * @param depart Le noeud de départ
     * @param destination Le noeud de destination
     * @param cout Le coût de l'arc
     * @param ligne num de la ligne
     */
    public void ajouterArc(String depart, String destination, double cout, String ligne){
        // Vérifie que la destination existe (la méthode la crée si elle n'existe pas)
        getIndice(destination);

        // Récupère l'indice du noeud de départ
        int indiceDepart = getIndice(depart);

        // Ajoute l'arc
        adjacence.get(indiceDepart).ajouterArc(new Arc(destination,cout,ligne));
    }

    /**
     * Version de ajouterArc sans l'information de la ligne
     */
    public void ajouterArc(String depart, String destination, double cout){
        ajouterArc(depart,destination,cout,null);
    }

    /**
     * Retourne une représentation en texte du graphe
     * @return Représentation en texte du graphe
     */
    public String toString(){
        StringBuilder tmp= new StringBuilder();
        for (int i=0; i<this.noeuds.size(); i++){
            tmp.append(this.noeuds.get(i)).append(" -> ").append(this.adjacence.get(i)).append("\n");
        }
        return tmp.toString();
    }

    /**
    *   Methode de Bellman-Ford renvoyant les distances minimales des points par rapport au dépar
    * @param noeud Nom du noeud de départ
    * @return ArrayList des distances des noeuds par rapport au noeud de départ
    *
     */
    public ArrayList<Double> pointFixe(String noeud) {
        //Valeurs precedentes
        ArrayList<Double> pointFixeBack = new ArrayList<>();
        //Valeurs Actuelles
        ArrayList<Double> pointFixe = new ArrayList<>();
        //Valeurs des nodes suivants
        List<Arc> next;
        //Valeur du node etudié
        double value;

        //Creation de la table de base avec toutes les valeurs à +infini sauf le node de départ
        for (int i=0;i<this.noeuds.size();i++){
            pointFixe.add(Double.MAX_VALUE);
        }
        pointFixe.set(this.getIndice(noeud),0.0);

        //Boucle Principale
        while (!pointFixe.equals(pointFixeBack)){
            //Mise en archive
            pointFixeBack=(ArrayList<Double>) pointFixe.clone();

            //POint de départ étudié
            for (int i=0;i<pointFixe.size();i++){
                //Recuparation des arcs
                next=this.suivants(this.noeuds.get(i));

                //Boucle de comparaison
                for (Arc arc : next) {
                    value = arc.getCout();
                    int iStudied = this.noeuds.indexOf(arc.getDest());
                    if (pointFixe.get(i) + value < pointFixeBack.get(iStudied)) {
                        pointFixe.set(iStudied, pointFixe.get(i) + value);
                    }
                }
            }
        }

        return pointFixe;
    }

    /**
     * Adaptation de dijksta pour trouver tous les points
     * @param b Nom du Noeud de départ
     * @return le nom des Noeud par ordre de proximité
     */
    public String dijkstra(String b){
        return this.dijkstra(b,b);
    }


    /**
     * Dijkstra renvoie la suite de Noeuds la plus opti
     *
     * @param b Noeud de Debut
     * @param e Noeud de fin
     * @return Chemin le plus court entre b et e
     */
    public String dijkstra(String b,String e) {
        //Variable à retourner
        StringBuilder output= new StringBuilder(b);
        //Node étudié actuellement
        String cur=b;

        //Boucle tant que pas arrivé au node de fin
        while (!cur.equals(e) || output.length()<2){
            //Informations sur le node actuel
            List<Arc> next = this.suivants(cur);
            int tmp=0;

            //Boucle renvoyant tmp l'indice du node le plus proche dans next
            for (int i=0;i<next.size();i++){
                if (next.get(tmp).getCout()>next.get(i).getCout() && !output.toString().contains(next.get(i).getDest())){
                    tmp=i;
                }
            }

            //Ajout du node trouvé à la variable
            output.append("->").append(next.get(tmp).getDest());

            //Actualisation du node actuel
            cur=next.get(tmp).getDest();
        }
        return output.toString();
    }


}
