package gameZeldiablo.Zeldiablo.Entities;

import gameZeldiablo.Zeldiablo.*;

import javafx.scene.image.Image;

import java.io.Serializable;

/**
 * gere un personnage situe en x,y
 */
public abstract class Entite implements Serializable, Sprited,Cloneable, Tickable {

    /**
     * position du personnage et vie
     */
    private volatile double x, y;
    private double hp;
    private final double maxHp; // Les points de vie de l'entité
    private final double degat; // Les dégâts que fait l'entité
    private boolean enVie = true;
    private String msgToSay;
    private String sprite; // L'image de l'entité
    private Labyrinthe labyrinthe;

    /**
     * constructeur
     *
     * @param dx position selon x
     * @param dy position selon y
     */
    public Entite(double dx, double dy, double maxHp, double degat, String img, Labyrinthe labyrinthe) {
        this.x = dx;
        this.y = dy;
        this.hp = maxHp;
        this.maxHp = maxHp;
        this.degat = degat;
        this.sprite = img;
        this.setLabyrinthe(labyrinthe);
    }


    /**
     * Retourne le sprite de l'entité
     * @return Sprite de l'entité
     */
    public Image getSprite() {
        return Sprite.getImg(sprite);
    }

    /**
     * Remplace le sprite
     * @param s nouvelle image
     */
    public void setSprite(String s){sprite=s;}

    /**
     * Methode prenant en compte des dégats et les appliquant au personnage
     * @param d nombre de dégats
     */
    public void prendreDegat(double d){
        if (this.hp>d) {
            this.hp -= d;
        }
        else{
            this.enVie=false;
            this.hp=0;
            setLabyrinthe(null);
        }
    }

    public abstract void deplacer(Direction action);

    public boolean gagnerVie(double v){
        if (this.hp==this.maxHp){
            return false;
        }
        if (this.hp+v>this.maxHp){
            this.hp=this.maxHp;
            return true;
        }
        this.hp+=v;
        return true;
    }

    /**
     * Inflige des dégâts à une cible
     * @param cible L'entité cible qui subit les dégâts
     */
    public void infligerDegats(Entite cible) {
        if (cible != null && cible.enVie) {
            cible.prendreDegat(this.degat);
        }
    }

    @Override
    public void tick() {/*inutile ici*/}

    public void animationDep(Direction action){
        new Thread(() -> {
            double[] old = {this.getY(), this.getX()};
            for (double i = 0; i < 11; i++) {
                try {
                    Thread.sleep(15);
                } catch (InterruptedException e) {
                    throw new RuntimeException(e);
                }
                switch (action) {
                    case HAUT   -> this.setY(old[0] - (i / 10));
                    case BAS    -> this.setY(old[0] + (i / 10));
                    case GAUCHE -> this.setX(old[1] - (i / 10));
                    case DROITE -> this.setX(old[1] + (i / 10));
                }
            }
        }).start();
    }

    //Getters

    public Labyrinthe getLabyrinthe(){return labyrinthe;}

    public void setLabyrinthe(Labyrinthe l){
        if (this.labyrinthe!=null){
            this.labyrinthe.getEntites().remove(this);
            this.labyrinthe.getTics().remove(this);
        }
        if (l!=null) {
            this.labyrinthe = l;
            l.getEntites().add(this);
            l.getTics().add(this);
        }else{
            this.labyrinthe=null;
        }
    }

    /**
     * Retourne si l'entité est en vie
     */
    public boolean getEnVie(){
        return this.enVie;
    }

    /**
     * @return position y du personnage
     */
    public int getY() {
        //getter
        return (int)Math.ceil(this.y);
    }

    /**
     * @return position x du personnage
     */
    public int getX() {
        // getter
        return (int)Math.ceil(this.x);
    }

    public double[] getDoublePos(){
        double[] tmp = {y,x};
        return tmp;
    }


    /**
     * Changement de position de l'entite
     * @param y pos y
     * @param x pos x
     */
    public void setPosition(int y, int x){
        this.y = y;
        this.x = x;
    }

    /**
     *
     * @return l'etat de vie du perso
     */
    public boolean estMort(){
        return !this.enVie;
    }

    /**
     * @return les pv du perso
     */
    public double getHp(){
        return this.hp;
    }

    /**
     * @return les pv max du perso
     */
    public double getMaxHp(){
        return this.maxHp;
    }

    /**
     *
     * @param y position y du perso
     */
    public void setY(double y){
        this.y=y;
    }

    /**
     *
     * @param x position x du perso
     */
    public void setX(double x){
        this.x=x;
    }

    /**
     * @return les degats du perso
     */
    public double getDegat() {
        return this.degat;
    }

    /**
     * Set si l'entité est en vie ou non
     * @param b true si l'entité est en vie, false sinon
     */
    public void setEnVie(boolean b) {
        this.enVie = b;
    }

    /**
     * Set les hp de l'entité
     * @param hp Les points de vie à attribuer
     */
    public void setHp(double hp) {
        this.hp = hp;
    }

    /**
     * Verifie l'adjascence
     * @param e Entite adjacente
     * @return si à cote
     */
    public boolean aCote(Entite e) {
        return (Math.abs(this.x - e.getX()) <= 1 && Math.abs(this.y - e.getY()) <= 1);
    }

    /**
     * messages
     * @return message de l'entite
     */
    public String getMsgToSay() {
        return msgToSay;
    }

    /**
     * set message
     * @param msgToSay nouveau message
     */
    public void setMsgToSay(String msgToSay) {
        this.msgToSay = msgToSay;
    }


    @Override
    public Entite clone(){
        try {
            return (Entite)super.clone();
        } catch (CloneNotSupportedException e) {
            throw new RuntimeException(e);
        }
    }

    public Entite clone(int x,int y){
            Entite e= this.clone();
            e.setX(x);
            e.setY(y);
            return e;
    }
}
