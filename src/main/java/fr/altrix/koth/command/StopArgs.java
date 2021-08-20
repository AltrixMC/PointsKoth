package fr.altrix.koth.command;

import fr.altrix.koth.KothPlugin;
import fr.altrix.koth.manager.KothManager;
import fr.better.command.complex.content.Argument;
import org.bukkit.entity.Player;

import java.util.List;

public class StopArgs extends Argument {

    private KothPlugin main;
    public StopArgs(KothPlugin main) {this.main = main;}

    @Override
    public String utility() {
        return main.getInterfacesManager().getiLanguages().stopUtility();
    }

    @Override
    public String execute(Player player, List<String> parameters) {
        KothManager kothManager = main.getKothManager();
        if (kothManager.getKothById(parameters.get(0)) == null)
            return main.getInterfacesManager().getiLanguages().kothNotFound()
                    .replace("{koth}", parameters.get(0));

        kothManager.getActualKoth().setStarted(false);
        return main.getInterfacesManager().getiLanguages().kothStopped()
                .replace("{koth}", parameters.get(0));
    }

    @Override
    public String parameter() {
        return main.getInterfacesManager().getiLanguages().stopParameters();
    }
}