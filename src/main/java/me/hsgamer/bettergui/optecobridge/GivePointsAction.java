package me.hsgamer.bettergui.optecobridge;

import me.hsgamer.bettergui.api.action.BaseAction;
import me.hsgamer.bettergui.config.MessageConfig;
import me.hsgamer.bettergui.lib.core.bukkit.utils.MessageUtils;
import me.hsgamer.bettergui.lib.core.common.Validate;
import me.hsgamer.bettergui.lib.core.expression.ExpressionUtils;
import me.hsgamer.bettergui.lib.taskchain.TaskChain;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;

import java.util.Objects;
import java.util.Optional;
import java.util.UUID;

public final class GivePointsAction extends BaseAction {

    public GivePointsAction(String string) {
        super(string);
    }

    @Override
    public void addToTaskChain(UUID uuid, TaskChain<?> taskChain) {
        int pointsToGive = 0;
        String replaced = getReplacedString(uuid);
        if (Validate.isValidPositiveNumber(replaced)) {
            pointsToGive = Integer.parseInt(replaced);
        } else if (ExpressionUtils.isValidExpression(replaced)) {
            pointsToGive = Objects.requireNonNull(ExpressionUtils.getResult(replaced)).intValue();
        } else {
            Optional.ofNullable(Bukkit.getPlayer(uuid)).ifPresent(player -> MessageUtils.sendMessage(player, MessageConfig.INVALID_NUMBER.getValue().replace("{input}", replaced)));
        }

        if (pointsToGive > 0) {
            int finalPointsToGive = pointsToGive;
            taskChain.sync(() -> {
                if (!OptEcoBridge.givePoints(uuid, finalPointsToGive)) {
                    Optional.ofNullable(Bukkit.getPlayer(uuid)).ifPresent(player -> player.sendMessage(ChatColor.RED + "Error: the transaction couldn't be executed. Please inform the staff."));
                }
            });
        }
    }
}
