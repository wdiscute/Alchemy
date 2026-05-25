package net.mcexpanded.alchemy.datagen;

import net.mcexpanded.alchemy.Alchemy;
import net.mcexpanded.alchemy.alchemy.TraitProperties;
import net.minecraft.resources.ResourceKey;

public interface DGTraits
{
    //vitality - regeneration, instant health, health boost?
    ResourceKey<TraitProperties> MINOR_VITALITY = register("minor_vitality",
            new TraitProperties(TraitGroups.VITALITY, 1, 0xffffffff)
    );

    ResourceKey<TraitProperties> VITALITY = register("vitality",
            new TraitProperties(TraitGroups.VITALITY, 2, 0xffffffff)
    );

    ResourceKey<TraitProperties> MAJOR_VITALITY = register("major_vitality",
            new TraitProperties(TraitGroups.VITALITY, 3, 0xffffffff)
    );

    //toxicity - poison
    ResourceKey<TraitProperties> MINOR_TOXICITY = register("minor_toxicity",
            new TraitProperties(TraitGroups.TOXICITY, 1, 0xffffffff)
    );

    ResourceKey<TraitProperties> TOXICITY = register("toxicity",
            new TraitProperties(TraitGroups.TOXICITY, 2, 0xffffffff)
    );

    ResourceKey<TraitProperties> MAJOR_TOXICITY = register("major_toxicity",
            new TraitProperties(TraitGroups.TOXICITY, 3, 0xffffffff)
    );

    //harm - instant damage, bleeding with sustainability
    ResourceKey<TraitProperties> MINOR_HARM = register("minor_harm",
            new TraitProperties(TraitGroups.HARM, 1, 0xffffffff)
    );

    ResourceKey<TraitProperties> HARM = register("harm",
            new TraitProperties(TraitGroups.HARM, 2, 0xffffffff)
    );

    ResourceKey<TraitProperties> MAJOR_HARM = register("major_harm",
            new TraitProperties(TraitGroups.HARM, 3, 0xffffffff)
    );

    //Hindrance - mov speed slowness, attack speed slowness?, mining fatigue
    ResourceKey<TraitProperties> MINOR_HINDRANCE = register("minor_hindrance",
            new TraitProperties(TraitGroups.HINDRANCE, 1, 0xffffffff)
    );

    ResourceKey<TraitProperties> HINDRANCE = register("hindrance",
            new TraitProperties(TraitGroups.HINDRANCE, 2, 0xffffffff)
    );

    ResourceKey<TraitProperties> MAJOR_HINDRANCE = register("major_hindrance",
            new TraitProperties(TraitGroups.HINDRANCE, 3, 0xffffffff)
    );

    //swiftness - swiftness, haste?
    ResourceKey<TraitProperties> MINOR_SWIFTNESS = register("minor_swiftness",
            new TraitProperties(TraitGroups.SWIFTNESS, 1, 0xffffffff)
    );

    ResourceKey<TraitProperties> SWIFTNESS = register("swiftness",
            new TraitProperties(TraitGroups.SWIFTNESS, 2, 0xffffffff)
    );

    ResourceKey<TraitProperties> MAJOR_SWIFTNESS = register("major_swiftness",
            new TraitProperties(TraitGroups.SWIFTNESS, 3, 0xffffffff)
    );

    //decay - poison, a third damage over time? maybe increases damage taken?
    ResourceKey<TraitProperties> MINOR_DECAY = register("minor_decay",
            new TraitProperties(TraitGroups.DECAY, 1, 0xffffffff)
    );

    ResourceKey<TraitProperties> DECAY = register("decay",
            new TraitProperties(TraitGroups.DECAY, 2, 0xffffffff)
    );

    ResourceKey<TraitProperties> MAJOR_DECAY = register("major_decay",
            new TraitProperties(TraitGroups.DECAY, 3, 0xffffffff)
    );

    //defile - mining related debuff?
    ResourceKey<TraitProperties> MINOR_DEFILE = register("minor_defile",
            new TraitProperties(TraitGroups.DEFILE, 1, 0xffffffff)
    );

    ResourceKey<TraitProperties> DEFILE = register("defile",
            new TraitProperties(TraitGroups.DEFILE, 2, 0xffffffff)
    );

    ResourceKey<TraitProperties> MAJOR_DEFILE = register("major_defile",
            new TraitProperties(TraitGroups.DEFILE, 3, 0xffffffff)
    );

    //might - attack related potion combinations?
    ResourceKey<TraitProperties> MINOR_MIGHT = register("minor_might",
            new TraitProperties(TraitGroups.MIGHT, 1, 0xffffffff)
    );

    ResourceKey<TraitProperties> MIGHT = register("might",
            new TraitProperties(TraitGroups.MIGHT, 2, 0xffffffff)
    );

    ResourceKey<TraitProperties> MAJOR_MIGHT = register("major_might",
            new TraitProperties(TraitGroups.MIGHT, 3, 0xffffffff)
    );

