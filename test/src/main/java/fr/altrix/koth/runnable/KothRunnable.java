package fr.altrix.koth.runnable;

import be.maximvdw.featherboard.api.FeatherBoardAPI;
import com.massivecraft.factions.FPlayer;
import com.massivecraft.factions.FPlayers;
import com.massivecraft.factions.Faction;
import com.massivecraft.factions.Factions;
import fr.altrix.koth.*;
import fr.altrix.koth.area.Koth;
import fr.altrix.koth.factions.*;
import fr.altrix.koth.utils.ValueComparator;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import org.bukkit.scheduler.BukkitRunnable;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

public class KothRunnable {

    public void startRunnable(Koth koth1) {
        KothPlugin.getInstance().actualKoth = koth1;
        Bukkit.broadcastMessage(ChatColor.translateAlternateColorCodes('&',
                KothPlugin.getInstance().getConfig().getString("messages.koth-started")
                .replace("{kothX}", String.valueOf(koth1.getMiddle().getBlockX()))
                .replace("{kothZ}", String.valueOf(koth1.getMiddle().getBlockZ()))
                .replace("{kothName}", koth1.getName())));

        new BukkitRunnable() {
            IFactions iFactions = new FactionsUUID();
            Koth koth = KothPlugin.getInstance().actualKoth;
            int time = 0;
            @Override
            public void run() {
                if (time >= koth.getMaxTime()) koth.setStarted(false);
                if (koth.getPoints().size() > 0 && koth.getPoints().get(koth.getTop().get(0)) >= KothPlugin.getInstance().getConfig().getInt("max-score")) koth.setStarted(false);

                if (koth.getStarted()) {
                    koth = KothPlugin.getInstance().actualKoth;
                    for (Player p : Bukkit.getOnlinePlayers()) {
                        FeatherBoardAPI.showScoreboard(p, "koth");
                        if (koth.isInArea(p)) {
                            String factionName = iFactions.getFactionTagByPlayer(p);
                            if (factionName != null)
                                if (koth.getPoints().containsKey(factionName))
                                    koth.getPoints().put(factionName, koth.getPoints().get(factionName) + 1);
                                else koth.getPoints().put(factionName, 1);
                        }
                    }
                    calculateTop(koth);
                    time++;
                } else { koth.finish(); cancel();}
            }
        }.runTaskTimerAsynchronously(KothPlugin.getInstance(), 0, 20);
    }

    public void calculateTop(Koth koth) {
        ValueComparator bvc = new ValueComparator(koth.getPoints());
        TreeMap<String, Integer> sorted_map = new TreeMap<String, Integer>(bvc);
        sorted_map.putAll(koth.getPoints());
        List<String> list = new ArrayList<>();
        for (int i = 1; i < 6; i++) {
            try {
                Map.Entry<String, Integer> e = sorted_map.pollFirstEntry();
                String factionName = e.getKey();
                list.add(factionName);
            } catch (Exception ignored) {}
        }
        koth.setTop(list);
    }
}
