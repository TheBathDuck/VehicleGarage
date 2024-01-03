package nl.thebathduck.vehiclegarage.menu;

import io.github.bananapuncher714.nbteditor.NBTEditor;
import nl.mtvehicles.core.infrastructure.vehicle.Vehicle;
import nl.mtvehicles.core.infrastructure.vehicle.VehicleUtils;
import nl.thebathduck.vehiclegarage.utils.GUIHolder;
import nl.thebathduck.vehiclegarage.utils.ItemBuilder;
import nl.thebathduck.vehiclegarage.utils.Utils;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.ItemFlag;
import org.bukkit.inventory.ItemStack;

import java.util.ArrayList;

public class GarageMenu extends GUIHolder {
    ItemFlag[] flags = {ItemFlag.HIDE_ATTRIBUTES, ItemFlag.HIDE_UNBREAKABLE};

    public GarageMenu(Player player, int page) {
        int pageSize = 9 * 5;
        ArrayList<String> licensePlates = new ArrayList<>(Utils.getGaragePlates(player));

        this.inventory = Bukkit.createInventory(this, 6 * 9, "Je Garage");
        player.openInventory(inventory);

        for (int i = 0; i < Math.min(licensePlates.size() - page * pageSize, pageSize); i++) {
            int index = i + page * pageSize;
            String plate = licensePlates.get(index);
            Vehicle vehicle = VehicleUtils.getVehicle(plate);

            ItemStack carItem = new ItemBuilder(Material.valueOf(vehicle.getSkinItem()))
                    .setColoredName("&6" + vehicle.getName())
                    .setDurability((short) vehicle.getSkinDamage())
                    .addLoreLine(Utils.color(""))
                    .addLoreLine(Utils.color("&7Kenteken: &f" + vehicle.getLicensePlate()))
                    .addLoreLine(Utils.color("&7Maximale Snelheid: &f" + vehicle.getMaxSpeed()))
                    .addLoreLine(Utils.color("&7Versnelling Snelheid: &f" + vehicle.getAccelerationSpeed()))
                    .addLoreLine(Utils.color("&7Rem Snelheid: &f" + vehicle.getBrakingSpeed()))
                    .addLoreLine(Utils.color("&7Benzine: &f" + (((int) vehicle.getFuel()) + "%")))
                    .addLoreLine(Utils.color(""))
                    .setNBT("mtvLicense", vehicle.getLicensePlate())
                    .setItemFlag(flags)
                    .makeUnbreakable(true)
                    .toItemStack();
            this.inventory.setItem(i, carItem);
        }

        if (page > 0) {
            this.inventory.setItem(pageSize + 2, new ItemBuilder(Material.SPECTRAL_ARROW)
                    .setColoredName("&6Pagina &c" + page)
                    .setNBT("page", (page - 1) + "")
                    .toItemStack());
        }

        if (licensePlates.size() - page * pageSize > pageSize) {
            int newPage = page + 2;
            this.inventory.setItem(pageSize + 6, new ItemBuilder(Material.SPECTRAL_ARROW)
                    .setColoredName("&6Pagina: &c" + (page + 2))
                    .setNBT("page", (page + 1) + "")

                    .toItemStack());
        }
    }


    @Override
    public void onClick(InventoryClickEvent event) {
        if (event.getCurrentItem() == null) return;
        if (event.getCurrentItem().getType().equals(Material.AIR)) return;
        event.setCancelled(true);
        ItemStack item = event.getCurrentItem();
        Player player = (Player) event.getWhoClicked();

        if (item.getType().equals(Material.SPECTRAL_ARROW)) {
            int np = Integer.valueOf(NBTEditor.getString(item, "page"));
            new GarageMenu(player, np);
            return;
        }

        if (!event.getInventory().equals(this.inventory)) return;
        player.closeInventory();

        String licensePlate = NBTEditor.getString(event.getCurrentItem(), "mtvLicense");
        Vehicle vehicle = VehicleUtils.getVehicle(licensePlate);

        if (vehicle == null) {
            player.sendMessage(Utils.color("&cEr ging iets mis met het ophalen van deze vehicle."));
            return;
        }

        VehicleUtils.spawnVehicle(licensePlate, player.getLocation());
        VehicleUtils.enterVehicle(licensePlate, player);
        Utils.removePlateFromGarage(player, licensePlate);

    }
}
