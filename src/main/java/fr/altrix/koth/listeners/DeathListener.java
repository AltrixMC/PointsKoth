package fr.altrix.koth.listeners;

import fr.altrix.koth.KothPlugin;
import fr.altrix.koth.area.Koth;
import org.bukkit.*;
import org.bukkit.event.*;
import org.bukkit.event.entity.PlayerDeathEvent;

public class DeathListener implements Listener {

    KothPlugin main;
    public DeathListener(KothPlugin main) {this.main = main;}

    @EventHandler(priority = EventPriority.HIGH)
    public void onDead(PlayerDeathEvent event) {
        if (main.getKothManager().actualKoth != null) {
            Koth koth = main.getKothManager().actualKoth;
            String factionName = main.getInterfacesManager().iFactions.getFactionTagByPlayer(event.getEntity());
            if (factionName != null && koth.getPoints().containsKey(factionName)) {
                int totalPoints = (int) (koth.getPoints().get(factionName) * main.getConfig().getDouble("death-multiplier"));
                int point = koth.getPoints().get(factionName);
                koth.getPoints().put(factionName, totalPoints);
                point = point - koth.getPoints().get(factionName);
                Bukkit.broadcastMessage(ChatColor.translateAlternateColorCodes('&',
                        main.getConfig().getString("messages.death-message")
                                .replace("%points%", String.valueOf(point))
                                .replace("%player%", event.getEntity().getName())
                                .replace("%faction%", factionName)));
            }
        }
    }

}
