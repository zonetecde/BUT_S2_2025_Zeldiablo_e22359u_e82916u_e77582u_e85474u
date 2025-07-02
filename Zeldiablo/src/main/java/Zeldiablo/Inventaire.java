package Zeldiablo;

import Zeldiablo.Entities.Player;
import Zeldiablo.Items.Item;
import Zeldiablo.Items.ItemsList;
import Zeldiablo.Items.Recette;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Inventaire {
    private final Map<ItemsList,List<Item>> stock = new HashMap<>();
    private final List<ItemsList> liste = new ArrayList<>();
    private final List<Recette> craft = new ArrayList<>();
    private boolean currentCurseur = true;
    private int curseurCraft = 0;
    private int curseurInv = 0;
    private final Player joueur;

    public Inventaire(Player joueur){
        this.joueur=joueur;
    }

    public int size(){
        return liste.size();
    }

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
                    stock.get(i).removeFirst();
                }
            }else{
                stock.remove(i);
                liste.remove(i);
            }
            if (curseurInv >= liste.size()-1){
                curseurInv =0;
            }
        }
    }

    public void remove(ItemsList i){remove(i,1);}

    public void use(ItemsList i){
        if (possedeItem(i)){
            if (stock.get(i).getFirst().use(joueur)){
                remove(i);
            }
        }
    }

    public List<Item> get(ItemsList i){
        if (possedeItem(i)){
            return stock.get(i);
        }
        else return null;
    }

    public boolean isEmpty(){
        return stock.isEmpty();
    }

    //Curseur
    public void moveCurseur(int nombre){
        if (currentCurseur) {
            if (curseurInv + nombre < liste.size() && curseurInv + nombre >= 0) {
                curseurInv += nombre;
            }else{
                currentCurseur= false;
            }
        }else {
            if (curseurCraft + nombre < craft.size() && curseurCraft + nombre >= 0) {
                curseurCraft += nombre;
            }else{
                currentCurseur= true;
            }
        }
    }

    public boolean getCurrentCurseur(){return currentCurseur;}

    public ItemsList getCurseur(){
        if (currentCurseur) {
            return liste.get(curseurInv);}
        else{
            return craft.get(curseurCraft).getName();}
    }

    public int getCurseurInt(){
        if (currentCurseur) {
            return curseurInv;}
        else{
            return curseurCraft;}
    }

    public Recette getCurseurCraft(){return craft.get(curseurCraft);}

    public List<Recette> getCraft(){return craft;}

    public int getNumber(ItemsList i){
        return stock.get(i).size();
    }

    public List<ItemsList> getListe(){return liste;}




}
