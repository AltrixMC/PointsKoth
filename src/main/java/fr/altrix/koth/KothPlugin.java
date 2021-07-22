package fr.altrix.koth;

import fr.altrix.koth.area.*;
import fr.altrix.koth.command.*;
import fr.altrix.koth.factions.*;
import fr.altrix.koth.languages.*;
import fr.altrix.koth.listener.*;
import fr.altrix.koth.manager.*;
import fr.altrix.koth.scoreboards.*;
import fr.altrix.koth.utils.*;
import fr.altrix.koth.utils.bstats.*;
import fr.better.command.*;
import fr.better.command.complex.*;
import fr.better.command.complex.content.*;
import org.bukkit.*;
import org.bukkit.plugin.*;
import org.bukkit.plugin.java.*;

import java.util.*;
import java.util.logging.*;

public final class KothPlugin extends JavaPlugin {

    private static KothPlugin instance;

    public Logger log = Logger.getLogger("Minecraft");
    public boolean upToDate;

    private KothManager kothManager;
    private InterfacesManager interfacesManager;

    @Override
    public void onEnable() {
        instance = this;
        saveDefaultConfig();

        getServer().getPluginManager().registerEvents(new DeathListener(), this);
        getServer().getPluginManager().registerEvents(new UpdateListener(), this);

        addCommands();
        update();

        kothManager = new KothManager();
        interfacesManager = new InterfacesManager();
    }

    private void addCommands() {
        CommandsBuilder builder = CommandsBuilder.init(this);
        Command command = builder.createComplexCommand("pkoth");
        command.add("start", new StartArgs(), ArgumentType.DONT_NEED_PLAYER);
        command.add("stop", new StopArgs(), ArgumentType.DONT_NEED_PLAYER);
        command.add("status", new StatusArgs(), ArgumentType.DONT_NEED_PLAYER);
        command.add("reload", new ReloadArgs(), ArgumentType.DONT_NEED_PLAYER);
    }
    private void update() {
        Metrics metrics = new Metrics(this, 11805);
        new UpdateChecker(this, 93590).getVersion(version -> {
            if (this.getDescription().getVersion().equalsIgnoreCase(version)) {
                log.info("There is not a new update available.");
                upToDate = true;
            } else {
                log.info("There is a new update available.");
                upToDate = false;
            }
        });
    }

    public static KothPlugin getInstance() {
        return instance;
    }

    public void reloadPlugin() {
        reloadConfig();
        kothManager = new KothManager();
        interfacesManager = new InterfacesManager();
    }

    public KothManager getKothManager() {
        return kothManager;
    }

    public InterfacesManager getInterfacesManager() {
        return interfacesManager;
    }
}
