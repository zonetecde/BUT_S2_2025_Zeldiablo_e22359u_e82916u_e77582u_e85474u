package gameZeldiablo.Zeldiablo.Entities;


import gameZeldiablo.Zeldiablo.Cases.Case;
import gameZeldiablo.Zeldiablo.Direction;
import gameZeldiablo.Zeldiablo.Items.Item;
import gameZeldiablo.Zeldiablo.Items.TypeItem;
import gameZeldiablo.Zeldiablo.Labyrinthe;
import gameZeldiablo.Zeldiablo.Prompt;
import gameZeldiablo.Zeldiablo.VariablesGlobales;

import java.util.ArrayList;

public class Player extends Entite {
    private ArrayList<Item> inventory;
    boolean aGagne = false;
    int sprite = 0;

    public boolean menuOuvert = false;
    public boolean curseurStart = true;
    public int curseurLog = 0;
    public int curseur = 0;
    public boolean currentlyMoving = false;

    /**
     * Constructeur
     * @param dx posx
     * @param dy posy
     * @param maxHp hp Max
     * @param degat attaque du joueur
     */
    public Player(int dx, int dy, double maxHp, double degat) {
        super(dx, dy, maxHp, degat, VariablesGlobales.SPRITE_JOUEUR[0],null);
        this.inventory = new ArrayList<>();
    }

    /**
     * Ramasse un objet si le joueur est sur une case contenant un objet
     *
     */
    public void ramasserItem() {
        int x = (int)this.getX();
        int y = (int)this.getY();
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
     * Inflige des dégâts à une cible en fonction de l'item équipé
     * @param cible L'entité cible qui subit les dégâts
     */
    @Override
    public void infligerDegats(Entite cible) {
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
     * Effectue l'attaque du joueur sur les monstres à proximité.
     * Cette méthode change l'état visuel du joueur et des monstres touchés,
     */

    public void attaque() {
        // Crée une copie de la liste pour éviter les problèmes de modification pendant l'itération
        ArrayList<Entite> monstresACheck = getLabyrinthe().getMonstres();

        // Pour chaque monstre
        for (Entite monstre : monstresACheck) {
            // Si le monstre est à côté du joueur
            if (this.aCote(monstre)) {
                this.infligerDegats(monstre);

                // Si le monstre est mort
                if (monstre.estMort()) {
                    getLabyrinthe().getEntites().remove(monstre);
                    this.setHp(Math.min(this.getHp() + 1, this.getMaxHp()));
                }
            }
        }
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



    //Getters et setters

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
     * deplace le personnage en fonction de l'action.
     * gere la collision avec les murs
     *
     * @param action une des actions possibles
     */
    public void deplacer(Direction action, Entite p) {
        if (action!=null) {
            // case courante
            int[] courante = {(int) p.getY(), (int) p.getX()};

            // calcule case suivante
            int[] suivante = Labyrinthe.getSuivant(courante[0], courante[1], action); // vérification des limites du plateau et si c'est pas un mur et si il n'y a pas de monstre
            if (getLabyrinthe().canEntityMoveTo(suivante[0], suivante[1])) {
                // Dès que l'entité se déplace, il ne dit plus rien
                p.setMsgToSay("");

                // on met à jour la position du personnage
                //Thread d'animation de deplacement du personnage
                new Thread(() -> {
                    double[] old = {p.getY(), p.getX()};
                    for (double i = 0; i < 11; i++) {
                        try {
                            Thread.sleep(15);
                        } catch (InterruptedException e) {
                            throw new RuntimeException(e);
                        }
                        switch (action) {
                            case HAUT -> p.setY(old[0] - (i / 10));
                            case BAS -> p.setY(old[0] + (i / 10));
                            case GAUCHE -> p.setX(old[1] - (i / 10));
                            case DROITE -> p.setX(old[1] + (i / 10));
                        }
                    }
                }).start();
                Case caseSuivante = getLabyrinthe().getCase(suivante[0], suivante[1]);
                caseSuivante.onStepOn(p);
            }
        }
    }
}