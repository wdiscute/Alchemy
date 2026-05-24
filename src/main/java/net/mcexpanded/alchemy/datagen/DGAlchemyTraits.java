package net.mcexpanded.alchemy.datagen;

import net.mcexpanded.alchemy.alchemy.TraitProperties;

import java.util.ArrayList;
import java.util.List;

public interface DGAlchemyTraits
{
    List<TraitProperties> ALCHEMY_TRAITS = new ArrayList<>();

    TraitProperties MINOR_VITALITY = register("minor_vitality",
            new TraitProperties("vitality", 1, 0xffffffff)
    );

    TraitProperties MAJOR_VITALITY = register("major_vitality",
            new TraitProperties("vitality", 2, 0xffffffff)
    );


    TraitProperties FORTIFY = register("fortify",
            new TraitProperties("fortify", 1, 0xffffffff)
    );



    static private TraitProperties register(String name, TraitProperties tp)
    {
        DGAlchemyTraitPropertiesProvider.register(name, tp);
        return tp;
    }

    static void bootstrap()
    {
    }
}
