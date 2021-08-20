package fr.altrix.koth.manager;

import fr.altrix.koth.KothPlugin;
import fr.altrix.koth.koth.Koth;
import fr.altrix.koth.runnable.KothRunnable;
import fr.altrix.koth.utils.Placeholders;
import org.bukkit.*;

import java.util.*;

public class KothManager {

    private List<Koth> koths;
    private Koth actualKoth;

    private KothPlugin main;
    public KothManager(KothPlugin main) {this.main = main; loadKoths(); }

    private void loadKoths() {
        koths = new ArrayList<>();
        for (String s : main.getConfig().getConfigurationSection("koth").getKeys(false))
            koths.add(new Koth(main.getConfig().getConfigurationSection("koth." + s), s, main));
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
            new Placeholders(main).register();
        koth.setStarted(true);
        new KothRunnable(main).startRunnable(koth);
    }

    public Koth getActualKoth() {
        return actualKoth;
    }

    public void setActualKoth(Koth actualKoth) {
        this.actualKoth = actualKoth;
    }
}
