package gameZeldiablo.Zeldiablo.Items;

import gameZeldiablo.Zeldiablo.Entities.Player;
import gameZeldiablo.Zeldiablo.Inventaire;
import gameZeldiablo.Zeldiablo.VariablesGlobales;

import java.io.File;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Objects;
import java.util.Scanner;

public class Recette extends Item{
    ArrayList<Element> elements;
    ItemsList result;

    /**
     * Constructeur de la classe Item
     *
     */
    public Recette(int i) {
        super(ItemsList.RECETTE, VariablesGlobales.SPRITE_RECETTE, TypeItem.MISC);
        read(i);
    }

    public Recette(){
        super(ItemsList.RECETTE, VariablesGlobales.SPRITE_RECETTE, TypeItem.MISC);
        read(-1);
    }


    //TODO Recettes
    private void read(int i){
        try {
            File[] files = Objects.requireNonNull(new File(VariablesGlobales.RECETTE_FOLDER).listFiles());
            if (i == -1) {
                i = (int) Math.floor(Math.random() * files.length);
            }

            elements = new ArrayList<>();
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
                        elements.add(new Element(Integer.parseInt(tmp[1]), ItemsList.valueOf(tmp[0])));
                    }
                } else {
                    result = ItemsList.valueOf(current);
                }
            }
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
            inv.add(Inventaire.get(this.result));
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
        StringBuilder eList = new StringBuilder("{");
        for (Element e : elements){
            eList.append(e.toString());
        }
        eList.append("}");

        return "Recette{" +
                "\n   elements = " + eList +
                "\n   result = " + result +
                " \n}";
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