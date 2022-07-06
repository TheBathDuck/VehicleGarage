package nl.thebathduck.vehiclegarage.listeners;

import com.gmail.filoghost.holographicdisplays.api.handler.TouchHandler;
import nl.thebathduck.vehiclegarage.menu.GarageMenu;
import org.bukkit.entity.Player;

public class HologramHandler implements TouchHandler {


    @Override
    public void onTouch(Player player) {
        new GarageMenu(player, 0);
    }
}
