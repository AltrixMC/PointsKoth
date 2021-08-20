package fr.altrix.koth.listeners;

import fr.altrix.koth.KothPlugin;
import net.md_5.bungee.api.ChatColor;
import net.md_5.bungee.api.chat.*;
import net.md_5.bungee.api.chat.TextComponent;
import org.bukkit.*;
import org.bukkit.entity.Player;
import org.bukkit.event.*;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.scheduler.BukkitRunnable;

import java.text.*;
import java.time.*;
import java.util.*;

public class JoinListener implements Listener {

    private KothPlugin main;
    public JoinListener(KothPlugin main) {this.main = main;}

    @EventHandler(priority = EventPriority.HIGHEST)
    public void onJoin(PlayerJoinEvent event) {
        Player player = event.getPlayer();
        Bukkit.getScheduler().runTaskLater(main, () -> {
            if (player.isOp()) {
                if (!main.isUpToDate()) {
                    player.sendMessage("§6PointsKoth » §7The plugin is no longer up to date ! Please download it on spigotmc");
                    TextComponent textComponent = new TextComponent("https://www.spigotmc.org/resources/pointskoth-points-king-of-the-hill.93590/");
                    textComponent.setColor(ChatColor.GOLD);
                    textComponent.setBold(true);
                    textComponent.setUnderlined(true);
                    textComponent.setClickEvent(new ClickEvent(ClickEvent.Action.OPEN_URL, "https://www.spigotmc.org/resources/pointskoth-points-king-of-the-hill.93590/"));
                    player.spigot().sendMessage(textComponent);
                }
                sendNews(player);
            }
            if (player.getName().equals("FirePro_"))
                player.sendMessage("§6PointsKoth » §7This plugin use PointsKoth ("+main.getDescription().getVersion()+")");
        },45);
    }

    private void sendNews(Player player) {
        Calendar calendarToday = Calendar.getInstance();
        Calendar calendarUpdate = Calendar.getInstance();
        calendarUpdate.setTimeInMillis(main.getLastUpdateTime() * 1000);

        long hours = Duration.between(calendarUpdate.toInstant(), calendarToday.toInstant()).toHours();
        if (hours <= 48) {
            player.sendMessage("");
            player.sendMessage("§6PointsKoth » §7News : " + main.getDesc()
                    .replace("<br> ", "\n§6PointsKoth » §7News : "));
            player.sendMessage("");
        }
    }
}
