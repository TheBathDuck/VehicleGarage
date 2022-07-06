package nl.thebathduck.vehiclegarage.listeners;

import nl.mtvehicles.core.events.VehiclePickUpEvent;
import nl.thebathduck.vehiclegarage.utils.Utils;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;

public class VehiclePickupListener implements Listener {

    @EventHandler
    public void onPickup(VehiclePickUpEvent event) {
        if(!event.getPlayer().hasPermission("garage.monteur")) {
            event.setCancelled(true);
            event.getPlayer().sendMessage(Utils.color("&cJij kan voertuigen niet oppakken!"));
            return;
        }
    }
}
