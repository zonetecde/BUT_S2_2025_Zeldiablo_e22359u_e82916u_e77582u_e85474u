package Zeldiablo;

import com.fasterxml.jackson.annotation.JsonAutoDetect;
import com.fasterxml.jackson.annotation.PropertyAccessor;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.File;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

public class MapList {
    private static Map<String,Labyrinthe> mapList = new HashMap<>();

    public static Labyrinthe getMap(String name){
        Labyrinthe carte = mapList.get(name);
        try {
            if (carte == null) {
                ObjectMapper mapper = new ObjectMapper();

                mapper.activateDefaultTyping(mapper.getPolymorphicTypeValidator());
                mapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
                mapper.setVisibility(PropertyAccessor.FIELD, JsonAutoDetect.Visibility.ANY);


                carte = mapper.readValue(new File("Laby/LabyBin/"+name), Labyrinthe.class);
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
                MapList.getMap(f.getName()).stopTickLoop();
            }
        }
        catch (Exception e){
            System.out.println("Donnees corrompues");
            throw new RuntimeException(e);
        }
    }

    //Setters et getters

    public static void setMapList(Map<String,Labyrinthe> m){
        MapList.mapList = m;
    }

    public static Map<String,Labyrinthe> getMapList(){return mapList;}
}
