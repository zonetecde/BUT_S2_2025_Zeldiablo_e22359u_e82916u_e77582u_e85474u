package Zeldiablo;

import Zeldiablo.Cases.Case;
import Zeldiablo.Entities.Entite;
import com.fasterxml.jackson.annotation.*;
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
