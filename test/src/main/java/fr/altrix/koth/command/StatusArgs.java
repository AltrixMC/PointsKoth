package fr.altrix.koth.command;

import fr.altrix.koth.KothPlugin;
import fr.altrix.koth.area.Koth;
import fr.better.command.complex.content.Argument;
import org.bukkit.entity.Player;

import java.util.List;

public class StatusArgs extends Argument {
    @Override
    public String utility() {
        return "get the current koth";
    }

    @Override
    public String execute(Player player, List<String> parameters) {
        Koth koth = KothPlugin.getInstance().actualKoth;
        if (koth == null)
            return "§6PointsKoth » §7No koth is started";

        return "§6PointsKoth » §7The koth §6" + koth.getName() + " §7is started";
    }

    @Override
    public String parameter() {
        return "";
    }
}
