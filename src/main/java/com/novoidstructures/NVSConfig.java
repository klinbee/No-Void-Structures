package com.novoidstructures;

import net.neoforged.neoforge.common.ModConfigSpec;

import java.util.List;

public class NVSConfig {
    public static final ModConfigSpec SPEC;

    public static final ModConfigSpec.BooleanValue ENABLE_STRUCTURE_PROCESSING;
    public static final ModConfigSpec.BooleanValue ENABLE_FEATURE_PROCESSING;
    public static final ModConfigSpec.BooleanValue UNDERGROUND_STRUCTURES_MODE;
    public static final ModConfigSpec.ConfigValue<List<? extends String>> STRUCTURE_EXCEPTIONS;
    public static final ModConfigSpec.ConfigValue<List<? extends String>> FEATURE_EXCEPTIONS;

    static {
        ModConfigSpec.Builder builder = new ModConfigSpec.Builder();

        builder.push("general");
        ENABLE_STRUCTURE_PROCESSING = builder
                .comment("Enable dynamic datapack generation for structures.")
                .define("enableStructureProcessing", true);
        ENABLE_FEATURE_PROCESSING = builder
                .comment("Enable dynamic datapack generation for configured/worldgen features.")
                .define("enableFeatureProcessing", false);
        UNDERGROUND_STRUCTURES_MODE = builder
                .comment("When enabled, structures are treated as underground-oriented and use conservative height limits.")
                .define("undergroundStructuresMode", false);
        builder.pop();

        builder.push("filters");
        STRUCTURE_EXCEPTIONS = builder
                .comment("Registry names of structures to skip. Example: minecraft:end_city")
                .defineListAllowEmpty("structureExceptions", List.of(), o -> o instanceof String s && s.contains(":"));
        FEATURE_EXCEPTIONS = builder
                .comment("Registry names of placed features to skip. Example: minecraft:ice_spike")
                .defineListAllowEmpty("featureExceptions", List.of(), o -> o instanceof String s && s.contains(":"));
        builder.pop();

        SPEC = builder.build();
    }
}
