package fr.altrix.koth.command;

import fr.altrix.koth.KothPlugin;
import fr.altrix.koth.manager.KothManager;
import fr.better.command.complex.content.Argument;
import org.bukkit.entity.Player;

import java.util.List;

public class StopArgs extends Argument {
    @Override
    public String utility() {
        return "permet d'arreter un koth";
    }

    @Override
    public String execute(Player player, List<String> parameters) {
        KothManager kothManager = new KothManager();
        if (kothManager.getKothById(parameters.get(0)) == null)  return "§6PointsKoth » §7Le koth §6" + parameters.get(0) + " §7n'as pas été trouvé";

        kothManager.stopGame(kothManager.getKothById(parameters.get(0)));
        return "§6PointsKoth » §7Le koth §6" + parameters.get(0) + " §7a bien été stopper";
    }

    @Override
    public String parameter() {
        return "<name>";
    }
}