package me.fortis.waterland;

import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.util.Vector;

import java.util.HashSet;
import java.util.Set;
import java.util.UUID;

public class WaterLandPlugin extends JavaPlugin {

    private final Set<UUID> enabledPlayers = new HashSet<>();

    @Override
    public void onEnable() {

        Bukkit.getScheduler().runTaskTimer(this, () -> {

            for (Player player : Bukkit.getOnlinePlayers()) {

                if (!enabledPlayers.contains(player.getUniqueId()))
                    continue;

                if (!player.isInWater())
                    continue;

                Vector vel = player.getVelocity();

                // Simulate land gravity underwater
                vel.setY(vel.getY() - 0.08);

                // Reduce water drag
                vel.setX(vel.getX() * 1.12);
                vel.setZ(vel.getZ() * 1.12);

                player.setVelocity(vel);

                player.setSwimming(false);
            }

        }, 1L, 1L);

        getLogger().info("WaterLand enabled.");
    }

    @Override
    public boolean onCommand(
            CommandSender sender,
            Command command,
            String label,
            String[] args
    ) {

        if (args.length == 0) {
            sender.sendMessage("§6/waterland on|off|toggle");
            sender.sendMessage("§6/waterland <player> on|off");
            return true;
        }

        if (args.length == 1 && sender instanceof Player player) {

            if (!player.hasPermission("waterland.use")) {
                player.sendMessage("§cYou do not have permission to use this command.");
                return true;
            }

            switch(args[0].toLowerCase()) {

                case "toggle" -> {
                    toggle(player);
                    return true;
                }

                case "on" -> {
                    enable(player);
                    return true;
                }

                case "off" -> {
                    disable(player);
                    return true;
                }
            }
        }

        if (args.length == 2) {

            if (!sender.hasPermission("waterland.others")) {
                sender.sendMessage("§cYou do not have permission to modify other players.");
                return true;
            }

            Player target = Bukkit.getPlayer(args[0]);

            if (target == null) {
                sender.sendMessage("§cPlayer not found.");
                return true;
            }

            switch(args[1].toLowerCase()) {

                case "on" -> {
                    enable(target);
                    sender.sendMessage("§aEnabled WaterLand for " + target.getName());
                    return true;
                }

                case "off" -> {
                    disable(target);
                    sender.sendMessage("§cDisabled WaterLand for " + target.getName());
                    return true;
                }
            }
        }

        return true;
    }

    private void enable(Player player) {

        enabledPlayers.add(player.getUniqueId());

        player.sendMessage("§aWaterLand enabled.");
    }

    private void disable(Player player) {

        enabledPlayers.remove(player.getUniqueId());

        player.sendMessage("§cWaterLand disabled.");
    }

    private void toggle(Player player) {

        if (enabledPlayers.contains(player.getUniqueId())) {
            disable(player);
        } else {
            enable(player);
        }
    }
}
