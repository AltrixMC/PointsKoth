package fr.firepro.varelia.koth;

import com.massivecraft.factions.Faction;
import fr.firepro.varelia.koth.command.VareliaKOTHCommand;
import lombok.Getter;
import lombok.Setter;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.HashMap;

public final class VareliaKOTH extends JavaPlugin {

    public static VareliaKOTH instance;

    @Getter @Setter private HashMap<Faction, Integer> factionsPoints = new HashMap<>();
    @Getter @Setter private HashMap<String, Boolean> started = new HashMap<>();
    @Getter @Setter private HashMap<Integer, String> placeholder = new HashMap<>();

    @Override
    public void onEnable() {
        instance = this;
        saveDefaultConfig();
        getCommand("koth").setExecutor(new VareliaKOTHCommand());
    }

    @Override
    public void onDisable() {
        // Plugin shutdown logic
    }

    public static VareliaKOTH getInstance() {
        return instance;
    }
}
