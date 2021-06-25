package fr.altrix.koth.utils;

import be.maximvdw.placeholderapi.PlaceholderAPI;
import com.massivecraft.factions.FPlayer;
import com.massivecraft.factions.FPlayers;
import com.massivecraft.factions.Faction;
import fr.altrix.koth.KothPlugin;
import fr.altrix.koth.area.Koth;

import java.util.List;
import java.util.Map;

public class Placeholders {
    public void registerPlaceholders(Koth koth) {
        for (int i = 1; i < 6; i++) {
            int number = i;
            PlaceholderAPI.registerPlaceholder(KothPlugin.getInstance(), "koth" + number + "_points", placeholderReplaceEvent -> {
                List<Faction> list = koth.getTop();
                if (list != null && list.size() >= number && list.get(number - 1) != null) {
                    Map<Faction, Integer> map = koth.getPoints();
                    return String.valueOf(map.get(list.get(number - 1)));
                }
                return "0";
            });
            PlaceholderAPI.registerPlaceholder(KothPlugin.getInstance(), "koth" + number + "_faction", placeholderReplaceEvent -> {
                List<Faction> list = koth.getTop();
                if (list != null && list.size() >= number && list.get(number - 1) != null) {
                    Faction factions = list.get(number - 1);
                    return factions.getTag();
                }
                return "Aucun";
            });
            PlaceholderAPI.registerPlaceholder(KothPlugin.getInstance(), "kothmy_points", placeholderReplaceEvent -> {
                Map<Faction, Integer> map = koth.getPoints();
                FPlayer fPlayer = FPlayers.getInstance().getByPlayer(placeholderReplaceEvent.getPlayer());
                if (map.get(fPlayer.getFaction()) != null)
                    return String.valueOf(map.get(fPlayer.getFaction()));
                return "0";
            });
        }
    }

    public void registerOthersPlaceholders(Koth koth) {
        PlaceholderAPI.registerPlaceholder(KothPlugin.getInstance(), "kothX", placeholderReplaceEvent -> {
            if (koth.getStarted())
                return String.valueOf(koth.getMiddle().getBlockX());
            return "Aucun";
        });

        PlaceholderAPI.registerPlaceholder(KothPlugin.getInstance(), "kothZ", placeholderReplaceEvent -> {
            if (koth.getStarted())
                return String.valueOf(koth.getMiddle().getBlockZ());
            return "Aucun";
        });

        PlaceholderAPI.registerPlaceholder(KothPlugin.getInstance(), "kothTime", placeholderReplaceEvent -> {
            int restTime = koth.getMaxTime() - koth.getTime();
            if (koth.getStarted())
                return String.valueOf(restTime);
            return "Aucun";
        });
    }

}
