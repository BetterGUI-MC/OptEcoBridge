package me.hsgamer.bettergui.optecobridge;

import me.hsgamer.bettergui.builder.CommandBuilder;
import me.hsgamer.bettergui.builder.RequirementBuilder;
import me.hsgamer.bettergui.manager.VariableManager;
import me.hsgamer.bettergui.object.addon.Addon;

public final class Main extends Addon {

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
    RequirementBuilder.register("point", PointIconRequirement.class);
    CommandBuilder.register("give-point:", GivePointsCommand.class);
    VariableManager.register("points", (player, s) -> String
        .valueOf(OptEcoBridge.getPoints(player)));
  }
}
