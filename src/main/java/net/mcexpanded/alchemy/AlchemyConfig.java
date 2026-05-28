package net.mcexpanded.alchemy;

import net.neoforged.neoforge.common.ModConfigSpec;

public class AlchemyConfig
{

    private static final ModConfigSpec.Builder BUILDER_SERVER = new ModConfigSpec.Builder();

    public static final ModConfigSpec.BooleanValue HIDE_REAGENT_TRAITS_UNTIL_FOUND = BUILDER_SERVER
            .translation("alchemy.configuration.hide_traits")
            .comment("Hides reagent traits until a potion with those traits has been crafted")
            .define("hide_traits", true);

    public static final ModConfigSpec.BooleanValue HIDE_EFFECTS_REQUIREMENTS_UNTIL_FOUND = BUILDER_SERVER
            .translation("alchemy.configuration.hide_effects")
            .comment("Hides potion effect requirements until that potion has been crafted")
            .define("hide_effects", true);

    static final ModConfigSpec SPEC_SERVER = BUILDER_SERVER.build();

}
