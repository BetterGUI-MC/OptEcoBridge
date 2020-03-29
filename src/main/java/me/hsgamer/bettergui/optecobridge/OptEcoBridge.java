package me.hsgamer.bettergui.optecobridge;

import me.playernguyen.OptEco;
import org.bukkit.entity.Player;

public class OptEcoBridge {

  private static OptEco optEco;

  private OptEcoBridge() {
  }

  public static void setupPlugin() {
    optEco = OptEco.getPlugin();
  }

  public static double getPoints(Player player) {
    return optEco.getAccountLoader().getBalance(player.getUniqueId());
  }

  public static boolean hasPoints(Player player, double minimum) {
    return getPoints(player) >= minimum;
  }

  public static boolean takePoints(Player player, double points) {
    return optEco.getAccountLoader().takeBalance(player.getUniqueId(), points);
  }

  public static boolean givePoints(Player player, double points) {
    return optEco.getAccountLoader().addBalance(player.getUniqueId(), points);
  }
}
