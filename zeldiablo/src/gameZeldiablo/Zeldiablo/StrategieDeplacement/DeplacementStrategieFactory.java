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
        return switch (intelligence) {
            case NULLE -> new DeplacementStatique();
            case FAIBLE -> new DeplacementHasard();
            case MOYENNE -> new DeplacementRapprochement();
            case FORTE -> new DeplacementIntelligent();
            default -> throw new IllegalArgumentException("Intelligence non reconnue: " + intelligence);
        };
    }
}
