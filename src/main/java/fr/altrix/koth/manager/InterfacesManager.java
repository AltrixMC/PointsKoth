package fr.altrix.koth.manager;

import fr.altrix.koth.*;
import fr.altrix.koth.factions.*;
import fr.altrix.koth.languages.*;
import fr.altrix.koth.scoreboards.*;
import org.bukkit.*;
import org.bukkit.plugin.*;

public class InterfacesManager {

    public IFactions iFactions;
    public IScoreBoard iScoreBoard;
    public ILanguages iLanguages;

    public InterfacesManager() {
        setActualsLibs();
    }

    private void setActualsLibs() {
        for (Plugin plugin : Bukkit.getPluginManager().getPlugins()) {
            if (plugin.getName().equalsIgnoreCase("FeatherBoard"))
                iScoreBoard = new FeatherBoard();
            else if (plugin.getName().equalsIgnoreCase("QuickBoard"))
                iScoreBoard = new QuickBoard();
            if (plugin.getName().equalsIgnoreCase("Factions")) {
                String authors = plugin.getDescription().getAuthors().toString();
                if (authors.contains("Driftay"))
                    iFactions = new FactionsUUID();
                else if (authors.contains("drtshock"))
                    iFactions = new FactionsUUID();
                else if (authors.contains("Cayorion") && Bukkit.getPluginManager().isPluginEnabled("MassiveCore"))
                    iFactions = new MassiveFaction();
            }
        }
        if (KothPlugin.getInstance().getConfig().getString("language").equalsIgnoreCase("fr"))
            iLanguages = new French();
        else if (KothPlugin.getInstance().getConfig().getString("language").equalsIgnoreCase("en"))
            iLanguages = new English();
        else iLanguages = new English();

        if (iFactions == null) {
            Bukkit.getLogger().warning("\n----------\nPlease use a faction plugin\n----------\n");
            Bukkit.getPluginManager().disablePlugin(KothPlugin.getInstance());
        }
    }
}
