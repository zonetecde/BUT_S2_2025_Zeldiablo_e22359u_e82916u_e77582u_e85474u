package gameZeldiablo.Zeldiablo.StrategieDeplacement;

import gameZeldiablo.Zeldiablo.Entities.Intelligence;

public class DeplacementStrategieFactory {

    /**
     * Retourne la stratégie de déplacement en fonction de l'intelligence du monstre.
     * 
     * @param intelligence L'intelligence du monstre qui détermine la stratégie de déplacement
     * @return Le DeplacementStrategie correspondant à l'intelligence donnée
     * @throws IllegalArgumentException Si l'intelligence n'est pas reconnue
     */
    public static DeplacementStrategie creerStrategie(Intelligence intelligence) {
        switch(intelligence) {
            case NULLE:
                return new DeplacementStatique();
            case FAIBLE:
                return new DeplacementHasard();
            case MOYENNE:
                return new DeplacementRapprochement();
            case FORTE:
                return new DeplacementIntelligent();
            default:
                throw new IllegalArgumentException("Intelligence non reconnue: " + intelligence);
        }
    }
}
