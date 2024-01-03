package nl.thebathduck.vehiclegarage.tasks;

import io.github.bananapuncher714.nbteditor.NBTEditor;
import nl.mtvehicles.core.infrastructure.vehicle.Vehicle;
import nl.mtvehicles.core.infrastructure.vehicle.VehicleUtils;
import nl.thebathduck.vehiclegarage.utils.ImpoundUtils;
import org.bukkit.World;
import org.bukkit.entity.ArmorStand;
import org.bukkit.entity.Entity;
import org.bukkit.scheduler.BukkitRunnable;
import xyz.xenondevs.particle.ParticleBuilder;
import xyz.xenondevs.particle.ParticleEffect;

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
                    if (vehicle == null) continue;
                    String ownerUuid = vehicle.getOwnerUUID().toString();
                    if (ownerUuid == null) continue;
                    ImpoundUtils.addPlateToImpound(ownerUuid, licensePlate);
                    new ParticleBuilder(ParticleEffect.CLOUD, armorStand.getLocation())
                            .setAmount(50)
                            .setOffset(0.1f, 0.1f, 0.1f)
                            .setSpeed(0.5f)
                            .display();
                    armorStand.remove();
                }

                if (!entity.isDead()) {
                    entity.remove();
                }
            }
        }
    }
}
