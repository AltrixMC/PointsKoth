package fr.altrix.koth.scoreboards;

import be.maximvdw.featherboard.api.*;
import fr.altrix.koth.KothPlugin;
import org.bukkit.entity.*;

public class FeatherBoard implements IScoreBoard {
    @Override
    public void showScoreBoardToPlayer(Player player, String scoreBoardName, KothPlugin main) {
        FeatherBoardAPI.showScoreboard(player, "koth");
    }

    @Override
    public void showDefaultScoreBoard(Player player, KothPlugin main) {
        FeatherBoardAPI.resetDefaultScoreboard(player);
    }
}
