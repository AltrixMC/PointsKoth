package fr.altrix.koth.command;

import fr.altrix.koth.KothPlugin;
import fr.altrix.koth.area.*;
import fr.altrix.koth.manager.KothManager;
import fr.better.command.complex.content.Argument;
import org.bukkit.entity.Player;

import java.util.List;

public class StopArgs extends Argument {

    KothPlugin main;
    public StopArgs(KothPlugin main) {this.main = main;}

    @Override
    public String utility() {
        return main.getInterfacesManager().iLanguages.stopUtility();
    }

    @Override
    public String execute(Player player, List<String> parameters) {
        KothManager kothManager = main.getKothManager();
        if (kothManager.getKothById(parameters.get(0)) == null)
            return main.getInterfacesManager().iLanguages.kothNotFound()
                    .replace("{koth}", parameters.get(0));

        kothManager.actualKoth.setStarted(false);
        return main.getInterfacesManager().iLanguages.kothStopped()
                .replace("{koth}", parameters.get(0));
    }

    @Override
    public String parameter() {
        return main.getInterfacesManager().iLanguages.stopParameters();
    }
}