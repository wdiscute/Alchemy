package net.mcexpanded.alchemy.datagen;

import com.mojang.datafixers.util.Pair;
import net.mcexpanded.alchemy.Alchemy;
import net.mcexpanded.alchemy.alchemy.TraitProperties;
import net.minecraft.core.HolderLookup;
import net.minecraft.core.RegistrySetBuilder;
import net.minecraft.data.PackOutput;
import net.minecraft.data.worldgen.BootstrapContext;
import net.minecraft.resources.ResourceKey;
import net.neoforged.neoforge.common.conditions.ModLoadedCondition;
import net.neoforged.neoforge.common.data.DatapackBuiltinEntriesProvider;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.concurrent.CompletableFuture;

public class DGAlchemyTraitPropertiesProvider extends DatapackBuiltinEntriesProvider
{
    public static final List<Pair<ResourceKey<TraitProperties>, TraitProperties>> PROPERTIES = new ArrayList<>();
    private static final List<ResourceKey<TraitProperties>> COMPAT_KEYS = new ArrayList<>();
    public static final RegistrySetBuilder REGISTRY = new RegistrySetBuilder().add(
            Alchemy.TRAIT_REGISTRY_KEY, DGAlchemyTraitPropertiesProvider::bootstrap);

    public DGAlchemyTraitPropertiesProvider(PackOutput output, CompletableFuture<HolderLookup.Provider> registries)
    {
        DGAlchemyTraits.bootstrap(); //register all entries before anything else
        super(output, registries, REGISTRY,
                (c) ->
                        COMPAT_KEYS.forEach(rk -> c.accept(rk, new ModLoadedCondition(rk.identifier().getNamespace()))),
                Set.of(Alchemy.MOD_ID, "minecraft")
        );
    }

    public static void register(String name, TraitProperties tp, ResourceKey<TraitProperties> key)
    {
        PROPERTIES.add(Pair.of(key, tp));
        String namespace = key.identifier().getNamespace();
        if (!namespace.equals("minecraft") && !namespace.equals("starcatcher"))
            COMPAT_KEYS.add(key);
    }

    public static void bootstrap(BootstrapContext<TraitProperties> context)
    {
        PROPERTIES.forEach(p -> context.register(p.getFirst(), p.getSecond()));
    }

    @Override
    public String getName()
    {
        return "TraitProperties";
    }
}
