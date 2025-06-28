package Zeldiablo.StrategieDeplacement.BellmandFord;


import java.io.Serializable;

/**
 * Arc, Classe brique réprésentant les arcs entre les noeuds
 */
public class Arc implements Serializable {
        private final String dest; // Contient le nom de la destination
        private final double cout; // Contient le coût de l'arc
        private String numLigne;

        /**
         * Constructeur de la classe Arc
         * @param dest Le nom de la destination
         * @param cout Le coût de l'arc
         */
        public Arc(String dest, double cout){
                this.dest = dest;
                this.cout = cout;
        }

        /**
         * Constructeur de la classe Arc utilisant numligne
         * @param dest destination de l'Arc
         * @param cout Cout en deplacement de l'Arc
         * @param numligne Numero de ligne
         */
        public Arc(String dest, double cout, String numligne){
                this.dest = dest;
                this.cout = cout;
                this.numLigne = numligne;
        }

        /**
         * Retourne une représentation en texte d'un arc
         * @return Représentation en texte d'un arc
         */
        public String toString(){
                return dest+"("+cout+")";
        }

        public String getDest() {return this.dest;}

        public String getNumLigne(){return numLigne;}

        public double getCout() {
                return this.cout;
        }
}
