package gameZeldiablo.Zeldiablo.Items;

import gameZeldiablo.Zeldiablo.Sprite;

public abstract class Item extends Sprite {
    public Item(String s) {
        super(s);
    }

    public abstract String getName();

}
