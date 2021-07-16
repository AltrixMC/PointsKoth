package fr.altrix.koth.scoreboards;

import fr.altrix.koth.*;
import me.tade.quickboard.*;
import me.tade.quickboard.api.*;
import org.bukkit.entity.*;
import org.bukkit.scheduler.*;

public class QuickBoard implements IScoreBoard {
    @Override
    public void showScoreBoardToPlayer(Player player, String scoreBoardName) {
        new BukkitRunnable() {
            @Override
            public void run() {
                QuickBoardAPI.createBoard(player, "scoreboard." + scoreBoardName);
            }
        }.runTask(KothPlugin.getInstance());
    }

    @Override
    public void showDefaultScoreBoard(Player player) {
        new BukkitRunnable() {
            @Override
            public void run() {
                QuickBoardAPI.createBoard(player, "scoreboard.default");
            }
        }.runTask(KothPlugin.getInstance());
    }


}
