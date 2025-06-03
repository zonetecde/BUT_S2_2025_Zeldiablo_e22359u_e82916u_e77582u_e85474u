package gameZeldiablo.Zeldiablo.Entities;

import javafx.scene.paint.Color;

public class EtatVisuelle {
    public static final EtatVisuelle NORMAL = new EtatVisuelle("NORMAL", Color.RED);
    public static final EtatVisuelle ATTAQUE_JOUEUR = new EtatVisuelle("ATTAQUE_JOUEUR", Color.GREEN);
    public static final EtatVisuelle ATTAQUE_MONSTRE = new EtatVisuelle("ATTAQUE_MONSTRE", Color.BLACK);
    public static final EtatVisuelle MORT = new EtatVisuelle("MORT", Color.TRANSPARENT);

    private final String nom;
    private final Color couleur;

    private EtatVisuelle (String nom, Color couleur) {
        this.nom = nom;
        this.couleur = couleur;
    }

    public String getNom() {
        return nom;
    }

    public Color getCouleur() {
        return couleur;
    }
}