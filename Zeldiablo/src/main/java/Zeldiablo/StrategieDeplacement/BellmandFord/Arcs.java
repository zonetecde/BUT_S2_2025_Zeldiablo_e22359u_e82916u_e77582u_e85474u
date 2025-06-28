package Zeldiablo.StrategieDeplacement.BellmandFord;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * Classe Arcs gerant une liste d'objets Arc
 */
public class Arcs implements Serializable {
    private final List<Arc> arcs; // Contient la liste des arcs

    /**
     * Constructeur de la classe Arcs
     * Initialise la liste des arcs
     */
    public Arcs(){
        this.arcs = new ArrayList<>();
    }

    /**
     * Ajoute un arc à la liste des arcs
     * @param a L'arc à ajouter
     * @throws IllegalArgumentException si l'arc est null
     */
    public void ajouterArc(Arc a){
        if(a == null) throw new IllegalArgumentException("L'arc donné ne peut pas être null");
        this.arcs.add(a);
    }

    /**
     * Récupère la liste des arcs
     * @return La liste des arcs
     */
    public List<Arc> getArcs(){
        return this.arcs;
    }

    /**
     * Retourne une représentation en texte des arcs
     * @return Représentation en texte des arcs
     */
    public String toString(){
        StringBuilder tmp= new StringBuilder();
        for (Arc arc : this.arcs) {
            tmp.append(arc.toString()).append(" ");
        }
        return tmp.toString();
    }
}
