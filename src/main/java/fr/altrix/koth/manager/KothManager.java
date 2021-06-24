package fr.altrix.koth.manager;

import fr.altrix.koth.KothPlugin;
import fr.altrix.koth.areas.Koth;
import fr.altrix.koth.runnable.KothRunnable;
import fr.altrix.koth.utils.Placeholders;

public class KothManager {

    public Koth getKothById(String id) {
        for (Koth koth : KothPlugin.getInstance().koths) {
            if (koth.getId().equalsIgnoreCase(id))
                return koth;
        }
        return null;
    }

    public void startGame(Koth koth) {
        koth.setStarted(true);
        setupKoth(koth);
        KothRunnable kothRunnable = new KothRunnable();
        kothRunnable.startRunnable(koth);
    }
    public void setupKoth(Koth koth) {
        Placeholders placeholders = new Placeholders();

        placeholders.registerPlaceholders(koth);
        placeholders.registerOthersPlaceholders(koth);
    }

    public void stopGame(Koth koth) {
        koth.setStarted(false);
    }

}
