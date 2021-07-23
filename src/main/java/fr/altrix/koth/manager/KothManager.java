package fr.altrix.koth.manager;

import fr.altrix.koth.KothPlugin;
import fr.altrix.koth.area.Koth;
import fr.altrix.koth.runnable.KothRunnable;
import fr.altrix.koth.utils.Placeholders;
import org.bukkit.*;

import java.util.*;

public class KothManager {

    public List<Koth> koths;
    public Koth actualKoth;

    public KothManager() { loadKoths(); }

    private void loadKoths() {
        koths = new ArrayList<>();
        for (String s : KothPlugin.getInstance().getConfig().getConfigurationSection("koth").getKeys(false)) {
            Koth koth = new Koth(KothPlugin.getInstance().getConfig().getConfigurationSection("koth." + s), s);
            koths.add(koth);
        }
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
        KothRunnable kothRunnable = new KothRunnable();
        kothRunnable.startRunnable(koth);
    }
    public void stopGame(Koth koth) {
        koth.setStarted(false);
    }

}
