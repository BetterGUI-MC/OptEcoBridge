package me.hsgamer.bettergui.optecobridge;

import com.playernguyen.optecoprime.OptEcoPrime;
import org.bukkit.Bukkit;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.UUID;
import java.util.concurrent.ExecutionException;
import java.util.logging.Level;

public final class OptEcoBridge {
    private static OptEcoPrime optEcoPrime;

    private OptEcoBridge() {
    }

    public static void setup() {
        optEcoPrime = JavaPlugin.getPlugin(OptEcoPrime.class);
        if (optEcoPrime == null) {
            throw new IllegalStateException("OptEcoPrime is not loaded");
        }
    }

    public static double getPoints(UUID uuid) {
        try {
            return optEcoPrime.getPlayerManager().getPlayer(uuid).getBalance();
        } catch (ExecutionException | InterruptedException e) {
            Bukkit.getLogger().log(Level.WARNING, e.getMessage(), e);
            return 0;
        }
    }

    public static boolean hasPoints(UUID uuid, double minimum) {
        return getPoints(uuid) >= minimum;
    }

    public static boolean takePoints(UUID uuid, double points) {
        try {
            optEcoPrime.getPlayerManager().takePlayerBalance(uuid, points);
            return true;
        } catch (Exception e) {
            Bukkit.getLogger().log(Level.WARNING, e.getMessage(), e);
            return false;
        }
    }

    public static boolean givePoints(UUID uuid, double points) {
        try {
            optEcoPrime.getPlayerManager().addPlayerBalance(uuid, points);
            return true;
        } catch (Exception e) {
            Bukkit.getLogger().log(Level.WARNING, e.getMessage(), e);
            return false;
        }
    }
}
