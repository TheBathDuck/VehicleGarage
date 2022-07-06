package nl.thebathduck.vehiclegarage.tasks;

import io.github.bananapuncher714.nbteditor.NBTEditor;
import nl.mtvehicles.core.infrastructure.models.Vehicle;
import nl.mtvehicles.core.infrastructure.models.VehicleUtils;
import nl.thebathduck.vehiclegarage.utils.ImpoundUtils;
import org.bukkit.World;
import org.bukkit.entity.ArmorStand;
import org.bukkit.entity.Entity;
import org.bukkit.scheduler.BukkitRunnable;
import xyz.xenondevs.particle.ParticleBuilder;
import xyz.xenondevs.particle.ParticleEffect;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class ImpoundTask extends BukkitRunnable {

    private List<World> worlds;

    public ImpoundTask(List<World> worlds) {
        this.worlds = worlds;
    }

    public ImpoundTask(World world) {
        this.worlds = new ArrayList<>();
        worlds.add(world);
    }

    @Override
    public void run() {
        for (World world : worlds) {
            for (Entity entity : world.getEntities()) {
                if (!(entity instanceof ArmorStand)) continue;
                if (entity.getCustomName() == null) continue;
                if (!(entity.getCustomName().contains("MTVEHICLES_"))) continue;
                ArmorStand armorStand = (ArmorStand) entity;

                if (entity.getCustomName().contains("SKIN")) {
                    String licensePlate = NBTEditor.getString(armorStand.getHelmet(), "mtvehicles.kenteken");
                    Vehicle vehicle = VehicleUtils.getVehicle(licensePlate);
                    String ownerUuid = vehicle.getOwnerUUID().toString();
                    ImpoundUtils.addPlateToImpound(ownerUuid, licensePlate);
                    new ParticleBuilder(ParticleEffect.CLOUD, armorStand.getLocation())
                            .setAmount(50)
                            .setOffset(0.1f, 0.1f, 0.1f)
                            .setSpeed(0.5f)
                            .display();
                    armorStand.remove();
                }

                if (entity != null || !entity.isDead()) {
                    entity.remove();
                } // Eu^73Qzb0.rt4Er^HVil2o+n
            }
        }
    }

}
