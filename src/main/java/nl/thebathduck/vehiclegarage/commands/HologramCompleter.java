package nl.thebathduck.vehiclegarage.commands;

import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.command.TabCompleter;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class HologramCompleter implements TabCompleter {
    @Override
    public List<String> onTabComplete(CommandSender sender, Command command, String alias, String[] args) {
        List<String> list = new ArrayList<>();
        list.add("addgaragepoint");
        list.add("addimpoundpoint");
        list.add("impoundlist");
        list.add("garagelist");
        list.add("removegarage");
        list.add("removeimpound");
        return list;
    }
}
