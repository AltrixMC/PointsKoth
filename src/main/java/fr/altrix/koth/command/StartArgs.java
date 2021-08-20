package fr.altrix.koth.command;

import fr.altrix.koth.KothPlugin;
import fr.altrix.koth.koth.Koth;
import fr.altrix.koth.manager.KothManager;
import fr.better.command.complex.content.Argument;
import org.bukkit.entity.Player;

import java.util.List;

public class StartArgs extends Argument {

    private KothPlugin main;
    public StartArgs(KothPlugin main) {this.main = main;}

    @Override
    public String utility() {
        return main.getInterfacesManager().getiLanguages().startUtility();
    }

    @Override
    public String execute(Player player, List<String> parameters) {
        KothManager kothManager = new KothManager(main);
        if (kothManager.getKothById(parameters.get(0)) == null)
            return main.getInterfacesManager().getiLanguages().kothNotFound()
                    .replace("{koth}", parameters.get(0));

        Koth koth = main.getKothManager().getActualKoth();
        if (koth != null)
            return main.getInterfacesManager().getiLanguages().kothAlreadyStarted()
                    .replace("{koth}", koth.getName());

        kothManager.startGame(kothManager.getKothById(parameters.get(0)));
        return main.getInterfacesManager().getiLanguages().kothStarted()
                .replace("{koth}", parameters.get(0));
    }

    @Override
    public String parameter() {
        return main.getInterfacesManager().getiLanguages().startParameters();
    }
}
