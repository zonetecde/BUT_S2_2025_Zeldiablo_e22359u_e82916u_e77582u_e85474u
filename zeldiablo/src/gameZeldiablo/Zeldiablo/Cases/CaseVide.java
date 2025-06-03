package gameZeldiablo.Zeldiablo.Cases;

import gameZeldiablo.Zeldiablo.Entities.Entite;
import gameZeldiablo.Zeldiablo.Items.Item;
import gameZeldiablo.Zeldiablo.VariablesGlobales;



public class CaseVide extends Case {
    private Item item;

    public CaseVide(int x, int y){
        super(x, y, true, VariablesGlobales.SPRITE_CASE_VIDE);
    }
    @Override
    public void onStepOn(Entite entite) {
        return;
    }

    /**
     * Méthode pour ajouter un item à la case
     * @param item L'item à ajouter
     */
    @Override
    public void addItem(Item item) {
        this.item = item;
    }

    /**
     * Vérifie si la case contient un objet
     * @return true si la case contient un objet, false sinon
     */
    @Override
    public boolean hasItem() {
        return item != null;
    }

    /**
     * Méthode pour retirer un item de la case
     */
    @Override
    public void removeItem(){
        this.item = null;
    }

    /**
     * Retourne l'objet contenu dans la case
     * @return L'objet de la case
     */
    public Item getItem() {
        return item;
    }
}


