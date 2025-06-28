package Zeldiablo;

import Zeldiablo.Cases.CaseBombe;
import Zeldiablo.Entities.Monstre;
import Zeldiablo.Entities.Player;
import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.JsonSubTypes;
import com.fasterxml.jackson.annotation.JsonTypeInfo;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;

@JsonIdentityInfo(
        generator = ObjectIdGenerators.UUIDGenerator.class,
        property = "@id"
)
@JsonTypeInfo(
        use = JsonTypeInfo.Id.NAME,
        include = JsonTypeInfo.As.PROPERTY,
        property = "type")
@JsonSubTypes({
        @JsonSubTypes.Type(value = Monstre.class, name = "Monstre"),
        @JsonSubTypes.Type(value = CaseBombe.class, name = "CaseBombe"),
        @JsonSubTypes.Type(value = Player.class, name = "Player")
        // Ajoute ici tous les types spécifiques nécessaires
})
public interface Tickable {
    void tick();
}
