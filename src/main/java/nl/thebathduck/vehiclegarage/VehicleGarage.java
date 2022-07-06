package nl.thebathduck.vehiclegarage;

import net.milkbowl.vault.economy.Economy;
import nl.mtvehicles.core.infrastructure.models.Config;
import nl.thebathduck.vehiclegarage.commands.*;
import nl.thebathduck.vehiclegarage.listeners.ToggleListener;
import nl.thebathduck.vehiclegarage.listeners.VehicleLeaveListener;
import nl.thebathduck.vehiclegarage.listeners.VehiclePickupListener;
import nl.thebathduck.vehiclegarage.menu.ImpoundMenu;
import nl.thebathduck.vehiclegarage.tasks.ImpoundTask;
import nl.thebathduck.vehiclegarage.utils.*;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.World;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.plugin.PluginManager;
import org.bukkit.plugin.RegisteredServiceProvider;
import org.bukkit.plugin.java.JavaPlugin;
import org.codemc.worldguardwrapper.region.IWrappedRegion;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.UUID;

public final class VehicleGarage extends JavaPlugin {


    private static VehicleGarage instance;
    private ConfigurationFile vehicleData;
    private Economy economy;

    @Override
    public void onLoad() {
        Utils.registerWGFlags();
    }
    @Override
    public void onEnable() {

        if(!hasDepends()) {
            getPluginLoader().disablePlugin(this);
            return;
        }

        instance = this;
        saveDefaultConfig();
        GUIHolder.init(this);
        setupEco();

        vehicleData = new ConfigurationFile(this, "vehicleData.yml", false);
        vehicleData.saveConfig();

        getCommand("garage").setExecutor(new GarageCommand());
        getCommand("garage").setTabCompleter(new GarageCompleter());

        getCommand("garagelocation").setExecutor(new HologramCommands());
        getCommand("garagelocation").setTabCompleter(new HologramCompleter());

        getCommand("impound").setExecutor(new ImpoundCommand());

        HoloUtils.createHolograms();


        PluginManager pluginManager = Bukkit.getPluginManager();
        pluginManager.registerEvents(new ToggleListener(), this);
        pluginManager.registerEvents(new VehicleLeaveListener(), this);
        pluginManager.registerEvents(new VehiclePickupListener(), this);


        ImpoundUtils.runTask(Bukkit.getWorlds());
    }

    @Override
    public void onDisable() {
        HoloUtils.destroyHolograms();
    }

    public boolean hasDepends() {
        if(getServer().getPluginManager().getPlugin("HolographicDisplays") == null) {
            getLogger().severe("");
            getLogger().severe("HolographicDisplays not found, couldn't hook!");
            getLogger().severe("Disabling VehicleGarage..");
            getLogger().severe("");
            return false;
        }
        if(getServer().getPluginManager().getPlugin("WorldGuard") == null) {
            getLogger().severe("");
            getLogger().severe("WorldGuard not found, couldn't hook!");
            getLogger().severe("Disabling VehicleGarage..");
            getLogger().severe("");
            return false;
        }
        if(getServer().getPluginManager().getPlugin("Vault") == null) {
            getLogger().severe("");
            getLogger().severe("Vault not found, couldn't hook!");
            getLogger().severe("Disabling VehicleGarage..");
            getLogger().severe("");
            return false;
        }
        return true;
    }

    public void setupEco() {
        if (getServer().getPluginManager().getPlugin("Vault") == null) {
            return;
        }
        RegisteredServiceProvider<Economy> rsp = getServer().getServicesManager().getRegistration(Economy.class);
        if(rsp == null) {
            return;
        }
        economy = rsp.getProvider();
    }

    public ConfigurationFile getVehicleData() {
        return vehicleData;
    }
    public FileConfiguration getVehicleConfig() {
        return vehicleData.getConfig();
    }

    public static VehicleGarage getInstance() {
        return instance;
    }

    public Economy getEconomy() {
        return economy;
    }

}
