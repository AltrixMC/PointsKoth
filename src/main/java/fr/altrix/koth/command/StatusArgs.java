package fr.altrix.koth.command;

import fr.altrix.koth.KothPlugin;
import fr.altrix.koth.area.Koth;
import fr.better.command.complex.content.Argument;
import org.bukkit.entity.Player;

import java.util.List;

public class StatusArgs extends Argument {
    @Override
    public String utility() {
        return KothPlugin.getInstance().iLanguages.statusUtility();
    }

    @Override
    public String execute(Player player, List<String> parameters) {
        Koth koth = KothPlugin.getInstance().actualKoth;
        if (koth == null)
            return KothPlugin.getInstance().iLanguages.noKothIsStarted();

        return KothPlugin.getInstance().iLanguages.kothIsStarted()
                .replace("{koth}", koth.getName());
    }

    @Override
    public String parameter() {
        return "";
    }
}
