package nl.thebathduck.vehiclegarage.utils;

import com.cryptomorin.xseries.XMaterial;
import com.gmail.filoghost.holographicdisplays.api.Hologram;
import com.gmail.filoghost.holographicdisplays.api.HologramsAPI;
import com.gmail.filoghost.holographicdisplays.api.line.ItemLine;
import com.gmail.filoghost.holographicdisplays.api.line.TextLine;
import nl.mtvehicles.core.infrastructure.models.Config;
import nl.thebathduck.vehiclegarage.VehicleGarage;
import nl.thebathduck.vehiclegarage.listeners.HologramHandler;
import nl.thebathduck.vehiclegarage.listeners.ImpoundHandler;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;

import java.util.ArrayList;

public class HoloUtils {

    private static ArrayList<Hologram> activeHolograms = new ArrayList<>();

    public static void destroyHolograms() {
        for(Hologram hologram : activeHolograms) {
            hologram.delete();
        }
        activeHolograms = new ArrayList<>();
    }

    public static void createHolograms() {

        if(VehicleGarage.getInstance().getConfig().getString("locations.impound") != null) {
            for(Location location : ConfigUtils.getHologramLocations("locations.impound")) {
                createImpoundHandler(location);
            }
        }

        if(VehicleGarage.getInstance().getConfig().getString("locations.takeout") != null) {
            for(Location location : ConfigUtils.getHologramLocations("locations.takeout")) {
                createTakeoutHologram(location);
            }
        }


    }
    public static void createTakeoutHologram(Location location) {
        Hologram hologram = HologramsAPI.createHologram(VehicleGarage.getInstance(), location);
        TextLine line1 = hologram.insertTextLine(0, Utils.color("&e&lVehicle Garage"));
        TextLine line2 = hologram.insertTextLine(1, Utils.color("&7Right-Click"));
        TextLine line3 = hologram.insertTextLine(2, Utils.color(""));
        ItemStack autoLine = new ItemBuilder(XMaterial.DIAMOND_HOE.parseMaterial())
                .makeUnbreakable(true)
                .setDurability((short) 1003)
                .toItemStack();

        ItemLine line4 = hologram.insertItemLine(3, autoLine);

        line1.setTouchHandler(new HologramHandler());
        line2.setTouchHandler(new HologramHandler());
        line3.setTouchHandler(new HologramHandler());
        line4.setTouchHandler(new HologramHandler());

        activeHolograms.add(hologram);
    }

    public static void createImpoundHandler(Location location) {
        Hologram hologram = HologramsAPI.createHologram(VehicleGarage.getInstance(), location);
        TextLine line1 = hologram.insertTextLine(0, Utils.color("&c&lImpound Garage"));
        TextLine line2 = hologram.insertTextLine(1, Utils.color("&7Right-Click"));
        TextLine line3 = hologram.insertTextLine(2, Utils.color(""));
        ItemStack autoLine = new ItemBuilder(XMaterial.DIAMOND_HOE.parseMaterial())
                .makeUnbreakable(true)
                .setDurability((short) 1005)
                .toItemStack();

        ItemLine line4 = hologram.insertItemLine(3, autoLine);

        line1.setTouchHandler(new ImpoundHandler());
        line2.setTouchHandler(new ImpoundHandler());
        line3.setTouchHandler(new ImpoundHandler());
        line4.setTouchHandler(new ImpoundHandler());

        activeHolograms.add(hologram);
    }

}
