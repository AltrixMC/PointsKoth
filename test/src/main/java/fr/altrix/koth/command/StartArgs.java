package fr.altrix.koth.command;

import fr.altrix.koth.KothPlugin;
import fr.altrix.koth.area.Koth;
import fr.altrix.koth.manager.KothManager;
import fr.better.command.complex.content.Argument;
import org.bukkit.entity.Player;

import java.util.List;

public class StartArgs extends Argument {
    @Override
    public String utility() {
        return "allows to start a koth";
    }

    @Override
    public String execute(Player player, List<String> parameters) {
        KothManager kothManager = new KothManager();
        if (kothManager.getKothById(parameters.get(0)) == null)
            return "§6PointsKoth » §7The koth §6" + parameters.get(0) + " §7was not found";

        Koth koth = KothPlugin.getInstance().actualKoth;
        if (koth != null)
            return "§6PointsKoth » §7A koth is already started (" + koth.getName() + ")";

        kothManager.startGame(kothManager.getKothById(parameters.get(0)));
        return "§6PointsKoth » §7The koth §6" + parameters.get(0) + " §7has been started";
    }

    @Override
    public String parameter() {
        return "<name>";
    }
}
