package fr.altrix.koth;

import fr.altrix.koth.area.*;
import fr.altrix.koth.command.*;
import fr.altrix.koth.factions.*;
import fr.altrix.koth.languages.*;
import fr.altrix.koth.listener.*;
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

    public List<Koth> koths;
    public Koth actualKoth;

    public IFactions iFactions;
    public IScoreBoard iScoreBoard;
    public ILanguages iLanguages;

    @Override
    public void onEnable() {
        instance = this;
        saveDefaultConfig();

        getServer().getPluginManager().registerEvents(new DeathListener(), this);
        getServer().getPluginManager().registerEvents(new UpdateListener(), this);

        CommandsBuilder builder = CommandsBuilder.init(this);
        Command command = builder.createComplexCommand("pkoth");
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
        setActualsLibs();
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

    private void setActualsLibs() {
        for (Plugin plugin : Bukkit.getPluginManager().getPlugins()) {
            if (plugin.getName().equalsIgnoreCase("FeatherBoard"))
                iScoreBoard = new FeatherBoard();
            else if (plugin.getName().equalsIgnoreCase("QuickBoard"))
                iScoreBoard = new QuickBoard();
            if (plugin.getName().equalsIgnoreCase("Factions")) {
                String authors = plugin.getDescription().getAuthors().toString();
                if (authors.contains("Driftay"))
                    iFactions = new FactionsUUID();
                else if (authors.contains("drtshock"))
                    iFactions = new FactionsUUID();
                else if (authors.contains("Cayorion") && Bukkit.getPluginManager().isPluginEnabled("MassiveCore"))
                    iFactions = new MassiveFaction();
            }
        }
        if (getConfig().getString("language").equalsIgnoreCase("fr"))
            iLanguages = new French();
        else if (getConfig().getString("language").equalsIgnoreCase("en"))
            iLanguages = new English();
        else iLanguages = new English();

        if (iFactions == null) {
            Bukkit.getLogger().warning("\n----------\nPlease use a faction plugin\n----------\n");
            Bukkit.getPluginManager().disablePlugin(this);
        }
    }

    public void reloadPlugin() {
        reloadConfig();
        loadKoths();
        setActualsLibs();
    }
}
