package fr.altrix.koth.command;

import fr.altrix.koth.KothPlugin;
import fr.altrix.koth.area.Koth;
import fr.better.command.complex.content.Argument;
import org.bukkit.entity.Player;

import java.util.List;

public class StatusArgs extends Argument {
    @Override
    public String utility() {
        return KothPlugin.getInstance().getInterfacesManager().iLanguages.statusUtility();
    }

    @Override
    public String execute(Player player, List<String> parameters) {
        Koth koth = KothPlugin.getInstance().getKothManager().actualKoth;
        if (koth == null)
            return KothPlugin.getInstance().getInterfacesManager().iLanguages.noKothIsStarted();

        return KothPlugin.getInstance().getInterfacesManager().iLanguages.kothIsStarted()
                .replace("{koth}", koth.getName());
    }

    @Override
    public String parameter() {
        return "";
    }
}
