package me.hsgamer.bettergui.optecobridge;

import me.hsgamer.bettergui.builder.ActionBuilder;
import me.hsgamer.bettergui.builder.RequirementBuilder;
import me.hsgamer.hscore.bukkit.addon.PluginAddon;
import me.hsgamer.hscore.variable.VariableManager;

public final class Main extends PluginAddon {

    @Override
    public boolean onLoad() {
        if (getPlugin().getServer().getPluginManager().getPlugin("PlayerPoints") != null) {
            getPlugin().getLogger().warning("Dude! Why are you still using PlayerPoints");
            getPlugin().getLogger().warning("OptEco is literally better");
            return false;
        } else {
            return true;
        }
    }

    @Override
    public void onEnable() {
        OptEcoBridge.setup();
        RequirementBuilder.INSTANCE.register(PointRequirement::new, "point");
        ActionBuilder.INSTANCE.register(GivePointsAction::new, "give-point", "givepoint");
        VariableManager.register("points", (original, uuid) -> String.valueOf(OptEcoBridge.getPoints(uuid)));
    }
}
