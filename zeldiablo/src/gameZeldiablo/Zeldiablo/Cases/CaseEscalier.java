package gameZeldiablo.Zeldiablo.Cases;

import gameZeldiablo.Zeldiablo.Entities.Entite;
import gameZeldiablo.Zeldiablo.Items.Item;
import gameZeldiablo.Zeldiablo.VariablesGlobales;
import gameZeldiablo.Zeldiablo.ZeldiabloJeu;

/**
 * Case d'escalier pour changer de niveau
 */
public class CaseEscalier extends Case{
    boolean monte;

    /**
     * Constructeur escalier
     * @param x pos x
     * @param y pos y
     * @param monte escalier montant ou descendant
     */
    public CaseEscalier(int x, int y, boolean monte) {
        super(x, y, true, CaseType.SOL, VariablesGlobales.SPRITE_ESCALIER_HAUT);
        this.monte = monte;
        if (!monte) {
            this.setImg(VariablesGlobales.SPRITE_ESCALIER_BAS);
        }
    }

    /**
     * Methode executée quand une entité interagit avec la case d'escalier.
     * Changement de niveau
     */
    @Override
    public void onAction(Entite entite, ZeldiabloJeu jeu) {
        // Si l'escalier est descendant et que le joueur a l'amulette, il gagne le jeu
        String nomFichier = jeu.getLaby().getNomFichier();
        if (!monte && nomFichier.endsWith("labyjeu1.txt")) {
            boolean possedeAmulette = jeu.getLaby().getPlayer().possedeItem("Amulette");
            if (possedeAmulette) {
                jeu.getLaby().getPlayer().setaGagne(true);
            } else {
                entite.setMsgToSay("Il me manque quelque chose...");
                return;
            }
            return;
        }



        jeu.changeLevel(this.monte);
    }
}