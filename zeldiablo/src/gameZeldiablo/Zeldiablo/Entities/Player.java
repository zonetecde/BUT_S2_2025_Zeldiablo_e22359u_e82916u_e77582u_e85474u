package gameZeldiablo.Zeldiablo.Entities;


import gameZeldiablo.Zeldiablo.Cases.Case;
import gameZeldiablo.Zeldiablo.Items.Item;
import gameZeldiablo.Zeldiablo.Items.TypeItem;
import gameZeldiablo.Zeldiablo.Labyrinthe;
import gameZeldiablo.Zeldiablo.Prompt;
import gameZeldiablo.Zeldiablo.VariablesGlobales;
import javafx.scene.control.Label;

import java.util.ArrayList;

public class Player extends Entite {
    private ArrayList<Item> inventory;
    private Labyrinthe labyrinthe;
    boolean aGagne = false;
    int sprite = 0;

    public boolean menuOuvert = false;
    public boolean curseurStart = true;
    public int curseur = 0;

    /**
     * Constructeur
     * @param dx posx
     * @param dy posy
     * @param maxHp hp Max
     * @param degat attaque du joueur
     */
    public Player(int dx, int dy, double maxHp, double degat) {
        super(dx, dy, maxHp, degat, VariablesGlobales.SPRITE_JOUEUR[0]);
        this.inventory = new ArrayList<>();
    }

    /**
     * Ramasse un objet si le joueur est sur une case contenant un objet
     *
     */
    public void ramasserItem() {
        int x = this.getX();
        int y = this.getY();
        Case caseCourante = getLabyrinthe().getCase(y, x);
        // Vérifie si la case contient un objet
        if (caseCourante.hasItem()) {
            // Ajoute l'objet à l'inventaire du joueur et retire l'objet de la case
            Item item = caseCourante.getItem();

            getInventory().add(item);
            String nomItem = item.getName();

            // Génère avec l'IA le texte que le joueur va dire
            Prompt.askGptForMsgWhenPickingItem(nomItem + " " + item.getSpriteName(), this::setMsgToSay);

            caseCourante.removeItem();
        }
    }

    public void reset(){
        this.setInventory(new ArrayList<>());
        this.setHp(this.getMaxHp());
    }

    /**
     * Setter du sprite
     * @param i nombre associé au sprite
     */
    public void setSpriteJoueur(int i) {
        this.sprite = i;
        this.setSprite(VariablesGlobales.SPRITE_JOUEUR[this.sprite]);
    }

    /**
     * getter du sprite
     * @return num du sprite
     */
    public int getSpriteJoueur(){return sprite;}

    /**
     * Dit si le joueur est en vie ou non
     * Si le joueur meurt, son inventaire est vidé
     *
     * @param b true si le joueur est en vie, false sinon
     */
    public void setEnVie(boolean b) {
        if (!b) {
            inventory.clear();
        }

        super.setEnVie(b);
    }

    /**
     * Setter de l'inventaire du joueur (entre les niveaux par exemple)
     *
     * @param inventory L'inventaire du joueur, une liste d'objets
     */
    public void setInventory(ArrayList<Item> inventory) {
        this.inventory = inventory;
    }

    /**
     * Retourne l'inventaire du joueur
     *
     * @return L'inventaire du joueur, une liste d'items
     */
    public ArrayList<Item> getInventory() {
        return inventory;
    }

    /**
     * getter des hp
     * @return double hp
     */
    public double getVie() {
        return this.getHp();

    }
    
    /**
     * Inflige des dégâts à une cible en fonction de l'item équipé
     * @param cible L'entité cible qui subit les dégâts
     */
    @Override
    public void mettreDegat(Entite cible) {
        if (cible != null && cible.getEnVie()) {
            // regarde l'item actuellement équipé
            if(getInventory().isEmpty() || getInventory().get(curseur).getType() != TypeItem.ARME) {
                // Si l'inventaire est vide, on inflige les dégâts de base
                cible.prendreDegat(this.getDegat());
                return;
            }

            // Le premier paramètre des armes est les dégâts
            cible.prendreDegat((double)getInventory().get(curseur).getDegat());
        }
    }

    /**
     * Retourne si le joueur a gagné ou non
     *
     * @return true si le joueur a gagné, false sinon
     */
    public boolean aGagne() {
        return aGagne;
    }

    /**
     * Set si le joueur a gagné ou non
     *
     * @param b true si le joueur a gagné, false sinon
     */
    public void setaGagne(boolean b) {
        this.aGagne = b;
    }

    /**
     * Regarde si le joueur possède un item dans son inventaire
     *
     * @return true si l'item est trouvé, false sinon
     */
    public boolean possedeItem(String nomItem) {
        for (Item item : inventory) {
            if (item.getName().equals(nomItem)) {
                return true;
            }
        }
        return false;
    }

   public Labyrinthe getLabyrinthe(){
        return this.labyrinthe;
   }

   public void setLabyrinthe(Labyrinthe l){
        this.labyrinthe = l;
   }

    /**
     * Clone le joueur
     * @return nouveau joueur
     */
    public Player clone() {
        Player clone = new Player(this.getX(), this.getY(), this.getMaxHp(), this.getDegat());
        clone.setEnVie(super.getEnVie());
        clone.setHp(this.getHp());
        clone.setInventory(new ArrayList<>(this.inventory));
        return clone;
    }
}