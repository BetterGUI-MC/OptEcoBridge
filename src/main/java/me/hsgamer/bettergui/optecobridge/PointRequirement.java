package me.hsgamer.bettergui.optecobridge;

import me.hsgamer.bettergui.BetterGUI;
import me.hsgamer.bettergui.api.requirement.TakableRequirement;
import me.hsgamer.bettergui.builder.RequirementBuilder;
import me.hsgamer.bettergui.util.StringReplacerApplier;
import me.hsgamer.hscore.bukkit.utils.MessageUtils;
import me.hsgamer.hscore.common.Validate;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;

import java.math.BigDecimal;
import java.util.Optional;
import java.util.UUID;

public final class PointRequirement extends TakableRequirement<Double> {
    public PointRequirement(RequirementBuilder.Input input) {
        super(input);
        getMenu().getVariableManager().register(getName(), (original, uuid) -> {
            double points = getFinalValue(uuid);
            if (points > 0 && !OptEcoBridge.hasPoints(uuid, points)) {
                return String.valueOf(points);
            }
            return BetterGUI.getInstance().getMessageConfig().haveMetRequirementPlaceholder;
        });
    }

    @Override
    protected boolean getDefaultTake() {
        return true;
    }

    @Override
    protected Object getDefaultValue() {
        return "0";
    }

    @Override
    protected Double convert(Object value, UUID uuid) {
        String parsed = StringReplacerApplier.replace(String.valueOf(value).trim(), uuid, this);
        return Validate.getNumber(parsed).map(BigDecimal::doubleValue).orElseGet(() -> {
            Optional.ofNullable(Bukkit.getPlayer(uuid)).ifPresent(player -> MessageUtils.sendMessage(player, BetterGUI.getInstance().getMessageConfig().invalidNumber.replace("{input}", parsed)));
            return 0D;
        });
    }

    @Override
    protected Result checkConverted(UUID uuid, Double value) {
        if (value > 0 && !OptEcoBridge.hasPoints(uuid, value)) {
            return Result.fail();
        }
        return successConditional((uuid1, process) -> Bukkit.getScheduler().runTask(BetterGUI.getInstance(), () -> {
            if (!OptEcoBridge.takePoints(uuid1, value)) {
                Optional.ofNullable(Bukkit.getPlayer(uuid1)).ifPresent(player -> player.sendMessage(ChatColor.RED + "Error: the transaction couldn't be executed. Please inform the staff."));
            }
            process.next();
        }));
    }
}
