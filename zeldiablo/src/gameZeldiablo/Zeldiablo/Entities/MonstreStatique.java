package gameZeldiablo.Zeldiablo.Entities;

/**
 * Monstre idiot et statique
 */
public class MonstreStatique extends Monstre {

    /**
     * Constructeur
     * @param dx posx
     * @param dy posy
     */
    public MonstreStatique(int dx, int dy) {
        super(dx, dy, Intelligence.NULLE);
    }
}
