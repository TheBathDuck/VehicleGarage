package nl.thebathduck.vehiclegarage.utils;

import nl.thebathduck.vehiclegarage.VehicleGarage;
import org.bukkit.Location;

import java.util.ArrayList;
import java.util.List;

public class ConfigUtils {

    public static List<Location> getHologramLocations(String path) {
        List<String> impoundList = VehicleGarage.getInstance().getConfig().getStringList(path);
        List<Location> locationsList = new ArrayList<>();
        if (impoundList == null) {
            impoundList = new ArrayList<>();
        }

        for (String stringLocation : impoundList) {
            Location loc = Utils.fromString(stringLocation);
            locationsList.add(loc);
        }
        return locationsList;
    }

    public static void addHologramLocation(String path, String locationString) {
        List<String> list = VehicleGarage.getInstance().getConfig().getStringList(path);
        if (list == null) {
            list = new ArrayList<>();
        }
        list.add(locationString);
        VehicleGarage.getInstance().getConfig().set(path, list);
        VehicleGarage.getInstance().saveConfig();
    }

    public static void removeHologramLocation(String path, int index) {
        List<String> list = VehicleGarage.getInstance().getConfig().getStringList(path);
        if (list == null) {
            list = new ArrayList<>();
        }
        list.remove(index);
        VehicleGarage.getInstance().getConfig().set(path, list);
        VehicleGarage.getInstance().saveConfig();
    }

    public static void set(String key, Object value) {
        VehicleGarage.getInstance().getConfig().set(key, value);
        VehicleGarage.getInstance().saveConfig();
    }

    public static String get(String key) {
        return VehicleGarage.getInstance().getVehicleConfig().getString(key);
    }

}
