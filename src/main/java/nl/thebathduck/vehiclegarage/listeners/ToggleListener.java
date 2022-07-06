package nl.thebathduck.vehiclegarage.listeners;

import nl.mtvehicles.core.infrastructure.models.VehicleUtils;
import nl.thebathduck.vehiclegarage.VehicleGarage;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerToggleSneakEvent;

public class ToggleListener implements Listener {

    @EventHandler
    public void onCrouch(PlayerToggleSneakEvent event) {
        Player player = event.getPlayer();


    }

}
