package nl.thebathduck.vehiclegarage.commands;

import com.gmail.filoghost.holographicdisplays.api.Hologram;
import com.gmail.filoghost.holographicdisplays.api.HologramsAPI;
import nl.mtvehicles.core.infrastructure.models.Config;
import nl.thebathduck.vehiclegarage.VehicleGarage;
import nl.thebathduck.vehiclegarage.utils.ConfigUtils;
import nl.thebathduck.vehiclegarage.utils.HoloUtils;
import nl.thebathduck.vehiclegarage.utils.Utils;
import org.bukkit.Location;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import java.util.Locale;

public class HologramCommands implements CommandExecutor {
    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if(!(sender instanceof Player)) return false;
        Player player = (Player) sender;

        if(!player.hasPermission("vehiclegarage.locations")) {
            player.sendMessage(Utils.color("&cJij heb hier geen permissies voor!"));
            return false;
        }


        // garagelocation add spawn
        if(args.length == 1 && args[0].equalsIgnoreCase("addgaragepoint")) {
            HoloUtils.destroyHolograms();
            ConfigUtils.addHologramLocation("locations.takeout", Utils.fromLocation(player.getLocation().add(0.5, 2, 0.5)));
            player.sendMessage(Utils.color("&2Jouw positie is nu toegevoegd!"));
            HoloUtils.createHolograms();
            return false;
        }
        if(args.length == 1 && args[0].equalsIgnoreCase("addimpoundpoint")) {
            HoloUtils.destroyHolograms();
            ConfigUtils.addHologramLocation("locations.impound", Utils.fromLocation(player.getLocation().add(0.5, 2, 0.5)));
            player.sendMessage(Utils.color("&2Jouw positie is nu toegevoegd!"));
            HoloUtils.createHolograms();
            return false;
        }

        if(args.length == 1 && args[0].equalsIgnoreCase("impoundlist")) {
            player.sendMessage(Utils.color("&7Impound Holograms:"));
            int index = 0;
            for(Location location : ConfigUtils.getHologramLocations("locations.impound")) {
                int x = location.getBlockX();
                int y = location.getBlockY();
                int z = location.getBlockZ();
                String world = location.getWorld().getName();
                player.sendMessage(Utils.color(" &7- &7Id: &f" + index + "&7, x: &f" + x + "&7, y: &f" + y + "&7, z: &f" + z + "&7, world: &f" + world));
                index++;
            }
            return false;
        }
        if(args.length == 1 && args[0].equalsIgnoreCase("garagelist")) {
            player.sendMessage(Utils.color("&7Garage Holograms:"));
            int index = 0;
            for(Location location : ConfigUtils.getHologramLocations("locations.takeout")) {
                int x = location.getBlockX();
                int y = location.getBlockY();
                int z = location.getBlockZ();
                String world = location.getWorld().getName();
                player.sendMessage(Utils.color(" &7- &7Id: &f" + index + "&7, x: &f" + x + "&7, y: &f" + y + "&7, z: &f" + z + "&7, world: &f" + world));
                index++;
            }
            return false;
        }
        if(args.length == 2 && args[0].equalsIgnoreCase("removeimpound")) {
            try {
                Integer.parseInt(args[1]);
            } catch (NumberFormatException e) {
                player.sendMessage(Utils.color("&cJe moet wel een nummer opgeven!"));
                return false;
            }
            int index = Integer.parseInt(args[1]);
            ConfigUtils.removeHologramLocation("locations.impound", index);
            player.sendMessage(Utils.color("&2Impound met id &a"+ index + " &2is verwijderd."));
            HoloUtils.destroyHolograms();
            HoloUtils.createHolograms();
            return false;
        }
        if(args.length == 2 && args[0].equalsIgnoreCase("removegarage")) {
            try {
                Integer.parseInt(args[1]);
            } catch (NumberFormatException e) {
                player.sendMessage(Utils.color("&cJe moet wel een nummer opgeven!"));
                return false;
            }
            int index = Integer.parseInt(args[1]);
            ConfigUtils.removeHologramLocation("locations.takeout", index);
            player.sendMessage(Utils.color("&2Garage met id &a"+ index + " &2is verwijderd."));
            HoloUtils.destroyHolograms();
            HoloUtils.createHolograms();
            return false;
        }

        sendHelp(player, args.length);
        return false;
    }

    private void sendHelp(Player player, int length) {
        player.sendMessage(Utils.color("&cSomething went wrong, argument length was " + length));
    }
}
