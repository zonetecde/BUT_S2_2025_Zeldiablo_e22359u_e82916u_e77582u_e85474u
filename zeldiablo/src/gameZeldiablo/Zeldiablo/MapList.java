package gameZeldiablo.Zeldiablo;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.util.HashMap;
import java.util.Map;

public class MapList {
    private static final Map<String,Labyrinthe> mapList = new HashMap<>();

    public static Labyrinthe getMap(String name){
        Labyrinthe carte = mapList.get(name);
        try {
            if (carte == null) {
                ObjectInputStream ois = new ObjectInputStream(new FileInputStream("Laby/LabyBin/"+name));
                carte = (Labyrinthe) ois.readObject();
                mapList.put(name, carte);
            }
        }catch (Exception e){
            System.out.println("Donnees corrompues");
            System.out.println(e.getMessage());
            System.exit(1);
        }

        return carte;
    }
}
