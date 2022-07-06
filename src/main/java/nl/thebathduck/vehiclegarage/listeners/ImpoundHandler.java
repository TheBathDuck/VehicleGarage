package nl.thebathduck.vehiclegarage.listeners;

import com.gmail.filoghost.holographicdisplays.api.handler.TouchHandler;
import nl.thebathduck.vehiclegarage.menu.ImpoundMenu;
import org.bukkit.entity.Player;

public class ImpoundHandler implements TouchHandler {
    @Override
    public void onTouch(Player player) {
        new ImpoundMenu(player, 0);
    }
}
