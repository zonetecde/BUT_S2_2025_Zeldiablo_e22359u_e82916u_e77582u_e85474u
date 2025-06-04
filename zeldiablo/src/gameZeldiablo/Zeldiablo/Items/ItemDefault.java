package gameZeldiablo.Zeldiablo.Items;

public class ItemDefault extends Item {
    public ItemDefault() {
        super("Placeholder", "", TypeItem.OBJET);
    }

    public ItemDefault(String name) {
        super(name, "", TypeItem.OBJET);
    }
    
    /**
     * Getter pour les dégâts de l'item
     * @return 0.0 car ce n'est pas une arme
     */
    @Override
    public double getDegat() {
        return 0.0;
    }
}
