package gameZeldiablo.Zeldiablo.Entities;


import gameZeldiablo.Zeldiablo.Items.Item;
import gameZeldiablo.Zeldiablo.Items.TypeItem;
import gameZeldiablo.Zeldiablo.VariablesGlobales;

import java.util.ArrayList;

public class Player extends Entite {
    private Inventaire inventaire;
    private boolean aGagne = false;
    private int sprite = 0;

    /**
     * Constructeur
     * @param dx posx
     * @param dy posy
     * @param maxHp hp Max
     * @param degat attaque du joueur
     */
    public Player(int dx, int dy, double maxHp, double degat) {
        super(dx, dy, maxHp, degat, VariablesGlobales.SPRITE_JOUEUR[0]);
        this.inventaire = new Inventaire();
    }

    /**
     * Setter du sprite
     * @param i nombre associé au sprite
     */
    public void setSpriteJoueur(int i) {
        this.sprite = i;
        this.getSprite().setImg(VariablesGlobales.SPRITE_JOUEUR[this.sprite]);
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
            inventaire.vider();
        }
        super.setEnVie(b);
    }    
    
    /**
     * Setter de l'inventaire du joueur (entre les niveaux par exemple)
     *
     * @param inventory L'inventaire du joueur, une liste d'objets
     */
    public void setInventory(ArrayList<Item> inventory) {
        this.inventaire.setItems(inventory);
    }

    /**
     * Ajoute un item à l'inventaire du joueur
     * @param item L'item à ajouter
     */
    public void ajouterItem(Item item) {
        inventaire.ajouterItem(item);
    }

    /**
     * Retourne l'objet Inventaire du joueur
     * @return L'objet Inventaire
     */
    public Inventaire getInventaireObjet() {
        return inventaire;
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
            if(this.getInventaireObjet().size() == 0 || this.getInventaireObjet().get(VariablesGlobales.curseur).getType() != TypeItem.ARME) {
                // Si l'inventaire est vide, on inflige les dégâts de base
                cible.prendreDegat(this.getDegat());
                return;
            }

            // Le premier paramètre des armes est les dégâts
            cible.prendreDegat(this.getInventaireObjet().get(VariablesGlobales.curseur).getDegat());
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
     * Clone le joueur
     * @return nouveau joueur
     */
    public Player clone() {
        Player clone = new Player(this.getX(), this.getY(), this.getMaxHp(), this.getDegat());
        clone.setEnVie(super.getEnVie());
        clone.setHp(this.getHp());
        clone.setInventory(new ArrayList<>(this.inventaire.getItems()));
        return clone;
    }
}