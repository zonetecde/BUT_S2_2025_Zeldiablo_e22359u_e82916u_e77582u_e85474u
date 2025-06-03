package gameZeldiablo.Zeldiablo.Items;

public class ItemDefault extends Item {
    private String name;

    public ItemDefault() {
        super(null);
        this.name = "Item de test";
    }

    public ItemDefault(String name) {
        super(null);
        this.name = name;
    }

    @Override
    public String getName() {
        return this.name;
    }

    @Override
    public String toString() {
        return "Item {" + name + "}";
    }
}
