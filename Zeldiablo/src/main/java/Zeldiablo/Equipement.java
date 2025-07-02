package Zeldiablo;

import Zeldiablo.Items.Arme;
import Zeldiablo.Items.Armors.Armor;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import java.io.Serializable;

@JsonIgnoreProperties(ignoreUnknown = true)
public class Equipement implements Serializable {
    Armor head;
    Armor torso;
    Armor leggings;
    Armor boots;
    Arme weapon;
    Armor[] armors = {head,torso,leggings,boots};

    public double DefenseGet(){
        double tmp = 0;
        for (Armor armor : armors){
            if (armor!=null){
                tmp+=armor.getArmorPoints();
            }
        }
        return tmp;
    }

    public double AttaqueGet(){
        if (weapon!=null) {
            return weapon.getDegat();
        }
        return 0;
    }

    public void setArmorPiece(Armor a, int i){
        armors[i] = a;
    }

    public Armor getArmorPiece(int i){
        return armors[i];
    }

    public void setArmors(Armor[] a){armors=a;}

    public Armor[] getArmors() {
        return armors;
    }

    public void setWeapon(Arme weapon){this.weapon=weapon;}

    public Arme getWeapon(){return this.weapon;}

    //TODO Gestion de l'equipement
}
