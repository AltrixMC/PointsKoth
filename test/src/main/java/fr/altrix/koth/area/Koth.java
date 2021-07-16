package fr.altrix.koth.area;

import be.maximvdw.featherboard.api.*;
import be.maximvdw.placeholderapi.*;
import fr.altrix.koth.*;
import org.bukkit.*;
import org.bukkit.configuration.*;
import org.bukkit.entity.*;
import org.bukkit.scheduler.*;

import java.util.*;

public class Koth {

    private String id;
    private String name;
    private int maxTime;
    private int time;
    private Location min, max;
    private Location middle;
    private boolean started;
    private Map<String, Integer> points;
    private List<String> top;

    public Koth(ConfigurationSection section, String id) {
        this.id = id;
        this.name = section.getString("name");
        this.maxTime = section.getInt("time");
        this.time = 0;
        this.started = false;

        World world = Bukkit.getWorld(section.getString("world"));
        if (world == null) {
            KothPlugin.getInstance().log.info("\n\n[PointsKoth] Bad configuration\n\n");
            Bukkit.getPluginManager().disablePlugin(KothPlugin.getInstance());
        }
        Location location1 = new Location(world, section.getInt("first.X"), section.getInt("first.Y"), section.getInt("first.Z"));
        Location location2 = new Location(world, section.getInt("second.X"), section.getInt("second.Y"), section.getInt("second.Z"));

        this.min = getMinimum(location1, location2);
        this.max = getMaximum(location1, location2);
        this.points = new HashMap<>();
        this.top = new ArrayList<>();
        calculateMiddle();
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getMaxTime() {
        return maxTime;
    }

    public void setMaxTime(int maxTime) {
        this.maxTime = maxTime;
    }

    public int getTime() {
        return time;
    }

    public void setTime(int time) {
        this.time = time;
    }

    public Location getMin() {
        return min;
    }

    public void setMin(Location min) {
        this.min = min;
    }

    public Location getMax() {
        return max;
    }

    public void setMax(Location max) {
        this.max = max;
    }

    public Location getMiddle() {
        return middle;
    }

    public void setMiddle(Location middle) {
        this.middle = middle;
    }

    public Boolean getStarted() {
        return started;
    }

    public void setStarted(Boolean started) {
        this.started = started;
    }

    public Map<String, Integer> getPoints() {
        return points;
    }

    public void setPoints(Map<String, Integer> points) {
        this.points = points;
    }

    public List<String> getTop() {
        return top;
    }

    public void setTop(List<String> top) {
        this.top = top;
    }

    private boolean isInAABB(Location loc) {
        return (this.min
                .getBlockX() <= loc.getBlockX() && this.max.getBlockX() >= loc.getBlockX() && this.min
                .getBlockZ() <= loc.getBlockZ() && this.max.getBlockZ() >= loc.getBlockZ() && this.min
                .getBlockY() <= loc.getBlockY() && this.max.getBlockY() >= loc.getBlockY());
    }

    public boolean isInArea(OfflinePlayer oPlayer) {
        if (oPlayer == null || !oPlayer.isOnline() || oPlayer.getPlayer() == null)
            return false;
        Player player = oPlayer.getPlayer();
        if (player.isDead())
            return false;
        if (player.getWorld() != this.min.getWorld())
            return false;
        Location loc = player.getLocation();
        return isInAABB(loc);
    }

    private Location getMinimum(Location loc1, Location loc2) {
        return new Location(loc1.getWorld(), Math.min(loc1.getX(), loc2.getX()), Math.min(loc1.getY(), loc2.getY()), Math.min(loc1.getZ(), loc2.getZ()));
    }

    private Location getMaximum(Location loc1, Location loc2) {
        return new Location(loc1.getWorld(), Math.max(loc1.getX(), loc2.getX()), Math.max(loc1.getY(), loc2.getY()), Math.max(loc1.getZ(), loc2.getZ()));
    }

    private void calculateMiddle() {
        this.middle = this.min.clone().add(this.max.clone()).multiply(0.5D);
    }

    private void clearAll() {
        started = false;
        top = new ArrayList<>();
        points = new HashMap<>();
    }

    public void finish() {
        String message = ChatColor.translateAlternateColorCodes('&', KothPlugin.getInstance().getConfig().getString("messages.koth-finish").replace("{kothName}", name));
        for (Player p : Bukkit.getOnlinePlayers()) {
            p.sendMessage(PlaceholderAPI.replacePlaceholders(p, message));
            FeatherBoardAPI.resetDefaultScoreboard(p);
        }
        new BukkitRunnable() {
            @Override
            public void run() {
                rewards();
                clearAll();
                KothPlugin.getInstance().actualKoth = null;
            }
        }.runTask(KothPlugin.getInstance());

    }

    private void rewards() {
        for (int i = 1; i < 6; i++) {
            if (top.size() >= i) {
                String factionName = top.get(i - 1);

                String command = KothPlugin.getInstance().getConfig().getString("reward-" + i).replace("{faction}", factionName);
                Bukkit.dispatchCommand(Bukkit.getConsoleSender(), command);
            }
        }
    }
}
