package gameZeldiablo.Zeldiablo.Entities;


import gameZeldiablo.Zeldiablo.VariablesGlobales;

/**
 * gere un personnage situe en x,y
 */
public abstract class Entite {

    /**
     * position du personnage
     */
    private int x, y, hp;
    boolean enVie=true;

    /**
     * constructeur
     *
     * @param dx position selon x
     * @param dy position selon y
     */
    public Entite(int dx, int dy, int hp) {
        this.x = dx;
        this.y = dy;
        this.hp = hp;
    }

    public Entite(int dx, int dy) {
        this.x = dx;
        this.y = dy;
        this.hp = VariablesGlobales.PVBASE;
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

    // ############################################
    // GETTER
    // ############################################

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

    public int getHp(){
        return this.hp;
    }

    public void setY(int y){
        this.y=y;
    }

    public void setX(int x){
        this.x=x;
    }
}
