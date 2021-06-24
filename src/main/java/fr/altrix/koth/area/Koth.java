package fr.altrix.koth.area;

import be.maximvdw.featherboard.api.FeatherBoardAPI;
import be.maximvdw.placeholderapi.PlaceholderAPI;
import com.massivecraft.factions.Faction;
import fr.altrix.koth.KothPlugin;
import org.bukkit.*;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.entity.Player;
import org.bukkit.scheduler.BukkitRunnable;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Koth {

    private String id;
    private String name;
    private int time;
    private Location min, max;
    private Location middle;
    private boolean started;
    private Map<Faction, Integer> points;
    private List<Faction> top;

    public Koth(String id, String name, int time, Location min, Location max, Location middle, boolean started, Map<Faction, Integer> points, List<Faction> top) {
        this.id = id;
        this.name = name;
        this.time = time;
        this.min = min;
        this.max = max;
        this.middle = middle;
        this.started = started;
        this.points = points;
        this.top = top;
    }

    public Koth(ConfigurationSection section, String id) {
        this.id = id;
        this.name = section.getString("name");
        this.time = section.getInt("time");
        this.started = false;

        World world = Bukkit.getWorld(section.getString("world"));
        if (world == null) {
            System.out.println("Ta mal fait la config connard !!");
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

    public Map<Faction, Integer> getPoints() {
        return points;
    }

    public void setPoints(Map<Faction, Integer> points) {
        this.points = points;
    }

    public List<Faction> getTop() {
        return top;
    }

    public void setTop(List<Faction> top) {
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
        this.started = false;
        this.top = new ArrayList<>();
        this.points = new HashMap<>();
    }

    public void finish() {
        String message = ChatColor.translateAlternateColorCodes('&', KothPlugin.getInstance().getConfig().getString("messages.koth-finish").replace("{kothName}", name));
        for (Player p : Bukkit.getOnlinePlayers()) {
            p.sendMessage(PlaceholderAPI.replacePlaceholders(p, message));
            FeatherBoardAPI.resetDefaultScoreboard(p);
        }
        if (!Bukkit.isPrimaryThread())
            new BukkitRunnable() {
                @Override
                public void run() {
                    rewards();
                }
            }.runTask(KothPlugin.getInstance());
        else rewards();
        clearAll();
        KothPlugin.getInstance().actualKoth = null;
    }

    private void rewards() {
        for (int i = 1; i < 6; i++) {
            if (top.size() >= i) {
                Faction faction = top.get(i - 1);

                String command = KothPlugin.getInstance().getConfig().getString("reward-" + i).replace("{faction}", faction.getTag());
                Bukkit.dispatchCommand(Bukkit.getConsoleSender(), command);
            }
        }
    }
}
