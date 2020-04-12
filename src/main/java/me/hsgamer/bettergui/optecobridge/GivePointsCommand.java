package me.hsgamer.bettergui.optecobridge;

import java.util.Objects;
import me.hsgamer.bettergui.BetterGUI;
import me.hsgamer.bettergui.config.impl.MessageConfig.DefaultMessage;
import me.hsgamer.bettergui.lib.taskchain.TaskChain;
import me.hsgamer.bettergui.object.Command;
import me.hsgamer.bettergui.util.CommonUtils;
import me.hsgamer.bettergui.util.ExpressionUtils;
import me.hsgamer.bettergui.util.Validate;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;

public final class GivePointsCommand extends Command {

  public GivePointsCommand(String string) {
    super(string);
  }

  @Override
  public void addToTaskChain(Player player, TaskChain<?> taskChain) {
    int pointsToGive = 0;
    String parsed = getParsedCommand(player);
    if (Validate.isValidPositiveNumber(parsed)) {
      pointsToGive = Integer.parseInt(parsed);
    } else if (ExpressionUtils.isValidExpression(parsed)) {
      pointsToGive = Objects.requireNonNull(ExpressionUtils.getResult(parsed)).intValue();
    } else {
      CommonUtils.sendMessage(player,
          BetterGUI.getInstance().getMessageConfig().get(DefaultMessage.INVALID_NUMBER)
              .replace("{input}", parsed));
    }

    if (pointsToGive > 0) {
      int finalPointsToGive = pointsToGive;
      taskChain.sync(() -> {
        if (!OptEcoBridge.givePoints(player, finalPointsToGive)) {
          player.sendMessage(ChatColor.RED
              + "Error: the transaction couldn't be executed. Please inform the staff.");
        }
      });
    }
  }
}