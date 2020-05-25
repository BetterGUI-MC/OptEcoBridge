package me.hsgamer.bettergui.optecobridge;

import me.playernguyen.opteco.api.OptEcoAPI;
import org.bukkit.OfflinePlayer;

public final class OptEcoBridge {

  private OptEcoBridge() {
  }

  public static double getPoints(OfflinePlayer player) {
    return new OptEcoAPI(player.getUniqueId()).getPoints();
  }

  public static boolean hasPoints(OfflinePlayer player, double minimum) {
    return getPoints(player) >= minimum;
  }

  public static boolean takePoints(OfflinePlayer player, double points) {
    return new OptEcoAPI(player.getUniqueId()).takePoints(points);
  }

  public static boolean givePoints(OfflinePlayer player, double points) {
    return new OptEcoAPI(player.getUniqueId()).addPoints(points);
  }
}
