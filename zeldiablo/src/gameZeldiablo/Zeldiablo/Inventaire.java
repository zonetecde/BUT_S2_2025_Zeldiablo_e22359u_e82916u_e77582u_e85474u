package gameZeldiablo.Zeldiablo;

import gameZeldiablo.Zeldiablo.Entities.Player;
import gameZeldiablo.Zeldiablo.Items.*;

import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Inventaire {
    public  static Map<ItemsList,Item> cacheItem = new HashMap<>();
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
        if (curseur >= liste.size()-1){
            curseur=0;
        }
    }

    public boolean possedeItem(ItemsList i){
        List<Item> item =stock.get(i);
        return (item != null && !item.isEmpty());
    }

    public void add(Item i){
        if (!stock.containsKey(i.getName())){
            createItem(i);
        }else{
            stock.get(i.getName()).add(i);
        }
    }

    public void remove(ItemsList i){
        List<Item> tmp = stock.get(i);
        if (tmp!=null && !tmp.isEmpty()){
            if (tmp.size()!=1) {
                stock.get(i).removeLast();
            }else{
                stock.remove(i);
                liste.remove(i);
                System.out.println("feur");
            }
        }
    }

    public void use(ItemsList i){
        if (possedeItem(i)){
            if (factory(i).use(joueur)){
                remove(i);
            }
        }
    }

    public boolean isEmpty(){
        return stock.isEmpty();
    }

    //Armes
    public double getMaxDegats(){
        double max= factory(liste.getFirst()).getDegat();
        for (ItemsList i : liste){
            double tmp = factory(i).getDegat();
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
    public static Item get(ItemsList i){return factory(i);}

    private static Item factory(ItemsList i){
        try {
            return switch (i){
                case AMULETTE -> CacheManage(i,Amulette.class.getConstructor());
                case BATON -> CacheManage(i,Baton.class.getConstructor());
                case BOMBE -> CacheManage(i,Bombe.class.getConstructor());
                case EPEE -> CacheManage(i,Epee.class.getConstructor());
                case FOOD -> CacheManage(i,Food.class.getConstructor());
                case HACHE -> CacheManage(i,Hache.class.getConstructor());
            };
        } catch (NoSuchMethodException e) {
            throw new RuntimeException(e);
        }
    }

    private static Item CacheManage(ItemsList i ,Constructor<?> c){
        if (cacheItem.containsKey(i)){
            return cacheItem.get(i);
        }
        try {
            return (Item)c.newInstance();
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}
