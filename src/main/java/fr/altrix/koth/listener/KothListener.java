package fr.altrix.koth.listener;

import com.massivecraft.factions.FPlayer;
import com.massivecraft.factions.FPlayers;
import com.massivecraft.factions.Faction;
import fr.altrix.koth.KothPlugin;
import fr.altrix.koth.area.Koth;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.entity.PlayerDeathEvent;

public class KothListener implements org.bukkit.event.Listener {

    @EventHandler(priority = EventPriority.HIGH)
    public void onDead(PlayerDeathEvent event) {
        Koth koth = KothPlugin.getInstance().actualKoth;
        FPlayer fPlayer = FPlayers.getInstance().getByPlayer(event.getEntity());
        Faction faction = fPlayer.getFaction();
        if (!faction.isWilderness() && koth.getPoints().containsKey(faction)) {
            int totalPoints = (int) (koth.getPoints().get(faction) * KothPlugin.getInstance().getConfig().getDouble("death-multiplier"));
            int point = koth.getPoints().get(faction);
            koth.getPoints().put(faction, totalPoints);
            point = point - koth.getPoints().get(faction);
            Bukkit.broadcastMessage(ChatColor.translateAlternateColorCodes('&', KothPlugin.getInstance().getConfig().getString("messages.death-message").replace("{points}", String.valueOf(point)).replace("{player}", event.getEntity().getName())));
        }
    }

}
