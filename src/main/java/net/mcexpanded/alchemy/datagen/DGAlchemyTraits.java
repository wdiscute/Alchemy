package net.mcexpanded.alchemy.datagen;

import net.mcexpanded.alchemy.Alchemy;
import net.mcexpanded.alchemy.alchemy.TraitProperties;
import net.minecraft.resources.ResourceKey;

import java.util.ArrayList;
import java.util.List;

public interface DGAlchemyTraits
{
    ResourceKey<TraitProperties> MINOR_VITALITY = register("minor_vitality",
            new TraitProperties("vitality", 1, 0xffffffff)
    );

    ResourceKey<TraitProperties> MAJOR_VITALITY = register("major_vitality",
            new TraitProperties("vitality", 2, 0xffffffff)
    );


    ResourceKey<TraitProperties> FORTIFY = register("fortify",
            new TraitProperties("fortify", 1, 0xffffffff)
    );



    static private ResourceKey<TraitProperties> register(String name, TraitProperties tp)
    {
        ResourceKey<TraitProperties> key = ResourceKey.create(Alchemy.TRAIT_REGISTRY_KEY, Alchemy.rl(name));
        DGAlchemyTraitPropertiesProvider.register(name, tp, key);
        return key;
    }

    static void bootstrap()
    {
    }
}
