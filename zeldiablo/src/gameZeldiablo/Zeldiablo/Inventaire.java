package gameZeldiablo.Zeldiablo;

import gameZeldiablo.Zeldiablo.Entities.Player;
import gameZeldiablo.Zeldiablo.Items.*;

import java.lang.reflect.Constructor;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Inventaire {
    private final Map<ItemsList,List<Item>> stock = new HashMap<>();
    private List<ItemsList> liste = new ArrayList<>();
    private int curseur = 0;
    private final Player joueur;

    public Inventaire(Player joueur){
        this.joueur=joueur;
    }

    public int size(){if (liste!=null) return liste.size(); else return 0;}

    public void createItem(Item i){
        List<Item> tmp = new ArrayList<>();
        tmp.add(i);
        stock.put(i.getName(), tmp );
        liste.add(i.getName());
    }

    public boolean possedeItem(ItemsList i){
        List<Item> item =stock.get(i);
        return (item != null && !item.isEmpty());
    }

    public boolean possedeItem(ItemsList i,int nb){
        List<Item> item =stock.get(i);
        return (item != null && item.size()>nb-1);
    }

    public void add(Item i){
        if (!stock.containsKey(i.getName())){
            createItem(i);
        }else{
            stock.get(i.getName()).add(i);
        }
    }

    public void remove(ItemsList i, int nb){
        List<Item> tmp = stock.get(i);
        if (tmp!=null && tmp.size()>=nb){
            if (tmp.size()!=nb) {
                for (int j=0;j<nb;j++) {
                    stock.get(i).removeLast();
                }
            }else{
                stock.remove(i);
                liste.remove(i);
                System.out.println("feur");
            }
            if (curseur >= liste.size()-1){
                curseur=0;
            }
        }
    }

    public void remove(ItemsList i){remove(i,1);}

    public void use(ItemsList i){
        if (possedeItem(i)){
            if (get(i).use(joueur)){
                remove(i);
            }
        }
    }

    public boolean isEmpty(){
        return stock.isEmpty();
    }

    //Armes
    public double getMaxDegats(){
        double max= get(liste.getFirst()).getDegat();
        for (ItemsList i : liste){
            double tmp = get(i).getDegat();
            if (tmp>max){max=tmp;}
        }
        return max;
    }


    //Curseur
    public void movecurseur(int nombre){
        if (curseur + nombre< liste.size() && curseur + nombre >= 0){
                curseur+=nombre;
        }
    }

    public ItemsList getCurseur(){return liste.get(curseur);}

    public int getCurseurInt(){return curseur;}

    public int getNumber(ItemsList i){
        return stock.get(i).size();
    }

    public List<ItemsList> getListe(){return liste;}

    //Factory
    public static Item get(ItemsList i){return ItemsList.factory(i);}




}
