package me.hsgamer.bettergui.optecobridge;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;
import java.util.UUID;
import me.hsgamer.bettergui.BetterGUI;
import me.hsgamer.bettergui.config.impl.MessageConfig.DefaultMessage;
import me.hsgamer.bettergui.object.Icon;
import me.hsgamer.bettergui.object.LocalVariable;
import me.hsgamer.bettergui.object.Requirement;
import me.hsgamer.bettergui.util.CommonUtils;
import me.hsgamer.bettergui.util.ExpressionUtils;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;

public final class PointIconRequirement extends Requirement<Object, Double> implements LocalVariable<Icon> {

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
      CommonUtils.sendMessage(player,
          BetterGUI.getInstance().getMessageConfig().get(DefaultMessage.INVALID_NUMBER)
              .replace("{input}", parsed));
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
  public Optional<Icon> getInvolved() {
    return getIcon();
  }

  @Override
  public String getReplacement(Player player, String s) {
    double points = getParsedValue(player);
    if (points > 0 && !OptEcoBridge.hasPoints(player, points)) {
      return String.valueOf(points);
    }
    return BetterGUI.getInstance().getMessageConfig()
        .get(DefaultMessage.HAVE_MET_REQUIREMENT_PLACEHOLDER);
  }
}
