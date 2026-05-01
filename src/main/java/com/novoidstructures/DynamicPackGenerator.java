package com.novoidstructures;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.mojang.logging.LogUtils;
import net.neoforged.fml.loading.FMLPaths;
import net.minecraft.core.Holder;
import net.minecraft.core.HolderLookup;
import net.minecraft.core.registries.Registries;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.level.levelgen.WorldOptions;
import net.minecraft.world.level.levelgen.structure.Structure;
import org.slf4j.Logger;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.HashSet;
import java.util.Set;
import java.util.concurrent.atomic.AtomicInteger;

public final class DynamicPackGenerator {
    private static final Logger LOGGER = LogUtils.getLogger();
    private static final Gson GSON = new GsonBuilder().setPrettyPrinting().create();

    private DynamicPackGenerator() {}

    public static void generate(HolderLookup.Provider registries, WorldOptions ignored) {
        RuntimeRules.load();
        Path root = FMLPaths.GAMEDIR.get().resolve("generated/no_void_structures_dynamic_pack");
        Path structuresPath = root.resolve("data/minecraft/worldgen/structure");
        Path featuresPath = root.resolve("data/minecraft/worldgen/placed_feature");

        try {
            cleanGeneratedWorldgen(root);
            Files.createDirectories(structuresPath);
            Files.createDirectories(featuresPath);
            writePackMeta(root);

            AtomicInteger structuresWritten = new AtomicInteger();
            AtomicInteger featuresWritten = new AtomicInteger();

            if (NVSConfig.ENABLE_STRUCTURE_PROCESSING.get()) {
                Set<String> skip = new HashSet<>(NVSConfig.STRUCTURE_EXCEPTIONS.get().stream().map(Object::toString).toList());
                skip.addAll(RuntimeRules.IGNORED_STRUCTURES);
                skip.addAll(RuntimeRules.IGNORED_UNDERGROUND_STRUCTURES);
                registries.lookupOrThrow(Registries.STRUCTURE).listElementIds().forEach(id -> {
                    if (!skip.contains(id.toString()) && !RuntimeRules.isNamespaceIgnored(id.toString())) {
                        if (writeStructureJson(structuresPath, id)) structuresWritten.incrementAndGet();
                    }
                });
            }

            if (NVSConfig.ENABLE_FEATURE_PROCESSING.get()) {
                Set<String> skipFeatures = new HashSet<>(NVSConfig.FEATURE_EXCEPTIONS.get().stream().map(Object::toString).toList());
                skipFeatures.addAll(RuntimeRules.IGNORED_FEATURES);
                registries.lookupOrThrow(Registries.PLACED_FEATURE).listElementIds().forEach(id -> {
                    if (!skipFeatures.contains(id.toString()) && !RuntimeRules.isNamespaceIgnored(id.toString())) {
                        if (writeFeatureJson(featuresPath, id)) featuresWritten.incrementAndGet();
                    }
                });
            }

            LOGGER.info("Dynamic datapack generated at {} (structures={}, features={})", root, structuresWritten.get(), featuresWritten.get());
        } catch (IOException e) {
            LOGGER.error("Failed to generate dynamic datapack", e);
        }
    }

    public static String identifyStructureAt(ServerPlayer player) {
        for (Holder<Structure> holder : player.serverLevel().registryAccess().lookupOrThrow(Registries.STRUCTURE).listElements().toList()) {
            if (player.serverLevel().structureManager().getStructureWithPieceAt(player.blockPosition(), holder.value()).isValid()) {
                return holder.unwrapKey().map(k -> k.location().toString()).orElse(null);
            }
        }
        return null;
    }

    private static void cleanGeneratedWorldgen(Path root) throws IOException {
        Path dataPath = root.resolve("data");
        if (!Files.exists(dataPath)) return;
        try (var walk = Files.walk(dataPath)) {
            walk.sorted((a, b) -> b.getNameCount() - a.getNameCount()).forEach(path -> {
                if (!path.equals(dataPath)) {
                    try { Files.deleteIfExists(path); } catch (IOException ignored) {}
                }
            });
        }
    }

    private static void writePackMeta(Path root) throws IOException {
        JsonObject rootMeta = new JsonObject();
        JsonObject pack = new JsonObject();
        pack.addProperty("pack_format", 48);
        pack.addProperty("description", "No Void Structures generated datapack");
        rootMeta.add("pack", pack);
        Files.writeString(root.resolve("pack.mcmeta"), GSON.toJson(rootMeta));
    }

    private static boolean writeStructureJson(Path structuresPath, ResourceLocation id) {
        try {
            Path output = structuresPath.resolve(id.getPath() + ".json");
            Files.createDirectories(output.getParent());

            JsonObject json = new JsonObject();
            json.addProperty("type", id.toString());
            json.addProperty("biomes", "#minecraft:is_overworld");
            json.addProperty("step", "surface_structures");

            JsonObject terrain = new JsonObject();
            terrain.addProperty("adapt_noise", true);
            terrain.addProperty("underground_mode", NVSConfig.UNDERGROUND_STRUCTURES_MODE.get());
            terrain.addProperty("forced_underground", RuntimeRules.IGNORED_UNDERGROUND_STRUCTURES.contains(id.toString()));
            json.add("no_void_structures", terrain);
            json.add("spawn_overrides", new JsonArray());

            if (!json.has("type") || !json.has("step")) return false;
            Files.writeString(output, GSON.toJson(json));
            return true;
        } catch (IOException e) {
            LOGGER.warn("Could not write structure override for {}", id, e);
            return false;
        }
    }

    private static boolean writeFeatureJson(Path featuresPath, ResourceLocation id) {
        try {
            Path output = featuresPath.resolve(id.getPath() + ".json");
            Files.createDirectories(output.getParent());

            JsonObject json = new JsonObject();
            json.addProperty("feature", id.toString());
            JsonArray placement = new JsonArray();
            JsonObject placementRule = new JsonObject();
            placementRule.addProperty("type", "minecraft:height_range");
            placementRule.addProperty("heightmap", NVSConfig.UNDERGROUND_STRUCTURES_MODE.get() ? "minecraft:bottom_to_top" : "minecraft:world_surface_wg");
            placement.add(placementRule);
            json.add("placement", placement);
            if (!json.has("feature") || !json.has("placement")) return false;
            Files.writeString(output, GSON.toJson(json));
            return true;
        } catch (IOException e) {
            LOGGER.warn("Could not write feature override for {}", id, e);
            return false;
        }
    }
}
