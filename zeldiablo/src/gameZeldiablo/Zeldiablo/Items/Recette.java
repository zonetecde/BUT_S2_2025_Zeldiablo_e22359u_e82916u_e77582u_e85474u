package gameZeldiablo.Zeldiablo.Items;

import gameZeldiablo.Zeldiablo.Entities.Player;
import gameZeldiablo.Zeldiablo.Inventaire;
import gameZeldiablo.Zeldiablo.VariablesGlobales;

import java.io.File;
import java.io.FileInputStream;
import java.io.ObjectInputStream;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Objects;
import java.util.Scanner;

public class Recette extends Item{
    ArrayList<Element> elements;
    ArrayList<ItemsList> result;

    /**
     * Constructeur de la classe Item
     *
     */
    public Recette(){
        super(ItemsList.RECETTE, VariablesGlobales.SPRITE_RECETTE, TypeItem.MISC);
    }


    public static Recette read(int i){
        try {
            File[] files = Objects.requireNonNull(new File(VariablesGlobales.RECETTE_FOLDER).listFiles());
            if (i == -1) {
                i = (int) Math.floor(Math.random() * files.length);
            }
            Recette out = new Recette();

            if (files[i].getName().startsWith("WR")) {
                out.elements = new ArrayList<>();
                out.result = new ArrayList<>();
                Scanner in = new Scanner(files[i]);

                boolean end = false;
                String current;
                while (in.hasNextLine()) {
                    current = in.nextLine();
                    if (!end) {
                        if (current.equals("//CRAFT")) {
                            end = true;
                        } else {
                            String[] tmp = current.split(";");
                            out.elements.add(new Element(Integer.parseInt(tmp[1]), ItemsList.valueOf(tmp[0])));
                        }
                    } else {
                        String[] tmp = current.split(";");
                        for (int j=0; j<Integer.parseInt(tmp[1]) ; j++) {
                            out.result.add(ItemsList.valueOf(tmp[0]));
                        }
                    }
                }
            }else{
                ObjectInputStream in = new ObjectInputStream(new FileInputStream(files[i]));
                out = (Recette)in.readObject();
            }

            return out;
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    public boolean use(Player p){
        Inventaire inv = p.getInventory();
        if (canUse(inv)){
            for (Element e : elements) {
                inv.remove(e.objet,e.nombre);
            }
            for (ItemsList i : result) {
                inv.add(ItemsList.factory(i));
            }
        }
        return false;
    }

    public boolean canUse(Inventaire inv){
        boolean canIt = true;
        for (Element e : elements){
            canIt = canIt && inv.possedeItem(e.objet,e.nombre);
        }
        return canIt;
    }

    @Override
    public String toString() {
//        StringBuilder eList = new StringBuilder("{");
//        for (Element e : elements){
//            eList.append(e.toString());
//        }
//        eList.append("}");
//
//        return "Recette{" +
//                "\n   elements = " + eList +
//                "\n   result = " + result +
//                " \n}";
        StringBuilder out = new StringBuilder();
        for (ItemsList i : result){
            out.append(i.toString()).append("\n");
        }

        return out.toString();
    }

    static class Element implements Serializable {
        public int nombre;
        public ItemsList objet;

        public Element(int nombre,ItemsList objet){
            this.nombre=nombre;
            this.objet=objet;
        }

        @Override
        public String toString() {
            return objet + "x" + nombre + ";";
        }
    }

    public static void main(String[] args){
        System.out.println(new Recette());
    }
}