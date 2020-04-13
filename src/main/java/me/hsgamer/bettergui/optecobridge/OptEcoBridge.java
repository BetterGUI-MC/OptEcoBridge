package me.hsgamer.bettergui.optecobridge;

import me.playernguyen.opteco.api.OptEcoAPI;
import org.bukkit.entity.Player;

public final class OptEcoBridge {

  private OptEcoBridge() {
  }

  public static double getPoints(Player player) {
    return new OptEcoAPI(player.getUniqueId()).getPoints();
  }

  public static boolean hasPoints(Player player, double minimum) {
    return getPoints(player) >= minimum;
  }

  public static boolean takePoints(Player player, double points) {
    return new OptEcoAPI(player.getUniqueId()).takePoints(points);
  }

  public static boolean givePoints(Player player, double points) {
    return new OptEcoAPI(player.getUniqueId()).addPoints(points);
  }
}
