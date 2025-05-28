package gameZeldiablo.Zeldiablo;

import java.util.ArrayList;

public class Utilities {

    /**
     * Génère un nombre aléatoire entre min (inclus) et max (exclus).
     *
     * @param min la valeur minimale (inclus)
     * @param max la valeur maximale (exclus)
     * @return un entier aléatoire entre min et max
     */
    public static int getRandomNumber(int min, int max) {
        return (int) ((Math.random() * (max - min)) + min);
    }
/**
     * Vérifie si deux coordonnées (représentées par des tableaux d'entiers) sont égales.
     *
     * @param c1 le premier tableau de coordonnées
     * @param c2 le deuxième tableau de coordonnées
     * @return true si les coordonnées sont égales, false sinon
     */
    public static boolean areTwoCoordinatesEqual(int[] c1, int[] c2){
        return c1[0] == c2[0] && c1[1] == c2[1];
    }

    /**
     * Vérifie si un tableau de coordonnées contient une certaine coordonnée.
     *
     * @param array le tableau de coordonnées
     * @param coo la coordonnée à vérifier
     * @return true si la coordonnée est présente dans le tableau, false sinon
     */

    public static boolean isThisCoordinateInArrayOfCoordinates(ArrayList<int[]> array, int[] coo){
        for (int i = 0; i < array.size(); i++){
            if(areTwoCoordinatesEqual(array.get(i), coo)) return true;
        }
        return false;
    }
}
