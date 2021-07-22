package fr.altrix.koth.command;

import fr.altrix.koth.KothPlugin;
import fr.better.command.complex.content.Argument;
import org.bukkit.entity.Player;

import java.util.List;

public class ReloadArgs extends Argument {
    @Override
    public String utility() {
        return KothPlugin.getInstance().iLanguages.reloadUtility();
    }

    @Override
    public String execute(Player player, List<String> parameters) {
        KothPlugin.getInstance().reloadConfig();
        KothPlugin.getInstance().loadKoths();
        return KothPlugin.getInstance().iLanguages.reloadedSuccessfully();
    }

    @Override
    public String parameter() {
        return "";
    }
}
