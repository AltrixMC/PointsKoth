package fr.altrix.varelia.koth.command;

import fr.altrix.varelia.koth.VareliaKOTH;
import fr.altrix.varelia.koth.scheduler.VareliaKOTHScheduler;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;

public class VareliaKOTHCommand implements CommandExecutor {
    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {

        if (args.length >= 2) {
            VareliaKOTHScheduler vareliaKOTHScheduler = new VareliaKOTHScheduler();
            if (args[0].equalsIgnoreCase("start")) {
                System.out.println(args[1]);
                sender.sendMessage("Vous venez de lancer le koth " + args[1]);
                vareliaKOTHScheduler.startKoth(args[1]);
            } else if (args[0].equalsIgnoreCase("stop")) {
                sender.sendMessage("Vous venez de stop le koth " + args[1]);
                vareliaKOTHScheduler.stopKoth(args[1]);
            }
        } else if (args[0].equalsIgnoreCase("status")) {
            sender.sendMessage("KOTH en cours : " + VareliaKOTH.getInstance().getStarted().keySet());
        }


        return false;
    }
}
