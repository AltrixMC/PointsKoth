package fr.altrix.koth;

import org.bukkit.entity.*;

public interface IScoreBoard {

    void showScoreBoardToPlayer(Player player, String scoreBoardName);

    void showDefaultScoreBoard(Player player);

}
