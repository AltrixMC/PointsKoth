package fr.altrix.koth.runnable;

import be.maximvdw.featherboard.api.FeatherBoardAPI;
import be.maximvdw.placeholderapi.PlaceholderAPI;
import com.massivecraft.factions.FPlayer;
import com.massivecraft.factions.FPlayers;
import com.massivecraft.factions.Faction;
import com.massivecraft.factions.Factions;
import fr.altrix.koth.KothPlugin;
import fr.altrix.koth.entity.Koth;
import fr.altrix.koth.utils.ValueComparator;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.entity.Player;
import org.bukkit.scheduler.BukkitRunnable;

import java.util.*;

public class KothRunnable {

    public void startRunnable(Koth koth1) {
        KothPlugin.getInstance().actualKoth = koth1;
        new BukkitRunnable() {
            Koth koth = KothPlugin.getInstance().actualKoth;
            int time = 0;
            @Override
            public void run() {
                if (time >= koth.getTime()) koth.setStarted(false);
                if (koth.getPoints() != null && koth.getPoints().get(0) >= KothPlugin.getInstance().getConfig().getInt("max-score"))
                if (koth.getStarted()) {
                    koth = KothPlugin.getInstance().actualKoth;
                    for (Player p : Bukkit.getOnlinePlayers()) {
                        FeatherBoardAPI.showScoreboard(p, "koth");
                        if (koth.isInArea(p)) {
                            FPlayer fPlayer = FPlayers.getInstance().getByPlayer(p);
                            Faction faction = fPlayer.getFaction();
                            if (faction != Factions.getInstance().getWilderness())
                                if (koth.getPoints().containsKey(faction))
                                    koth.getPoints().put(faction, koth.getPoints().get(faction) + 1);
                                else koth.getPoints().put(faction, 1);
                        }
                    }
                    calculateTop(koth);
                    time++;
                } else { koth.finish();cancel(); }
            }
        }.runTaskTimerAsynchronously(KothPlugin.getInstance(), 0, 20);
    }

    public void calculateTop(Koth koth) {
        ValueComparator bvc = new ValueComparator(koth.getPoints());
        TreeMap<Faction, Integer> sorted_map = new TreeMap<Faction, Integer>(bvc);
        sorted_map.putAll(koth.getPoints());
        List<Faction> list = new ArrayList<>();
        for (int i = 1; i < 6; i++) {
            try {
                Map.Entry<Faction, Integer> e = sorted_map.pollFirstEntry();
                Faction faction = e.getKey();
                list.add(faction);
            } catch (Exception ignored) {}
        }
        koth.setTop(list);
    }


}
