package fr.altrix.koth.listener;

import com.massivecraft.factions.FPlayer;
import com.massivecraft.factions.FPlayers;
import com.massivecraft.factions.Faction;
import fr.altrix.koth.KothPlugin;
import fr.altrix.koth.area.Koth;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.event.*;
import org.bukkit.event.entity.PlayerDeathEvent;

public class KothListener implements Listener {

    @EventHandler(priority = EventPriority.HIGH)
    public void onDead(PlayerDeathEvent event) {
        Koth koth = KothPlugin.getInstance().actualKoth;
        String factionName = KothPlugin.getInstance().iFactions.getFactionTagByPlayer(event.getEntity());
        if (factionName != null && koth.getPoints().containsKey(factionName)) {
            int totalPoints = (int) (koth.getPoints().get(factionName) * KothPlugin.getInstance().getConfig().getDouble("death-multiplier"));
            int point = koth.getPoints().get(factionName);
            koth.getPoints().put(factionName, totalPoints);
            point = point - koth.getPoints().get(factionName);
            Bukkit.broadcastMessage(ChatColor.translateAlternateColorCodes('&',
                    KothPlugin.getInstance().getConfig().getString("messages.death-message")
                            .replace("{points}", String.valueOf(point))
                            .replace("{player}", event.getEntity().getName())
                            .replace("{faction}", factionName)));
        }
    }

}
