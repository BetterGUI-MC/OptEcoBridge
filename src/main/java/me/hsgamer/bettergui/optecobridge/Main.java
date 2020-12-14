package me.hsgamer.bettergui.optecobridge;

import me.hsgamer.bettergui.api.addon.BetterGUIAddon;
import me.hsgamer.bettergui.builder.ActionBuilder;
import me.hsgamer.bettergui.builder.RequirementBuilder;
import me.hsgamer.bettergui.lib.core.variable.VariableManager;

public final class Main extends BetterGUIAddon {

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
        RequirementBuilder.INSTANCE.register(PointRequirement::new, "point");
        ActionBuilder.INSTANCE.register(GivePointsAction::new, "give-point", "givepoint");
        VariableManager.register("points", (original, uuid) -> String.valueOf(OptEcoBridge.getPoints(uuid)));
    }
}
