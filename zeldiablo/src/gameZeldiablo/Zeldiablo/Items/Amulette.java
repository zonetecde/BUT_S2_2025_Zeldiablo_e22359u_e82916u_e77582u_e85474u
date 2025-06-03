package gameZeldiablo.Zeldiablo.Items;

public class Amulette implements Item {
    private String name = "Amulette";

    public Amulette() {;
    }

    @Override
    public String getName() {
        return this.name;
    }

    @Override
    public String toString() {
        return "Amulette";
    }
}
