package fr.altrix.koth.scoreboards;

import fr.altrix.koth.*;
import me.tade.quickboard.api.*;
import org.bukkit.entity.*;
import org.bukkit.scheduler.*;

public class QuickBoard implements IScoreBoard {



    @Override
    public void showScoreBoardToPlayer(Player player, String scoreBoardName, KothPlugin main) {
        new BukkitRunnable() {
            @Override
            public void run() {
                QuickBoardAPI.createBoard(player, "scoreboard." + scoreBoardName);
            }
        }.runTask(main);
    }

    @Override
    public void showDefaultScoreBoard(Player player, KothPlugin main) {
        new BukkitRunnable() {
            @Override
            public void run() {
                QuickBoardAPI.createBoard(player, "scoreboard.default");
            }
        }.runTask(main);
    }


}
