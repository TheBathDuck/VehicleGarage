package nl.thebathduck.vehiclegarage.utils;

import nl.thebathduck.vehiclegarage.VehicleGarage;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.World;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.Player;
import org.codemc.worldguardwrapper.WorldGuardWrapper;
import org.codemc.worldguardwrapper.flag.IWrappedFlag;
import org.codemc.worldguardwrapper.flag.WrappedState;
import org.codemc.worldguardwrapper.region.IWrappedRegion;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class Utils {
    private static WorldGuardWrapper wrapper;
    private static IWrappedFlag<WrappedState> takeInFlag;

    public static String color(String msg) {
        return ChatColor.translateAlternateColorCodes('&', msg);
    }

    public static List<String> getGaragePlates(Player player) {
        FileConfiguration config = VehicleGarage.getInstance().getVehicleConfig();
        List<String> plates = config.getStringList("vehicles." + player.getUniqueId().toString() + ".garage");
        if (plates == null) plates = new ArrayList<>();
        return plates;
    }

    public static void removePlateFromGarage(Player player, String licensePlate) {
        List<String> plates = getGaragePlates(player);
        plates.remove(licensePlate);
        set("vehicles." + player.getUniqueId().toString() + ".garage", plates);
    }

    public static void addPlateToGarage(Player player, String licensePlate) {
        List<String> plates = getGaragePlates(player);
        plates.add(licensePlate);
        set("vehicles." + player.getUniqueId().toString() + ".garage", plates);
    }

    public static String fromLocation(Location location) {
        String world = location.getWorld().getName();
        int x = location.getBlockX();
        int y = location.getBlockY();
        int z = location.getBlockZ();
        return world + ";" + x + ";" + y + ";" + z;
    }

    public static Location fromString(String string) {
        String[] splitted = string.split(";");

        World world = Bukkit.getWorld(splitted[0]);
        int x = Integer.parseInt(splitted[1]);
        int y = Integer.parseInt(splitted[2]);
        int z = Integer.parseInt(splitted[3]);
        return new Location(world, x, y, z);
    }

    public static void set(String key, Object value) {
        VehicleGarage.getInstance().getVehicleConfig().set(key, value);
        VehicleGarage.getInstance().getVehicleData().saveConfig();
    }

    public static String get(String key) {
        return VehicleGarage.getInstance().getVehicleConfig().getString(key);
    }


    public static void registerWGFlags() {
        wrapper = WorldGuardWrapper.getInstance();
        takeInFlag = wrapper.registerFlag("mtvg-takein", WrappedState.class, WrappedState.DENY).orElse(null);
    }

    public static boolean isTakein(Location location) {
        for (IWrappedRegion iwr : wrapper.getRegions(location)) {
            Object wrappedState = iwr.getFlag(takeInFlag).orElse(WrappedState.DENY);
            if (wrappedState.getClass().equals(Optional.class)) {
                wrappedState = ((Optional<WrappedState>) wrappedState).orElse(WrappedState.DENY);
            }
            if (wrappedState.equals(WrappedState.ALLOW)) return true;
        }
        return false;
    }


}
