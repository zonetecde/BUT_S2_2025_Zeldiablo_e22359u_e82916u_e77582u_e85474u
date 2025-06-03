package gameZeldiablo.Zeldiablo.StrategieDeplacement;

import gameZeldiablo.Zeldiablo.Entities.Monstre;
import gameZeldiablo.Zeldiablo.Entities.Player;
import gameZeldiablo.Zeldiablo.Labyrinthe;

public class DeplacementIntelligent implements DeplacementStrategie {
    @Override
    /**
     * Déplace l'entité selon la stratégie de déplacement intelligente.
     * Le monstre se déplace vers le joueur en minimisant la distance.
     *
     * @param labyrinthe Le labyrinthe dans lequel se trouve le monstre.
     * @param monstre    Le monstre qui utilise cette stratégie de déplacement.
     */
    public void deplacement(Labyrinthe labyrinthe, Monstre monstre) {
        Player joueur = labyrinthe.getPlayer();
        if (joueur == null) return;
        
        // Récupère toutes les coordonnées nécessaires
        int monstreX = monstre.getX();
        int monstreY = monstre.getY();
        int joueurX = joueur.getX();
        int joueurY = joueur.getY();
        
        // Var pour avoir les meilleures coordonnées
        int meilleurX = monstreX;
        int meilleurY = monstreY;
        double meilleureDistance = calculerDistance(monstreX, monstreY, joueurX, joueurY);
        
        // Teste les 4 directions possibles
        int[] dx = {1, -1, 0, 0};
        int[] dy = {0, 0, 1, -1};
        
        // Essaie pour chaque direction possible
        for (int i = 0; i < 4; i++) {
            int nouveauX = monstreX + dx[i];
            int nouveauY = monstreY + dy[i];
            
            // Vérifie si on peut se déplacer vers cette case
            if (labyrinthe.canEntityMoveTo(nouveauY, nouveauX)) {
                double distance = calculerDistance(nouveauX, nouveauY, joueurX, joueurY);
                
                // Si c'est une meilleur distance par rapport à l'ancienne
                if (distance < meilleureDistance) {
                    meilleureDistance = distance;
                    meilleurX = nouveauX;
                    meilleurY = nouveauY;
                }
            }
        }
        
        // Déplace le monstre vers la meilleure position trouvée
        if (meilleurX != monstreX || meilleurY != monstreY) {
            monstre.setPosition(meilleurY, meilleurX);
        }
    }
    
    /**
     * Calcule la distance entre deux points
     */
    private double calculerDistance(int x1, int y1, int x2, int y2) {
        int deltaX = x2 - x1;
        int deltaY = y2 - y1;
        return Math.sqrt(deltaX * deltaX + deltaY * deltaY);
    }
}
