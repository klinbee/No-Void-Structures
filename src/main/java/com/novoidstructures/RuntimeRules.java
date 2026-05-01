package com.novoidstructures;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonObject;
import com.google.gson.reflect.TypeToken;
import net.neoforged.fml.loading.FMLPaths;

import java.io.IOException;
import java.lang.reflect.Type;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.HashSet;
import java.util.Set;

public final class RuntimeRules {
    private static final Gson GSON = new GsonBuilder().setPrettyPrinting().create();
    private static final Type SET_TYPE = new TypeToken<Set<String>>() {}.getType();

    public static final Set<String> IGNORED_STRUCTURES = new HashSet<>();
    public static final Set<String> IGNORED_UNDERGROUND_STRUCTURES = new HashSet<>();
    public static final Set<String> IGNORED_FEATURES = new HashSet<>();
    public static final Set<String> IGNORED_NAMESPACES = new HashSet<>();

    private static final Path FILE = FMLPaths.CONFIGDIR.get().resolve("no_void_structures_runtime_rules.json");

    private RuntimeRules() {}

    public static void load() {
        if (!Files.exists(FILE)) {
            save();
            return;
        }
        try {
            JsonObject root = GSON.fromJson(Files.readString(FILE), JsonObject.class);
            IGNORED_STRUCTURES.clear();
            IGNORED_UNDERGROUND_STRUCTURES.clear();
            IGNORED_FEATURES.clear();
            IGNORED_NAMESPACES.clear();

            if (root.has("ignored_structures")) IGNORED_STRUCTURES.addAll(GSON.fromJson(root.get("ignored_structures"), SET_TYPE));
            if (root.has("ignored_underground_structures")) IGNORED_UNDERGROUND_STRUCTURES.addAll(GSON.fromJson(root.get("ignored_underground_structures"), SET_TYPE));
            if (root.has("ignored_features")) IGNORED_FEATURES.addAll(GSON.fromJson(root.get("ignored_features"), SET_TYPE));
            if (root.has("ignored_namespaces")) IGNORED_NAMESPACES.addAll(GSON.fromJson(root.get("ignored_namespaces"), SET_TYPE));
        } catch (Exception ignored) {
            save();
        }
    }

    public static void save() {
        JsonObject root = new JsonObject();
        root.add("ignored_structures", GSON.toJsonTree(IGNORED_STRUCTURES, SET_TYPE));
        root.add("ignored_underground_structures", GSON.toJsonTree(IGNORED_UNDERGROUND_STRUCTURES, SET_TYPE));
        root.add("ignored_features", GSON.toJsonTree(IGNORED_FEATURES, SET_TYPE));
        root.add("ignored_namespaces", GSON.toJsonTree(IGNORED_NAMESPACES, SET_TYPE));
        try {
            Files.writeString(FILE, GSON.toJson(root));
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public static boolean isNamespaceIgnored(String id) {
        int idx = id.indexOf(':');
        return idx > 0 && IGNORED_NAMESPACES.contains(id.substring(0, idx));
    }
}
