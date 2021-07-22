package fr.altrix.koth.listener;

import fr.altrix.koth.KothPlugin;
import net.md_5.bungee.api.ChatColor;
import net.md_5.bungee.api.chat.*;
import net.md_5.bungee.api.chat.TextComponent;
import org.bukkit.entity.Player;
import org.bukkit.event.*;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.scheduler.BukkitRunnable;

import java.awt.*;

public class UpdateListener implements Listener {

    @EventHandler(priority = EventPriority.HIGHEST)
    public void onJoin(PlayerJoinEvent event) {
        Player player = event.getPlayer();
        if (player.isOp() && !KothPlugin.getInstance().upToDate) {
            new BukkitRunnable() {
                @Override
                public void run() {
                    player.sendMessage("§6PointsKoth » §7The plugin is no longer up to date ! Please download it on spigotmc");
                    TextComponent textComponent = new TextComponent("https://www.spigotmc.org/resources/pointskoth-points-king-of-the-hill.93590/");
                    textComponent.setColor(ChatColor.GOLD);
                    textComponent.setBold(true);
                    textComponent.setUnderlined(true);
                    textComponent.setClickEvent(new ClickEvent(ClickEvent.Action.OPEN_URL, "https://www.spigotmc.org/resources/pointskoth-points-king-of-the-hill.93590/"));
                    player.spigot().sendMessage(textComponent);
                }
            }.runTaskLaterAsynchronously(KothPlugin.getInstance(), 40L);
        }
    }
}
