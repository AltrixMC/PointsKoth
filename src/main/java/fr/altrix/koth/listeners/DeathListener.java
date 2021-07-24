package fr.altrix.koth.listeners;

import fr.altrix.koth.KothPlugin;
import fr.altrix.koth.area.Koth;
import org.bukkit.*;
import org.bukkit.event.*;
import org.bukkit.event.entity.PlayerDeathEvent;

public class DeathListener implements Listener {

    @EventHandler(priority = EventPriority.HIGH)
    public void onDead(PlayerDeathEvent event) {
        Koth koth = KothPlugin.getInstance().getKothManager().actualKoth;
        String factionName = KothPlugin.getInstance().getInterfacesManager().iFactions.getFactionTagByPlayer(event.getEntity());
        if (factionName != null && koth.getPoints().containsKey(factionName)) {
            int totalPoints = (int) (koth.getPoints().get(factionName) * KothPlugin.getInstance().getConfig().getDouble("death-multiplier"));
            int point = koth.getPoints().get(factionName);
            koth.getPoints().put(factionName, totalPoints);
            point = point - koth.getPoints().get(factionName);
            Bukkit.broadcastMessage(ChatColor.translateAlternateColorCodes('&',
                    KothPlugin.getInstance().getConfig().getString("messages.death-message")
                            .replace("%points%", String.valueOf(point))
                            .replace("%player%", event.getEntity().getName())
                            .replace("%faction%", factionName)));
        }
    }

}
