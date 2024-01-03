package nl.thebathduck.vehiclegarage.commands;

import io.github.bananapuncher714.nbteditor.NBTEditor;
import nl.mtvehicles.core.infrastructure.vehicle.Vehicle;
import nl.mtvehicles.core.infrastructure.vehicle.VehicleUtils;
import nl.thebathduck.vehiclegarage.menu.GarageMenu;
import nl.thebathduck.vehiclegarage.utils.Utils;
import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

public class GarageCommand implements CommandExecutor {
    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        Player player = (Player) sender;
        if (!player.hasPermission("garage.manage")) {
            player.sendMessage(Utils.color("&cYou don't have permissions to execute this!"));
            return false;
        }

        if (args.length == 2 && args[0].equalsIgnoreCase("addvehicle")) {
            Player target = Bukkit.getPlayer(args[1]);

            if (target == null || !target.isOnline()) {
                player.sendMessage(Utils.color("&cDeze speler is niet online!"));
                return false;
            }

            ItemStack handCar = player.getInventory().getItemInMainHand();
            String licensePlate = NBTEditor.getString(handCar, "mtvehicles.kenteken");
            Vehicle vehicle = VehicleUtils.getVehicle(licensePlate);


            player.sendMessage(Utils.color("&2Het voertuig &a" + vehicle.getName() + " &2is toegevoegd aan &a" + target.getName() + "&2's garage."));
            Utils.addPlateToGarage(player, licensePlate);
            handCar.setAmount(0);
            return false;
        }

        if (args.length == 1 && args[0].equalsIgnoreCase("open")) {
            new GarageMenu(player, 0);
            return false;
        }

        sendHelp(player);
        return false;
    }


    private void sendHelp(Player player) {
        player.sendMessage(Utils.color("&7Commands:"));
        player.sendMessage(Utils.color("&6/vehicle &eaddvehicle <Player> &7- &6Add a vehicle to player's garage."));
        player.sendMessage(Utils.color("&6/vehicle &eopen &7- &6Open your garage."));
        player.sendMessage(Utils.color(""));
        player.sendMessage(Utils.color("&7Hologram:"));
        player.sendMessage(Utils.color("&6/garagelocation &ehologram &7- &6Spawn the hologram."));
        player.sendMessage(Utils.color(""));
        player.sendMessage(Utils.color("&7Setup Takeout Region:"));
        player.sendMessage(Utils.color("&6/rg flag &e<Region> &emtvg-takein allow &7- &6Allows takein for vehicles."));
        player.sendMessage(Utils.color(""));
    }

}
