package fr.altrix.koth.command;

import fr.altrix.koth.KothPlugin;
import fr.better.command.complex.content.Argument;
import org.bukkit.entity.Player;

import java.util.List;

public class ReloadArgs extends Argument {

    KothPlugin main;
    public ReloadArgs(KothPlugin main) {this.main = main;}

    @Override
    public String utility() {
        return main.getInterfacesManager().iLanguages.reloadUtility();
    }

    @Override
    public String execute(Player player, List<String> parameters) {
        if (main.getKothManager().actualKoth == null) {
            main.reloadPlugin();
            return main.getInterfacesManager().iLanguages.reloadedSuccessfully();
        } else {
            return main.getInterfacesManager().iLanguages.kothIsStartedPleaseStop();
        }
    }

    @Override
    public String parameter() {
        return "";
    }
}
