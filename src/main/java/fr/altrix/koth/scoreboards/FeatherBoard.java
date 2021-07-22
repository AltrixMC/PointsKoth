package fr.altrix.koth.scoreboards;

import be.maximvdw.featherboard.api.*;
import org.bukkit.entity.*;

public class FeatherBoard implements IScoreBoard {
    @Override
    public void showScoreBoardToPlayer(Player player, String scoreBoardName) {
        FeatherBoardAPI.showScoreboard(player, "koth");
    }

    @Override
    public void showDefaultScoreBoard(Player player) {
        FeatherBoardAPI.resetDefaultScoreboard(player);
    }
}
