package nl.thebathduck.vehiclegarage.commands;

import nl.thebathduck.vehiclegarage.VehicleGarage;
import nl.thebathduck.vehiclegarage.menu.ImpoundMenu;
import nl.thebathduck.vehiclegarage.tasks.ImpoundTask;
import nl.thebathduck.vehiclegarage.utils.Utils;
import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class ImpoundCommand implements CommandExecutor {
    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if (!(sender instanceof Player)) return false;
        Player player = (Player) sender;

        if (!player.hasPermission("impound.manage")) {
            player.sendMessage(Utils.color("&cJij heb hier geen permissies voor!"));
            return false;
        }

        if (args[0].equalsIgnoreCase("open")) {
            new ImpoundMenu(player, 0);
        }

        if (args[0].equalsIgnoreCase("force")) {
            player.sendMessage(Utils.color("&cAlle voertuigen in je wereld worden in de impound gezet."));

            Bukkit.getScheduler().runTaskAsynchronously(VehicleGarage.getInstance(), new ImpoundTask(player.getWorld()));

        }


        return false;
    }
}
