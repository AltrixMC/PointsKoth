package fr.altrix.koth.scoreboards;

import fr.altrix.koth.KothPlugin;
import org.bukkit.entity.*;

public interface IScoreBoard {

    void showScoreBoardToPlayer(Player player, String scoreBoardName, KothPlugin main);

    void showDefaultScoreBoard(Player player, KothPlugin main);

}
