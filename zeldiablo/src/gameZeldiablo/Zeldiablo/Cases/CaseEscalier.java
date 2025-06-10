package gameZeldiablo.Zeldiablo.Cases;

import gameZeldiablo.Zeldiablo.Entities.Entite;
import gameZeldiablo.Zeldiablo.Items.Item;
import gameZeldiablo.Zeldiablo.MapList;
import gameZeldiablo.Zeldiablo.VariablesGlobales;
import gameZeldiablo.Zeldiablo.ZeldiabloJeu;

/**
 * Case d'escalier pour changer de niveau
 */
public class CaseEscalier extends Case{
    boolean monte;
    String nextMap;
    int x,y;

    /**
     * Constructeur escalier
     * @param monte escalier montant ou descendant
     */
    public CaseEscalier( boolean monte) {
        super(true, VariablesGlobales.SPRITE_ESCALIER_HAUT);
        this.monte = monte;
        if (!monte) {
            this.setSprite(VariablesGlobales.SPRITE_ESCALIER_BAS);
        }
    }

    public void setNext(String nextMap,int x,int y){
        this.nextMap=nextMap;
        this.x=x;
        this.y=y;
    }

    /**
     * Methode executée quand une entité interagit avec la case d'escalier.
     * Changement de niveau
     */
    @Override
    public void onAction(Entite entite, ZeldiabloJeu jeu) {
        // Si l'escalier est descendant et que le joueur a l'amulette, il gagne le jeu
        if (!monte && jeu.getLaby()== MapList.getMap("FirstMap")) {
            boolean possedeAmulette = jeu.getLaby().getPlayer().possedeItem("Amulette");
            if (possedeAmulette) {
                jeu.getLaby().getPlayer().setaGagne(true);
            } else {
                entite.setMsgToSay("Il me manque quelque chose...");
            }
        }

        jeu.changeLevel(nextMap,x,y);
    }

    public boolean getMonte(){return monte;}
}