package fr.firepro.varelia.koth.command;

import be.maximvdw.placeholderapi.PlaceholderAPI;
import fr.firepro.varelia.koth.VareliaKOTH;
import fr.firepro.varelia.koth.scheduler.VareliaKOTHScheduler;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class VareliaKOTHCommand implements CommandExecutor {
    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {

        if (args.length >= 2) {
            VareliaKOTHScheduler vareliaKOTHScheduler = new VareliaKOTHScheduler();
            if (args[0].equalsIgnoreCase("start")) {
                vareliaKOTHScheduler.startKoth(args[1]);
                sender.sendMessage("Vous venez de lancer le koth " + args[1]);
            } else if (args[0].equalsIgnoreCase("stop")) {
                vareliaKOTHScheduler.stopKoth(args[1]);
                sender.sendMessage("Vous venez de stop le koth " + args[1]);
            }
        } else if (args[0].equalsIgnoreCase("status")) {
            sender.sendMessage("KOTH en cours : " + VareliaKOTH.getInstance().getStarted().keySet());
        }


        return false;
    }
}
