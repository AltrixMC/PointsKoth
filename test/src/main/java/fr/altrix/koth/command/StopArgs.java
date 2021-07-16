package fr.altrix.koth.command;

import fr.altrix.koth.KothPlugin;
import fr.altrix.koth.manager.KothManager;
import fr.better.command.complex.content.Argument;
import org.bukkit.entity.Player;

import java.util.List;

public class StopArgs extends Argument {
    @Override
    public String utility() {
        return "allows to stop a koth";
    }

    @Override
    public String execute(Player player, List<String> parameters) {
        KothManager kothManager = new KothManager();
        if (kothManager.getKothById(parameters.get(0)) == null)
            return "§6PointsKoth » §7The koth §6" + parameters.get(0) + " §7was not found";

        kothManager.stopGame(kothManager.getKothById(parameters.get(0)));
        return "§6PointsKoth » §7The koth §6" + parameters.get(0) + " §7has been stopped";
    }

    @Override
    public String parameter() {
        return "<name>";
    }
}