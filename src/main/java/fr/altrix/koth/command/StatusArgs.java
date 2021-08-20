package fr.altrix.koth.command;

import fr.altrix.koth.KothPlugin;
import fr.altrix.koth.koth.Koth;
import fr.better.command.complex.content.Argument;
import org.bukkit.entity.Player;

import java.util.List;

public class StatusArgs extends Argument {

    private KothPlugin main;
    public StatusArgs(KothPlugin main) {this.main = main;}

    @Override
    public String utility() {
        return main.getInterfacesManager().getiLanguages().statusUtility();
    }

    @Override
    public String execute(Player player, List<String> parameters) {
        Koth koth = main.getKothManager().getActualKoth();
        if (koth == null)
            return main.getInterfacesManager().getiLanguages().noKothIsStarted();

        return main.getInterfacesManager().getiLanguages().kothIsStarted()
                .replace("{koth}", koth.getName());
    }

    @Override
    public String parameter() {
        return "";
    }
}
