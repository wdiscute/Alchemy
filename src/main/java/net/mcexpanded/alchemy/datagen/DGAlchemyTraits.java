package net.mcexpanded.alchemy.datagen;

import net.mcexpanded.alchemy.Alchemy;
import net.mcexpanded.alchemy.alchemy.TraitProperties;
import net.minecraft.resources.ResourceKey;

public interface DGAlchemyTraits
{
    //vitality - regeneration, instant health, health boost?
    ResourceKey<TraitProperties> MINOR_VITALITY = register("minor_vitality",
            new TraitProperties("vitality", 1, 0xffffffff)
    );

    ResourceKey<TraitProperties> MAJOR_VITALITY = register("major_vitality",
            new TraitProperties("vitality", 2, 0xffffffff)
    );

    //Hindrance - mov speed slowness, attack speed slowness?, mining fatigue
    ResourceKey<TraitProperties> MINOR_HINDRANCE = register("minor_hindrance",
            new TraitProperties("hindrance", 1, 0xffffffff)
    );

    ResourceKey<TraitProperties> MAJOR_HINDRANCE = register("major_hindrance",
            new TraitProperties("hindrance", 2, 0xffffffff)
    );

    //harm - instant damage, bleeding with sustainability
    ResourceKey<TraitProperties> HARM = register("harm",
            new TraitProperties("harm", 1, 0xffffffff)
    );

    //persistence - makes potions into their "permanent" counterpart? health boost
    ResourceKey<TraitProperties> PERSISTENCE = register("persistence",
            new TraitProperties("persistence", 2, 0xffffffff)
    );

    //sustainability - makes potions into their over time counterparts?
    ResourceKey<TraitProperties> SUSTAINABILITY = register("sustainability",
            new TraitProperties("sustainability", 2, 0xffffffff)
    );

    //levity - slow falling with sustainability?
    ResourceKey<TraitProperties> LEVITY = register("levity",
            new TraitProperties("levity", 2, 0xffffffff)
    );

    //swiftness - swiftness, haste?
    ResourceKey<TraitProperties> MINOR_SWIFTNESS = register("minor_swiftness",
            new TraitProperties("swiftness", 1, 0xffffffff)
    );

    ResourceKey<TraitProperties> MAJOR_SWIFTNESS = register("major_swiftness",
            new TraitProperties("swiftness", 1, 0xffffffff)
    );

    //steady - knockback resistance
    ResourceKey<TraitProperties> STEADY = register("steady",
            new TraitProperties("steady", 1, 0xffffffff)
    );

    //toxicity - poison
    ResourceKey<TraitProperties> MINOR_TOXICITY = register("minor_toxicity",
            new TraitProperties("toxicity", 1, 0xffffffff)
    );

    ResourceKey<TraitProperties> MAJOR_TOXICITY = register("major_toxicity",
            new TraitProperties("toxicity", 2, 0xffffffff)
    );

    //decay - poison, a third damage over time? maybe increases damage taken?
    ResourceKey<TraitProperties> DECAY = register("decay",
            new TraitProperties("decay", 1, 0xffffffff)
    );

    //defile - mining related debuff?
    ResourceKey<TraitProperties> DEFILE = register("defile",
            new TraitProperties("defile", 2, 0xffffffff)
    );

    //might - attack related potion combinations?
    ResourceKey<TraitProperties> MIGHT = register("might",
            new TraitProperties("might", 1, 0xffffffff)
    );

    //Voracity - hunger
    ResourceKey<TraitProperties> VORACITY = register("voracity",
            new TraitProperties("voracity", 1, 0xffffffff)
    );

    //Satiety - saturation?
    ResourceKey<TraitProperties> SATIETY = register("satiety",
            new TraitProperties("satiety", 1, 0xffffffff)
    );

    //Temporal - some time related stuff, teleport back? Restore health to where it was when it ends?
    ResourceKey<TraitProperties> TEMPORAL = register("temporal",
            new TraitProperties("temporal", 1, 0xffffffff)
    );

    //radiance - affects stuff around you?
    ResourceKey<TraitProperties> RADIANCE = register("radiance",
            new TraitProperties("radiance", 1, 0xffffffff)
    );

    //purity - cleansing type stuff? reduce timer of all other effects?
    ResourceKey<TraitProperties> PURITY = register("purity",
            new TraitProperties("purity", 1, 0xffffffff)
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
