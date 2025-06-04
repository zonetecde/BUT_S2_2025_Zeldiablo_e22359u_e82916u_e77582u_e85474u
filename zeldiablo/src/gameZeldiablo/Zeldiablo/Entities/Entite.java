package gameZeldiablo.Zeldiablo.Entities;

import gameZeldiablo.Zeldiablo.Sprite;

/**
 * gere un personnage situe en x,y
 */
public abstract class Entite extends Sprite {

    /**
     * position du personnage et vie
     */
    private int x, y;
    private double hp;
    private final double maxHp; // Les points de vie de l'entité
    private final double degat; // Les dégâts que fait l'entité
    boolean enVie = true;
    private String msgToSay;
    private EtatVisuelle etatVisuelle;

    /**
     * constructeur
     *
     * @param dx position selon x
     * @param dy position selon y
     */
    public Entite(int dx, int dy, double maxHp, double degat, String img) {
        super(img);
        this.x = dx;
        this.y = dy;
        this.hp = maxHp;
        this.maxHp = maxHp;
        this.degat = degat;
    }


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
        }
    }

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
    public void mettreDegat(Entite cible) {
        if (cible != null && cible.enVie) {
            cible.prendreDegat(this.degat);
        }
    }

    /**
     * @return position x du personnage
     */
    public int getX() {
        // getter
        return this.x;
    }

    /**
     * @return position y du personnage
     */
    public int getY() {
        //getter
        return this.y;
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
    public void setY(int y){
        this.y=y;
    }

    /**
     *
     * @param x position x du perso
     */
    public void setX(int x){
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
     * ???
     * @param etatVisuelle etat
     */
    public void setEtatVisuelle(EtatVisuelle etatVisuelle) {
        this.etatVisuelle = etatVisuelle;
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
}
