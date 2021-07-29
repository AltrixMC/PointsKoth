package fr.altrix.koth.command;

import fr.altrix.koth.KothPlugin;
import fr.altrix.koth.area.Koth;
import fr.better.command.complex.content.Argument;
import org.bukkit.entity.Player;

import java.util.List;

public class StatusArgs extends Argument {

    KothPlugin main;
    public StatusArgs(KothPlugin main) {this.main = main;}

    @Override
    public String utility() {
        return main.getInterfacesManager().iLanguages.statusUtility();
    }

    @Override
    public String execute(Player player, List<String> parameters) {
        Koth koth = main.getKothManager().actualKoth;
        if (koth == null)
            return main.getInterfacesManager().iLanguages.noKothIsStarted();

        return main.getInterfacesManager().iLanguages.kothIsStarted()
                .replace("{koth}", koth.getName());
    }

    @Override
    public String parameter() {
        return "";
    }
}
