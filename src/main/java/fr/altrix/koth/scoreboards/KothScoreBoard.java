package fr.altrix.koth.scoreboards;

import fr.altrix.koth.*;
import fr.altrix.koth.manager.*;
import fr.mrmicky.fastboard.*;
import me.clip.placeholderapi.*;
import org.bukkit.*;
import org.bukkit.entity.*;
import org.bukkit.scheduler.*;

import java.util.*;
import java.util.stream.*;

public class KothScoreBoard implements IScoreBoard {

    private String title;
    private List<String> lines;

    KothPlugin main;

    public KothScoreBoard(KothPlugin main) {
        this.main = main;
        this.title = ChatColor.translateAlternateColorCodes('&', main.getConfig().getString("scoreboard.title"));
        this.lines = main.getConfig().getStringList("scoreboard.lines");
    }

    private List<String> getColoredLines(List<String> lines) {
        return lines.stream().map(s -> ChatColor.translateAlternateColorCodes('&', s)).collect(Collectors.toList());
    }

    @Override
    public void showScoreBoardToPlayer(Player player, String scoreBoardName) {
        new BukkitRunnable() {
            final String title1 = title;
            final List<String> lines1 = lines;
            @Override
            public void run() {
                InterfacesManager interfacesManager = main.getInterfacesManager();

                FastBoard board;
                if (interfacesManager.boards.containsKey(player)) board = interfacesManager.boards.get(player);
                else board = new FastBoard(player);

                String title = ChatColor.translateAlternateColorCodes('&', title1);

                List<String> lines = getColoredLines(lines1.stream().map(s -> PlaceholderAPI.setPlaceholders(player, s)).collect(Collectors.toList()));

                board.updateTitle(title);
                board.updateLines(lines);

                interfacesManager.boards.put(player, board);
            }
        }.runTask(main);
    }

    @Override
    public void showDefaultScoreBoard(Player player) {
        new FastBoard(player).delete();
    }
}
