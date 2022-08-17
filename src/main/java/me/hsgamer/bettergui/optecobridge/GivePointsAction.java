package me.hsgamer.bettergui.optecobridge;

import me.hsgamer.bettergui.BetterGUI;
import me.hsgamer.bettergui.api.action.BaseAction;
import me.hsgamer.bettergui.builder.ActionBuilder;
import me.hsgamer.hscore.common.Validate;
import me.hsgamer.hscore.task.BatchRunnable;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;

import java.math.BigDecimal;
import java.util.Optional;
import java.util.UUID;

public final class GivePointsAction extends BaseAction {
    public GivePointsAction(ActionBuilder.Input input) {
        super(input);
    }

    @Override
    public void accept(UUID uuid, BatchRunnable.Process process) {
        String parsed = getReplacedString(uuid);
        Optional<Double> optionalPoint = Validate.getNumber(parsed).map(BigDecimal::doubleValue);
        if (!optionalPoint.isPresent()) {
            Optional.ofNullable(Bukkit.getPlayer(uuid)).ifPresent(player -> player.sendMessage(ChatColor.RED + "Invalid point amount: " + parsed));
            process.next();
            return;
        }
        double pointToGive = optionalPoint.get();
        if (pointToGive > 0) {
            Bukkit.getScheduler().runTask(BetterGUI.getInstance(), () -> {
                if (!OptEcoBridge.givePoints(uuid, pointToGive)) {
                    Optional.ofNullable(Bukkit.getPlayer(uuid)).ifPresent(player -> player.sendMessage(ChatColor.RED + "Error: the transaction couldn't be executed. Please inform the staff."));
                }
                process.next();
            });
        } else {
            process.next();
        }
    }
}
