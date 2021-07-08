package me.hsgamer.bettergui.optecobridge;

import me.playernguyen.opteco.OptEco;

import java.util.UUID;

public final class OptEcoBridge {

    private OptEcoBridge() {
    }

    public static double getPoints(UUID uuid) {
        return OptEco.getInstance().getApplicationInterface().getPoints(uuid);
    }

    public static boolean hasPoints(UUID uuid, double minimum) {
        return getPoints(uuid) >= minimum;
    }

    public static boolean takePoints(UUID uuid, double points) {
        return OptEco.getInstance().getApplicationInterface().takePoints(uuid, points);
    }

    public static boolean givePoints(UUID uuid, double points) {
        return OptEco.getInstance().getApplicationInterface().addPoints(uuid, points);
    }
}
