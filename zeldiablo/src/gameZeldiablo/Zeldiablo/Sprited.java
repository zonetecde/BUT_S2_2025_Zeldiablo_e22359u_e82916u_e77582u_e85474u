package gameZeldiablo.Zeldiablo;

import com.fasterxml.jackson.annotation.*;
import gameZeldiablo.Zeldiablo.Cases.Case;
import gameZeldiablo.Zeldiablo.Entities.Entite;
import gameZeldiablo.Zeldiablo.Entities.Monstre;
import gameZeldiablo.Zeldiablo.Entities.Player;
import javafx.scene.image.Image;

public interface Sprited {
    @JsonTypeInfo(
            use = JsonTypeInfo.Id.NAME,
            include = JsonTypeInfo.As.PROPERTY,
            property = "@Type"
    )
    @JsonSubTypes({
            @JsonSubTypes.Type(value = Entite.class, name = "Entite"),
            @JsonSubTypes.Type(value = Case.class, name = "Case")
    })
    @JsonIdentityInfo(
            generator = ObjectIdGenerators.UUIDGenerator.class,
            property = "@id"
    )
    @JsonIgnore
    Image getSprite();
}
