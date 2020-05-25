package me.hsgamer.bettergui.optecobridge;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;
import me.hsgamer.bettergui.config.impl.MessageConfig;
import me.hsgamer.bettergui.object.LocalVariable;
import me.hsgamer.bettergui.object.LocalVariableManager;
import me.hsgamer.bettergui.object.Requirement;
import me.hsgamer.bettergui.util.CommonUtils;
import me.hsgamer.bettergui.util.ExpressionUtils;
import org.bukkit.ChatColor;
import org.bukkit.OfflinePlayer;
import org.bukkit.entity.Player;

public final class PointIconRequirement extends Requirement<Object, Double> implements
    LocalVariable {

  private final Map<UUID, Double> checked = new HashMap<>();

  public PointIconRequirement() {
    super(true);
  }

  @Override
  public Double getParsedValue(Player player) {
    String parsed = parseFromString(String.valueOf(value).trim(), player);
    if (ExpressionUtils.isValidExpression(parsed)) {
      return ExpressionUtils.getResult(parsed).doubleValue();
    } else {
      CommonUtils
          .sendMessage(player, MessageConfig.INVALID_NUMBER.getValue().replace("{input}", parsed));
      return 0D;
    }
  }

  @Override
  public boolean check(Player player) {
    double points = getParsedValue(player);
    if (points > 0 && !OptEcoBridge.hasPoints(player, points)) {
      return false;
    } else {
      checked.put(player.getUniqueId(), points);
      return true;
    }
  }

  @Override
  public void take(Player player) {
    if (!OptEcoBridge.takePoints(player, checked.remove(player.getUniqueId()))) {
      player.sendMessage(ChatColor.RED
          + "Error: the transaction couldn't be executed. Please inform the staff.");
    }
  }

  @Override
  public String getIdentifier() {
    return "require_points";
  }

  @Override
  public LocalVariableManager<?> getInvolved() {
    return getVariableManager();
  }

  @Override
  public String getReplacement(OfflinePlayer player, String s) {
    if (!player.isOnline()) {
      return "";
    }
    double points = getParsedValue(player.getPlayer());
    if (points > 0 && !OptEcoBridge.hasPoints(player, points)) {
      return String.valueOf(points);
    }
    return MessageConfig.HAVE_MET_REQUIREMENT_PLACEHOLDER.getValue();
  }
}