    //Voracity - hunger
    ResourceKey<TraitProperties> MINOR_VORACITY = register("minor_voracity",
            new TraitProperties(TraitGroups.VORACITY, 1, 0xffffffff)
    );

    ResourceKey<TraitProperties> VORACITY = register("voracity",
            new TraitProperties(TraitGroups.VORACITY, 2, 0xffffffff)
    );

    ResourceKey<TraitProperties> MAJOR_VORACITY = register("major_voracity",
            new TraitProperties(TraitGroups.VORACITY, 3, 0xffffffff)
    );

    //Satiety - saturation?
    ResourceKey<TraitProperties> MINOR_SATIETY = register("minor_satiety",
            new TraitProperties(TraitGroups.SATIETY, 1, 0xffffffff)
    );

    ResourceKey<TraitProperties> SATIETY = register("satiety",
            new TraitProperties(TraitGroups.SATIETY, 2, 0xffffffff)
    );

    ResourceKey<TraitProperties> MAJOR_SATIETY = register("major_satiety",
            new TraitProperties(TraitGroups.SATIETY, 3, 0xffffffff)
    );


    //steady - knockback resistance
    ResourceKey<TraitProperties> MINOR_STEADY = register("minor_steadiness",
            new TraitProperties(TraitGroups.STEADINESS, 1, 0xffffffff)
    );

    ResourceKey<TraitProperties> STEADY = register("steadiness",
            new TraitProperties(TraitGroups.STEADINESS, 2, 0xffffffff)
    );

    ResourceKey<TraitProperties> MAJOR_STEADY = register("major_steadiness",
            new TraitProperties(TraitGroups.STEADINESS, 3, 0xffffffff)
    );

    //scorch - fire resistance, burn
    ResourceKey<TraitProperties> MINOR_SCORCH = register("minor_scorch",
            new TraitProperties(TraitGroups.SCORCH, 1, 0xffffffff)
    );

    ResourceKey<TraitProperties> SCORCH = register("scorch",
            new TraitProperties(TraitGroups.SCORCH, 2, 0xffffffff)
    );

    ResourceKey<TraitProperties> MAJOR_SCORCH = register("major_scorch",
            new TraitProperties(TraitGroups.SCORCH, 3, 0xffffffff)
    );

    //aquosity - water breathing
    ResourceKey<TraitProperties> MINOR_AQUOSITY = register("minor_aquosity",
            new TraitProperties(TraitGroups.AQUOSITY, 1, 0xffffffff)
    );

    ResourceKey<TraitProperties> AQUOSITY = register("aquosity",
            new TraitProperties(TraitGroups.AQUOSITY, 2, 0xffffffff)
    );

    ResourceKey<TraitProperties> MAJOR_AQUOSITY = register("major_aquosity",
            new TraitProperties(TraitGroups.AQUOSITY, 3, 0xffffffff)
    );






    //luminosity
    ResourceKey<TraitProperties> LUMINOSITY = register("luminosity",
            new TraitProperties(TraitGroups.LUMINOSITY, 1, 0xffffffff)
    );

    //stickiness
    ResourceKey<TraitProperties> STICKINESS = register("stickiness",
            new TraitProperties(TraitGroups.STICKINESS, 1, 0xffffffff)
    );

    //obscurity
    ResourceKey<TraitProperties> OBSCURITY = register("obscurity",
            new TraitProperties(TraitGroups.OBSCURITY, 1, 0xffffffff)
    );

    //persistence - makes potions into their "permanent" counterpart? health boost
    ResourceKey<TraitProperties> PERSISTENCE = register("persistence",
            new TraitProperties(TraitGroups.PERSISTENCE, 1, 0xffffffff)
    );

    //sustainability - makes potions into their over time counterparts?
    ResourceKey<TraitProperties> SUSTAINABILITY = register("sustainability",
            new TraitProperties(TraitGroups.SUSTAINABILITY, 1, 0xffffffff)
    );

    //levity - slow falling with sustainability?
    ResourceKey<TraitProperties> LEVITY = register("levity",
            new TraitProperties(TraitGroups.LEVITY, 1, 0xffffffff)
    );

    //Temporal - some time related stuff, teleport back? Restore health to where it was when it ends?
    ResourceKey<TraitProperties> TEMPORAL = register("temporal",
            new TraitProperties(TraitGroups.TEMPORAL, 1, 0xffffffff)
    );

    //radiance - affects stuff around you?
    ResourceKey<TraitProperties> RADIANCE = register("radiance",
            new TraitProperties(TraitGroups.RADIANCE, 1, 0xffffffff)
    );

    //purity - cleansing type stuff? reduce timer of all other effects?
    ResourceKey<TraitProperties> PURITY = register("purity",
            new TraitProperties(TraitGroups.PURITY, 1, 0xffffffff)
    );

    //perception - night vision, seeing aura of mobs?
    ResourceKey<TraitProperties> PERCEPTION = register("perception",
            new TraitProperties(TraitGroups.PERCEPTION, 1, 0xffffffff)
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
