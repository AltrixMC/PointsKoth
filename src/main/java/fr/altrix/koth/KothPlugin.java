package fr.altrix.koth;

import fr.altrix.koth.command.StartArgs;
import fr.altrix.koth.command.StopArgs;
import fr.altrix.koth.area.Koth;
import fr.altrix.koth.listener.KothListener;
import fr.better.command.CommandsBuilder;
import fr.better.command.complex.Command;
import fr.better.command.complex.content.ArgumentType;
import org.bstats.bukkit.Metrics;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.ArrayList;
import java.util.List;

public final class KothPlugin extends JavaPlugin {

    public static KothPlugin instance;

    public List<Koth> koths;
    public Koth actualKoth;

    @Override
    public void onEnable() {
        instance = this;
        saveDefaultConfig();

        getServer().getPluginManager().registerEvents(new KothListener(), this);

        CommandsBuilder builder = CommandsBuilder.init(this);
        Command command = builder.createComplexCommand("koth");
        command.add("start", new StartArgs(), ArgumentType.DONT_NEED_PLAYER);
        command.add("stop", new StopArgs(), ArgumentType.DONT_NEED_PLAYER);

        koths = new ArrayList<>();
        for (String s : getConfig().getConfigurationSection("koth").getKeys(false)) {
            Koth koth = new Koth(getConfig().getConfigurationSection("koth." + s), s);
            koths.add(koth);
        }
        Metrics metrics = new Metrics(this, 11805);
    }

    public static KothPlugin getInstance() {
        return instance;
    }
}
