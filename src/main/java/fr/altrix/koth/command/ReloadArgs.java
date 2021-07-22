package fr.altrix.koth.command;

import fr.altrix.koth.KothPlugin;
import fr.better.command.complex.content.Argument;
import org.bukkit.entity.Player;

import java.util.List;

public class ReloadArgs extends Argument {
    @Override
    public String utility() {
        return KothPlugin.getInstance().getInterfacesManager().iLanguages.reloadUtility();
    }

    @Override
    public String execute(Player player, List<String> parameters) {
        if (KothPlugin.getInstance().getKothManager().actualKoth == null) {
            KothPlugin.getInstance().reloadPlugin();
            return KothPlugin.getInstance().getInterfacesManager().iLanguages.reloadedSuccessfully();
        } else {
            return KothPlugin.getInstance().getInterfacesManager().iLanguages.kothIsStartedPleaseStop();
        }
    }

    @Override
    public String parameter() {
        return "";
    }
}
