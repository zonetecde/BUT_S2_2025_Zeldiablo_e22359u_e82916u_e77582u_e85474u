package gameZeldiablo.Zeldiablo.Entities;

import gameZeldiablo.Zeldiablo.Items.Item;

import java.util.ArrayList;

/**
 * Classe représentant l'inventaire d'un joueur
 */
public class Inventaire {
    private ArrayList<Item> items;
    
    /**
     * Constructeur de l'inventaire
     */
    public Inventaire() {
        this.items = new ArrayList<>();
    }
    
    /**
     * Constructeur de l'inventaire avec une liste d'items existante (pour le clone)
     * @param items Liste d'items à copier dans l'inventaire
     */
    public Inventaire(ArrayList<Item> items) {
        this.items = new ArrayList<>(items);
    }
    
    /**
     * Ajoute un item à l'inventaire
     * @param item L'item à ajouter
     */
    public void ajouterItem(Item item) {
        this.items.add(item);
    }
    
    /**
     * Retourne la liste des items de l'inventaire
     * @return ArrayList des items
     */
    public ArrayList<Item> getItems() {
        return items;
    }
    
    /**
     * Remplace la liste des items de l'inventaire
     * @param items Nouvelle liste d'items
     */
    public void setItems(ArrayList<Item> items) {
        this.items = new ArrayList<>(items);
    }
    
    /**
     * Retourne la taille de l'inventaire
     * @return Le nombre d'items dans l'inventaire
     */
    public int size() {
        return items.size();
    }
    
    /**
     * Retourne l'item à l'index spécifié
     * @param index L'index de l'item
     * @return L'item à l'index donné
     */
    public Item get(int index) {
        return items.get(index);
    }

    /**
     * Vide l'inventaire
     */
    public void vider() {
        items.clear();
    }
    
    /**
     * Vérifie si le joueur possède un item avec le nom donné
     * @param nomItem Le nom de l'item à rechercher
     * @return true si l'item est trouvé, false sinon
     */
    public boolean possedeItem(String nomItem) {
        for (Item item : items) {
            if (item.getName().equals(nomItem)) {
                return true;
            }
        }
        return false;
    }
}
