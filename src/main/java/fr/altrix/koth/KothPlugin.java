package fr.altrix.koth;

import fr.altrix.koth.command.ReloadArgs;
import fr.altrix.koth.command.StartArgs;
import fr.altrix.koth.command.StatusArgs;
import fr.altrix.koth.command.StopArgs;
import fr.altrix.koth.area.Koth;
import fr.altrix.koth.listener.KothListener;
import fr.altrix.koth.listener.UpdateListener;
import fr.altrix.koth.utils.UpdateChecker;
import fr.better.command.CommandsBuilder;
import fr.better.command.complex.Command;
import fr.better.command.complex.content.ArgumentType;
import org.bstats.bukkit.Metrics;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.ArrayList;
import java.util.List;
import java.util.logging.Logger;

public final class KothPlugin extends JavaPlugin {

    public static KothPlugin instance;

    public Logger log = Logger.getLogger("Minecraft");
    public boolean upToDate;

    public List<Koth> koths;
    public Koth actualKoth;

    @Override
    public void onEnable() {
        instance = this;
        saveDefaultConfig();

        getServer().getPluginManager().registerEvents(new KothListener(), this);
        getServer().getPluginManager().registerEvents(new UpdateListener(), this);

        CommandsBuilder builder = CommandsBuilder.init(this);
        Command command = builder.createComplexCommand("koth");
        command.add("start", new StartArgs(), ArgumentType.DONT_NEED_PLAYER);
        command.add("stop", new StopArgs(), ArgumentType.DONT_NEED_PLAYER);
        command.add("status", new StatusArgs(), ArgumentType.DONT_NEED_PLAYER);
        command.add("reload", new ReloadArgs(), ArgumentType.DONT_NEED_PLAYER);

        loadKoths();
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

    public void loadKoths() {
        koths = new ArrayList<>();
        for (String s : getConfig().getConfigurationSection("koth").getKeys(false)) {
            Koth koth = new Koth(getConfig().getConfigurationSection("koth." + s), s);
            koths.add(koth);
        }
    }

    public static KothPlugin getInstance() {
        return instance;
    }
}
