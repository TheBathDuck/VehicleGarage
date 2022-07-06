package nl.thebathduck.vehiclegarage.listeners;

import nl.mtvehicles.core.events.VehicleLeaveEvent;
import nl.mtvehicles.core.infrastructure.models.Vehicle;
import nl.thebathduck.vehiclegarage.utils.Utils;
import org.bukkit.Location;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;

import java.util.List;

public class VehicleLeaveListener implements Listener {

    @EventHandler
    public void onVehicleLeave(VehicleLeaveEvent event) {
        if(event.getPlayer() == null || event.getVehicle() == null) return;
        Player player = event.getPlayer();
        Vehicle vehicle = event.getVehicle();

        boolean takeInLocation = Utils.isTakein(player.getLocation());

        if(takeInLocation) {
            if(!vehicle.getOwnerUUID().equals(event.getPlayer().getUniqueId())) {
                player.sendMessage(Utils.color("&cDit is niet jou voertuig!"));
                return;
            }

            player.sendMessage(Utils.color("&2Je hebt je &a" + event.getVehicle().getName() + " &2teruggezet in je garage."));
            List<String> inGarage = Utils.getGaragePlates(player);

            if(!inGarage.contains(event.getLicensePlate())) {
                Utils.addPlateToGarage(player, event.getLicensePlate());
            }

            killCar(player.getLocation(), event.getLicensePlate());
        }
    }

    private void killCar(Location location, String licensePlate) {

        for(Entity entity : location.getWorld().getNearbyEntities(location, 20, 20, 20)) {
            if(entity.getCustomName() == null) continue;
            if(entity.getCustomName().contains(licensePlate)) {
                entity.remove();
            }
        }
    }

}
