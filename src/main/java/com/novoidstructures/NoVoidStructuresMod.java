package com.novoidstructures;

import com.mojang.logging.LogUtils;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.ModContainer;
import net.neoforged.fml.common.Mod;
import net.neoforged.fml.config.ModConfig;
import net.neoforged.fml.loading.FMLPaths;
import net.neoforged.neoforge.event.AddPackFindersEvent;
import net.neoforged.neoforge.resource.PathPackResources;
import net.neoforged.neoforge.resource.event.ServerAboutToStartEvent;
import net.neoforged.neoforge.event.RegisterCommandsEvent;
import net.minecraft.server.packs.PackType;
import net.minecraft.server.packs.repository.KnownPack;
import net.minecraft.server.packs.repository.Pack;
import net.minecraft.server.packs.repository.PackSource;
import net.minecraft.server.packs.repository.RepositorySource;
import org.slf4j.Logger;

import java.nio.file.Path;
import java.util.Optional;
import java.util.function.Consumer;

@Mod(NoVoidStructuresMod.MOD_ID)
@net.neoforged.fml.common.EventBusSubscriber(modid = NoVoidStructuresMod.MOD_ID)
public class NoVoidStructuresMod {
    public static final String MOD_ID = "no_void_structures";
    private static final Logger LOGGER = LogUtils.getLogger();

    public NoVoidStructuresMod(ModContainer container) {
        container.registerConfig(ModConfig.Type.SERVER, NVSConfig.SPEC);
    }

    @SubscribeEvent
    public static void onServerAboutToStart(ServerAboutToStartEvent event) {
        DynamicPackGenerator.generate(event.getServer().registryAccess(), event.getServer().getWorldData().worldGenOptions());
    }

    @SubscribeEvent
    public static void onRegisterCommands(RegisterCommandsEvent event) {
        NVSCommands.register(event.getDispatcher());
    }

    @SubscribeEvent
    public static void onAddPackFinders(AddPackFindersEvent event) {
        if (event.getPackType() != PackType.SERVER_DATA) {
            return;
        }

        Path packRoot = FMLPaths.GAMEDIR.get().resolve("generated/no_void_structures_dynamic_pack");
        RepositorySource source = new DynamicRepositorySource(packRoot);
        event.addRepositorySource(source);
        LOGGER.info("Registered dynamic datapack finder for {}", packRoot);
    }

    private record DynamicRepositorySource(Path packRoot) implements RepositorySource {
        @Override
        public void loadPacks(Consumer<Pack> consumer) {
            Pack pack = Pack.readMetaAndCreate(
                    "file/no_void_structures_dynamic",
                    net.minecraft.network.chat.Component.literal("No Void Structures Dynamic"),
                    false,
                    id -> new PathPackResources.PathResourcesSupplier(packRoot),
                    PackType.SERVER_DATA,
                    Pack.Position.TOP,
                    false,
                    PackSource.DEFAULT,
                    Optional.of(new KnownPack("no_void_structures", "dynamic", "1"))
            );

            if (pack != null) {
                consumer.accept(pack);
            }
        }
    }
}
