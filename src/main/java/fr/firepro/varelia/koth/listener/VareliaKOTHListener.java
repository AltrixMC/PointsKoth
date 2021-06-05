package fr.firepro.varelia.koth.listener;

import com.massivecraft.factions.FPlayer;
import com.massivecraft.factions.FPlayers;
import com.massivecraft.factions.Faction;
import fr.firepro.varelia.koth.VareliaKOTH;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.PlayerDeathEvent;

public class VareliaKOTHListener implements Listener {

    @EventHandler(priority = EventPriority.HIGHEST)
    public void onDead(PlayerDeathEvent event) {
        if (VareliaKOTH.getInstance().getStarted().size() > 0) {
            FPlayer fPlayer = FPlayers.getInstance().getByPlayer(event.getEntity());
            Faction faction = fPlayer.getFaction();
            if (!faction.isWilderness() && VareliaKOTH.getInstance().getFactionsPoints().containsKey(faction)) {
                int totalPoints = (int) (VareliaKOTH.getInstance().getFactionsPoints().get(faction) * VareliaKOTH.getInstance().getConfig().getDouble("koth.death-multiplier"));
                int point = (int) VareliaKOTH.getInstance().getFactionsPoints().get(faction);
                VareliaKOTH.getInstance().addFactionPoints(faction, totalPoints);
                point = point - VareliaKOTH.getInstance().getFactionsPoints().get(faction);
                Bukkit.broadcastMessage(ChatColor.translateAlternateColorCodes('&',VareliaKOTH.getInstance().getConfig().getString("messages.death-message").replace("{points}", String.valueOf(point)).replace("{player}", event.getEntity().getName())));
            }
        }
    }

}
