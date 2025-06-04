package gameZeldiablo.Zeldiablo.Items;

/**
 * Classe abstraite représentant une arme
 */
public abstract class Arme extends Item {
    
    protected double degat;
    
    /**
     * Constructeur de la classe Arme
     * @param name Nom de l'arme
     * @param img Chemin de l'image de l'arme
     * @param degat Dégâts infligés par l'arme
     */
    public Arme(String name, String img, double degat) {
        super(name, img, TypeItem.ARME);
        this.degat = degat;
    }
    
    /**
     * Getter pour les dégâts de l'arme
     * @return Les dégâts de l'arme
     */
    @Override
    public double getDegat() {
        return this.degat;
    }

    public String toString() {
        return this.getName() + " (" + this.getDegat() + " pdg)";
    }
}
