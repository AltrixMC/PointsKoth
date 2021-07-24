package fr.altrix.koth.manager;

import fr.altrix.koth.KothPlugin;
import fr.altrix.koth.area.Koth;
import fr.altrix.koth.runnable.KothRunnable;
import fr.altrix.koth.utils.Placeholders;
import org.bukkit.*;
import org.bukkit.entity.*;

import java.util.*;

public class KothManager {

    public List<Koth> koths;
    public Koth actualKoth;

    public KothManager() { loadKoths(); }

    private void loadKoths() {
        koths = new ArrayList<>();
        for (String s : KothPlugin.getInstance().getConfig().getConfigurationSection("koth").getKeys(false))
            koths.add(new Koth(KothPlugin.getInstance().getConfig().getConfigurationSection("koth." + s), s));
    }

    public Koth getKothById(String id) {
        for (Koth koth : koths) {
            if (koth.getId().equalsIgnoreCase(id))
                return koth;
        }
        return null;
    }

    public void startGame(Koth koth) {
        if (Bukkit.getPluginManager().getPlugin("PlaceholderAPI") != null)
            new Placeholders(KothPlugin.getInstance(), koth).register();
        koth.setStarted(true);
        new KothRunnable().startRunnable(koth);
    }
}
