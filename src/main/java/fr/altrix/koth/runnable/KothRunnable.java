package fr.altrix.koth.runnable;

import fr.altrix.koth.*;
import fr.altrix.koth.koth.Koth;
import fr.altrix.koth.scoreboards.*;
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

    private KothPlugin main;
    public KothRunnable(KothPlugin main){this.main = main;}

    public void startRunnable(Koth koth1) {

        broadcastStart(koth1);

        new BukkitRunnable() {
            final Koth koth = koth1;
            int time = 0;
            @Override
            public void run() {
                if (time >= koth.getMaxTime())
                    koth.setStarted(false);

                if (koth.getPoints().size() > 0 && koth.getPoints().get(koth.getTop().get(0)) >= main.getConfig().getInt("max-score"))
                    koth.setStarted(false);

                if (koth.getStarted()) {

                    Bukkit.getOnlinePlayers().forEach(player -> {
                        showScoreBoardToPlayer(player);

                        if (koth.isInArea(player)) {
                            String factionName = main.getInterfacesManager().getiFactions().getFactionTagByPlayer(player);
                            if (factionName != null) addPoints(koth, factionName);
                        }
                    });

                    calculateTop(koth);

                    time++; koth.setTime(time);
                    main.getKothManager().setActualKoth(koth);
                } else { koth.finish(); cancel();}
            }
        }.runTaskTimerAsynchronously(main, 0, 20);
    }

    private void broadcastStart(Koth koth) {
        Bukkit.broadcastMessage(ChatColor.translateAlternateColorCodes('&',
                main.getConfig().getString("messages.koth-started")
                        .replace("%koth_x%", String.valueOf(koth.getMiddle().getBlockX()))
                        .replace("%koth_z%", String.valueOf(koth.getMiddle().getBlockZ()))
                        .replace("%koth_name%", koth.getName())));
    }

    private void showScoreBoardToPlayer(Player player) {
        IScoreBoard iScoreBoard = main.getInterfacesManager().getiScoreBoard();
        if (iScoreBoard != null)
            iScoreBoard.showScoreBoardToPlayer(player, "koth",main);
    }

    private void addPoints(Koth koth, String factionName) {
        if (koth.getPoints().containsKey(factionName))
            koth.getPoints().put(factionName, koth.getPoints().get(factionName) + 1);
        else koth.getPoints().put(factionName, 1);
    }

    private void calculateTop(Koth koth) {
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
