package fr.altrix.koth.manager;

import fr.altrix.koth.*;
import fr.altrix.koth.factions.*;
import fr.altrix.koth.languages.*;
import fr.altrix.koth.scoreboards.*;
import fr.mrmicky.fastboard.*;
import org.bukkit.*;
import org.bukkit.entity.*;
import org.bukkit.plugin.*;

import java.util.*;

public class InterfacesManager {

    private IFactions iFactions;
    private IScoreBoard iScoreBoard;
    private ILanguages iLanguages;

    private Map<Player, FastBoard> boards;

    private KothPlugin main;
    public InterfacesManager(KothPlugin main) {
        this.main = main;
        setActualsLibs();
    }

    private void setActualsLibs() {
        for (Plugin plugin : Bukkit.getPluginManager().getPlugins()) {
            if (plugin.getName().equalsIgnoreCase("FeatherBoard"))
                iScoreBoard = new FeatherBoard();
            else if (plugin.getName().equalsIgnoreCase("QuickBoard"))
                iScoreBoard = new QuickBoard();
            else iScoreBoard = new KothScoreBoard(main);
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
        if (main.getConfig().getString("language").equalsIgnoreCase("fr"))
            iLanguages = new French();
        else if (main.getConfig().getString("language").equalsIgnoreCase("en"))
            iLanguages = new English();
        else iLanguages = new English();

        if (iFactions == null) {
            Bukkit.getLogger().warning("\n----------\nPlease use a faction plugin\n----------\n");
            Bukkit.getPluginManager().disablePlugin(main);
        }
        boards = new HashMap<>();
    }

    public IFactions getiFactions() {
        return iFactions;
    }

    public void setiFactions(IFactions iFactions) {
        this.iFactions = iFactions;
    }

    public IScoreBoard getiScoreBoard() {
        return iScoreBoard;
    }

    public void setiScoreBoard(IScoreBoard iScoreBoard) {
        this.iScoreBoard = iScoreBoard;
    }

    public ILanguages getiLanguages() {
        return iLanguages;
    }

    public void setiLanguages(ILanguages iLanguages) {
        this.iLanguages = iLanguages;
    }

    public Map<Player, FastBoard> getBoards() {
        return boards;
    }
}
