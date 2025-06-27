package gameZeldiablo.Zeldiablo;

import com.fasterxml.jackson.annotation.JsonAutoDetect;
import com.fasterxml.jackson.annotation.JsonTypeInfo;
import com.fasterxml.jackson.annotation.PropertyAccessor;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;

import java.io.File;
import java.io.FileInputStream;
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
                ObjectMapper mapper = new ObjectMapper();
//                //AI
//
//                // Configure type info
//                mapper.enableDefaultTyping(ObjectMapper.DefaultTyping.NON_FINAL, JsonTypeInfo.As.PROPERTY);
//
//
//                // Configure deserialization features
//                mapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
//                mapper.configure(DeserializationFeature.FAIL_ON_MISSING_EXTERNAL_TYPE_ID_PROPERTY, false);
//                mapper.enable(SerializationFeature.INDENT_OUTPUT);
//
//                // Configure visibility
//                mapper.setVisibility(PropertyAccessor.FIELD, JsonAutoDetect.Visibility.ANY);
//                mapper.setVisibility(PropertyAccessor.GETTER, JsonAutoDetect.Visibility.ANY);
//                mapper.setVisibility(PropertyAccessor.SETTER, JsonAutoDetect.Visibility.ANY);
//
//
//                //AI

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
