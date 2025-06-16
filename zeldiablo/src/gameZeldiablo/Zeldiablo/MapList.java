package gameZeldiablo.Zeldiablo;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

public class MapList {
    private static Map<String,Labyrinthe> mapList = new HashMap<>();

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

    public static void initialisation(){
        try {
            File folder = new File("Laby/LabyBin");
            for (File f : Objects.requireNonNull(folder.listFiles())) {
                MapList.getMap(f.getName());
            }
        }
        catch (Exception e){
            System.out.println("Donnees corrompues");
            System.out.println(e.getMessage());
            System.exit(1);
        }
    }

    //Setters et getters

    public static void setMapList(Map<String,Labyrinthe> m){
        MapList.mapList = m;
    }

    public static Map<String,Labyrinthe> getMapList(){return mapList;}
}
