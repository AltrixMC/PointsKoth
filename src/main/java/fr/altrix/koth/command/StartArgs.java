package fr.altrix.koth.command;

import fr.altrix.koth.KothPlugin;
import fr.altrix.koth.area.Koth;
import fr.altrix.koth.manager.KothManager;
import fr.better.command.complex.content.Argument;
import org.bukkit.entity.Player;

import java.util.List;

public class StartArgs extends Argument {
    @Override
    public String utility() {
        return KothPlugin.getInstance().getInterfacesManager().iLanguages.startUtility();
    }

    @Override
    public String execute(Player player, List<String> parameters) {
        KothManager kothManager = new KothManager();
        if (kothManager.getKothById(parameters.get(0)) == null)
            return KothPlugin.getInstance().getInterfacesManager().iLanguages.kothNotFound()
                    .replace("{koth}", parameters.get(0));

        Koth koth = KothPlugin.getInstance().getKothManager().actualKoth;
        if (koth != null)
            return KothPlugin.getInstance().getInterfacesManager().iLanguages.kothAlreadyStarted()
                    .replace("{koth}", koth.getName());

        kothManager.startGame(kothManager.getKothById(parameters.get(0)));
        return KothPlugin.getInstance().getInterfacesManager().iLanguages.kothStarted()
                .replace("{koth}", parameters.get(0));
    }

    @Override
    public String parameter() {
        return KothPlugin.getInstance().getInterfacesManager().iLanguages.startParameters();
    }
}
