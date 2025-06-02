package gameZeldiablo.Zeldiablo.Items;

public class ItemDefault implements Item {
    private String name;

    public ItemDefault() {
        this.name = name;
    }

    @Override
    public String getName() {
        return this.name;
    }



    @Override
    public String toString() {
        return "ItemDefault{" +
                "name='" + name + '\'' +
                '}';
    }
}
