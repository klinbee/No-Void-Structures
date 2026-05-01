package com.novoidstructures;

import com.mojang.brigadier.CommandDispatcher;
import com.mojang.brigadier.arguments.StringArgumentType;
import net.minecraft.commands.CommandSourceStack;
import net.minecraft.commands.Commands;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerPlayer;

import java.util.Set;

public final class NVSCommands {
    private NVSCommands() {}

    public static void register(CommandDispatcher<CommandSourceStack> dispatcher) {
        dispatcher.register(Commands.literal("nvs")
                .requires(s -> s.hasPermission(2))
                .then(Commands.literal("identify")
                        .executes(ctx -> identify(ctx.getSource())))
                .then(Commands.literal("reload")
                        .executes(ctx -> reload(ctx.getSource())))
                .then(Commands.literal("ignore")
                        .then(Commands.literal("here")
                                .then(Commands.literal("normal").executes(ctx -> toggleHere(ctx.getSource(), RuntimeRules.IGNORED_STRUCTURES)))
                                .then(Commands.literal("underground").executes(ctx -> toggleHere(ctx.getSource(), RuntimeRules.IGNORED_UNDERGROUND_STRUCTURES)))
                                .then(Commands.literal("feature").executes(ctx -> toggleHere(ctx.getSource(), RuntimeRules.IGNORED_FEATURES))))
                        .then(Commands.literal("namespace")
                                .then(Commands.argument("namespace", StringArgumentType.word())
                                        .executes(ctx -> toggleNamespace(ctx.getSource(), StringArgumentType.getString(ctx, "namespace")))))));
    }

    private static int identify(CommandSourceStack source) {
        ServerPlayer player = source.getPlayer();
        if (player == null) return 0;
        String id = DynamicPackGenerator.identifyStructureAt(player);
        source.sendSuccess(() -> Component.literal(id == null ? "No se detectó estructura en tu posición" : "Estructura detectada: " + id), false);
        return 1;
    }

    private static int reload(CommandSourceStack source) {
        RuntimeRules.load();
        DynamicPackGenerator.generate(source.getServer().registryAccess(), source.getServer().getWorldData().worldGenOptions());
        source.sendSuccess(() -> Component.literal("NoVoidStructures: reglas recargadas y datapack regenerado."), true);
        return 1;
    }

    private static int toggleNamespace(CommandSourceStack source, String ns) {
        if (RuntimeRules.IGNORED_NAMESPACES.contains(ns)) RuntimeRules.IGNORED_NAMESPACES.remove(ns);
        else RuntimeRules.IGNORED_NAMESPACES.add(ns);
        RuntimeRules.save();
        source.sendSuccess(() -> Component.literal("Namespace toggled: " + ns), true);
        return 1;
    }

    private static int toggleHere(CommandSourceStack source, Set<String> targetSet) {
        ServerPlayer player = source.getPlayer();
        if (player == null) return 0;

        String id = DynamicPackGenerator.identifyStructureAt(player);
        if (id == null) {
            source.sendFailure(Component.literal("No se encontró estructura en tu posición."));
            return 0;
        }
        if (targetSet.contains(id)) targetSet.remove(id); else targetSet.add(id);
        RuntimeRules.save();
        source.sendSuccess(() -> Component.literal("Toggled ignore para: " + id), true);
        return 1;
    }
}
