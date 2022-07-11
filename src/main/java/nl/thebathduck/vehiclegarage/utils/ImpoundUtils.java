package nl.thebathduck.vehiclegarage.utils;

import io.github.bananapuncher714.nbteditor.NBTEditor;
import nl.mtvehicles.core.infrastructure.models.Vehicle;
import nl.mtvehicles.core.infrastructure.models.VehicleUtils;
import nl.thebathduck.vehiclegarage.VehicleGarage;
import nl.thebathduck.vehiclegarage.tasks.ImpoundTask;
import org.bukkit.Bukkit;
import org.bukkit.World;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.ArmorStand;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.scheduler.BukkitTask;

import java.util.ArrayList;
import java.util.List;

public class ImpoundUtils {


    private static List<Vehicle> getVehicles(World world) {
        List<Vehicle> vehicles = new ArrayList<>();
        for(Entity entity : world.getEntities()) {
            if(!(entity instanceof ArmorStand)) continue;
            if(entity.getCustomName() == null) continue;
            if(!(entity.getCustomName().contains("MTVEHICLES_"))) continue;
            if(entity.getCustomName().contains("SKIN")) continue;
            ArmorStand stand = (ArmorStand) entity;
            ItemStack head = stand.getHelmet();
            String licensePlate = NBTEditor.getString(head, "mtvehicles.kenteken");
            Vehicle vehicle = VehicleUtils.getVehicle(licensePlate);
            vehicles.add(vehicle);
        }
        return vehicles;
    }

    public static void runTask(List<World> worlds) {
        Bukkit.getScheduler().runTaskLaterAsynchronously(VehicleGarage.getInstance(), new ImpoundTask(worlds), 20*5);
    }

    public static List<String> getImpoundPlates(String uuid) {
        FileConfiguration config = VehicleGarage.getInstance().getVehicleConfig();
        List<String> plates = config.getStringList("vehicles." + uuid + ".impound");
        if (plates == null) plates = new ArrayList<>();
        return plates;
    }

    public static void removeImpoundPlate(String uuid, String licensePlate) {
        List<String> plates = getImpoundPlates(uuid);
        plates.remove(licensePlate);
        set("vehicles." + uuid + ".impound", plates);
    }

    public static void addPlateToImpound(String uuid, String licensePlate) {
        List<String> plates = getImpoundPlates(uuid);
        plates.add(licensePlate);
        set("vehicles." + uuid + ".impound", plates);
    }

    public static void set(String key, Object value) {
        VehicleGarage.getInstance().getVehicleConfig().set(key, value);
        VehicleGarage.getInstance().getVehicleData().saveConfig();
    }


}
