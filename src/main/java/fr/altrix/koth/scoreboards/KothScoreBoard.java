package fr.altrix.koth.scoreboards;

import fr.altrix.koth.*;
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

    public KothScoreBoard() {
        this.title = ChatColor.translateAlternateColorCodes('&', KothPlugin.getInstance().getConfig().getString("scoreboard.title"));
        this.lines = KothPlugin.getInstance().getConfig().getStringList("scoreboard.lines");
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
                FastBoard board = new FastBoard(player);

                String title = ChatColor.translateAlternateColorCodes('&', title1);

                List<String> lines = getColoredLines(lines1.stream().map(s -> PlaceholderAPI.setPlaceholders(player, s)).collect(Collectors.toList()));

                board.updateTitle(title);
                board.updateLines(lines);
            }
        }.runTask(KothPlugin.getInstance());
    }

    @Override
    public void showDefaultScoreBoard(Player player) {
        new FastBoard(player).delete();
    }
}
