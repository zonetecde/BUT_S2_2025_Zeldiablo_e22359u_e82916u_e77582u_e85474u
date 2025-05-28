package gameZeldiablo.Zeldiablo.Entities;


import gameZeldiablo.Zeldiablo.VariablesGlobales;

/**
 * gere un personnage situe en x,y
 */
public abstract class Entite {

    /**
     * position du personnage et vie
     */
    private int x, y, hp, maxHp;
    boolean enVie = true;

    /**
     * constructeur
     *
     * @param dx position selon x
     * @param dy position selon y
     */
    public Entite(int dx, int dy, int hp, int maxHp) {
        this.x = dx;
        this.y = dy;
        this.hp = hp;
        this.maxHp = maxHp;
    }

    public Entite(int dx, int dy) {
        this.x = dx;
        this.y = dy;
        this.hp = VariablesGlobales.PV_BASE;
        this.maxHp = VariablesGlobales.PV_BASE;
    }

    /**
     * permet de savoir si le personnage est en x,y
     *
     * @param dx position testee
     * @param dy position testee
     * @return true si le personnage est bien en (dx,dy)
     */
    public boolean etrePresent(int dx, int dy) {
        return (this.x == dx && this.y == dy);
    }

    /**
     * Methode prenant en compte des dégats et les appliquant au personnage
     * @param d nombre de dégats
     */
    public void prendreDegat(int d){
        if (this.hp>d) {
            this.hp -= d;
        }
        else{
            this.enVie=false;
            this.hp=0;
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
     *
     * @return l'etat de vie du perso
     */
    public boolean estMort(){
        return !this.enVie;
    }

    /**
     * @return les pv du perso
     */
    public int getHp(){
        return this.hp;
    }

    /**
     * @return les pv max du perso
     */
    public int getMaxHp(){
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


}
