package fr.altrix.koth;

import fr.altrix.koth.command.*;
import fr.altrix.koth.listeners.*;
import fr.altrix.koth.manager.*;
import fr.altrix.koth.utils.*;
import fr.altrix.koth.utils.bstats.*;
import fr.better.command.*;
import fr.better.command.complex.*;
import fr.better.command.complex.content.*;
import org.bukkit.plugin.java.*;
import org.json.simple.*;
import org.json.simple.parser.*;

import java.util.*;

public final class KothPlugin extends JavaPlugin {

    private static KothPlugin instance;

    public boolean upToDate = false;
    public String desc;
    public long lastUpdateTime;

    private KothManager kothManager;
    private InterfacesManager interfacesManager;

    @Override
    public void onEnable() {
        instance = this;
        saveDefaultConfig();

        getServer().getPluginManager().registerEvents(new DeathListener(), this);
        getServer().getPluginManager().registerEvents(new JoinListener(), this);

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
        UpdateChecker updateChecker = new UpdateChecker(this, 93590);
        updateChecker.getDesc(desc -> {
            try {
                JSONObject jsonObject = (JSONObject) new JSONParser().parse(desc);

                this.desc = base64ToString((String) jsonObject.get("description"));
                this.lastUpdateTime = (long) jsonObject.get("date");
            } catch (ParseException e) { e.printStackTrace(); }
        });
        updateChecker.getVersion(version -> {
            try {
                JSONObject jsonObject = (JSONObject) new JSONParser().parse(version);

                if (jsonObject.get("name").equals(getDescription().getVersion()))
                    upToDate = true;
            } catch (ParseException e) { e.printStackTrace(); }
        });
    }

    private String base64ToString(String base64) {
        byte[] decodedBytes = Base64.getDecoder().decode(base64);
        return new String(decodedBytes);
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
