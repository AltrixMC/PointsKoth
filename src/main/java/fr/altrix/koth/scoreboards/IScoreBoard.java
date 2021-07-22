package fr.altrix.koth.scoreboards;

import org.bukkit.entity.*;

public interface IScoreBoard {

    void showScoreBoardToPlayer(Player player, String scoreBoardName);

    void showDefaultScoreBoard(Player player);

}
