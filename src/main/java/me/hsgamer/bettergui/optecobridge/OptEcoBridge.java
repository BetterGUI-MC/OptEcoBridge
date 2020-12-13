package me.hsgamer.bettergui.optecobridge;

import me.playernguyen.opteco.api.OptEcoAPI;

import java.util.UUID;

public final class OptEcoBridge {

    private OptEcoBridge() {
    }

    public static double getPoints(UUID uuid) {
        return new OptEcoAPI(uuid).getPoints();
    }

    public static boolean hasPoints(UUID uuid, double minimum) {
        return getPoints(uuid) >= minimum;
    }

    public static boolean takePoints(UUID uuid, double points) {
        return new OptEcoAPI(uuid).takePoints(points);
    }

    public static boolean givePoints(UUID uuid, double points) {
        return new OptEcoAPI(uuid).addPoints(points);
    }
}
