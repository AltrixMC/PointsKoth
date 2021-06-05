package fr.firepro.varelia.koth.scheduler;

import be.maximvdw.featherboard.api.FeatherBoardAPI;
import be.maximvdw.placeholderapi.PlaceholderAPI;
import be.maximvdw.placeholderapi.PlaceholderReplaceEvent;
import be.maximvdw.placeholderapi.PlaceholderReplacer;
import com.massivecraft.factions.FPlayer;
import com.massivecraft.factions.FPlayers;
import com.massivecraft.factions.Faction;
import com.massivecraft.factions.Factions;
import fr.firepro.varelia.koth.VareliaKOTH;
import fr.firepro.varelia.koth.utils.ValueComparator;
import fr.firepro.varelia.koth.utils.VareliaKOTHInfos;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.entity.Player;
import org.bukkit.scheduler.BukkitRunnable;

import java.util.*;

public class VareliaKOTHScheduler {

    List<Player> list = new ArrayList<>();

    public void startGame(String name) {
        VareliaKOTHInfos vareliaKOTHInfos = new VareliaKOTHInfos(name);
        Location area1 = new Location(Bukkit.getWorld(vareliaKOTHInfos.kothCoordsWorld), vareliaKOTHInfos.kothCoords1X, vareliaKOTHInfos.kothCoords1Y, vareliaKOTHInfos.kothCoords1Z);
        Location area2 = new Location(Bukkit.getWorld(vareliaKOTHInfos.kothCoordsWorld), vareliaKOTHInfos.kothCoords2X, vareliaKOTHInfos.kothCoords2Y, vareliaKOTHInfos.kothCoords2Z);
        Location min = getMinimum(area1, area2);Location max = getMaximum(area1, area2);
        finishScheduler(vareliaKOTHInfos.time, name);
        update(name);
        for (int i = 1; i < 6; i++) { registerPlaceholder(name, i);}
        registerOthersPlaceholders(name, vareliaKOTHInfos.kothCoords2X, vareliaKOTHInfos.kothCoords2Z);
        Bukkit.broadcastMessage(ChatColor.translateAlternateColorCodes('&', VareliaKOTH.getInstance().getConfig().getString("messages.koth-started").replace("{kothX}", String.valueOf(vareliaKOTHInfos.kothCoords1X)).replace("{kothZ}", String.valueOf(vareliaKOTHInfos.kothCoords1Z))));

        new BukkitRunnable() {
            @Override
            public void run() {
                HashMap<String, Boolean> started = VareliaKOTH.getInstance().getStarted();
                if (started.get(name)) {
                    HashMap<Faction, Integer> factionsPoints = VareliaKOTH.getInstance().getFactionsPoints();
                    for (Player p : Bukkit.getOnlinePlayers()) {
                        FeatherBoardAPI.showScoreboard(p, "koth");
                        if (isInArea(p, min, max) && !p.isDead()) {
                            if (list.contains(p)) {
                                FPlayer fPlayer = FPlayers.getInstance().getByPlayer(p);
                                Faction faction = fPlayer.getFaction();
                                if (faction != Factions.getInstance().getWilderness()) {
                                    if (factionsPoints.containsKey(faction)) {
                                        VareliaKOTH.getInstance().addFactionPoints(faction, factionsPoints.get(faction) + 1);
                                    } else {
                                        VareliaKOTH.getInstance().addFactionPoints(faction, 1);
                                    }
                                }
                            }
                            list.add(p);
                        } else list.remove(p);
                    }
                } else {
                    String message = ChatColor.translateAlternateColorCodes('&', VareliaKOTH.getInstance().getConfig().getString("messages.koth-finish"));
                    for (Player p : Bukkit.getOnlinePlayers()) { p.sendMessage(PlaceholderAPI.replacePlaceholders(p, message)); FeatherBoardAPI.resetDefaultScoreboard(p);}
                    VareliaKOTH.getInstance().setFactionsPoints(new HashMap<>());
                    VareliaKOTH.getInstance().setPlaceholder(new HashMap<>());
                    cancel();
                }
            }
        }.runTaskTimerAsynchronously(VareliaKOTH.getInstance(), 0, 20);
    }

    public void finishScheduler(Integer time, String name) {
        Bukkit.getScheduler().runTaskLaterAsynchronously(VareliaKOTH.getInstance(), new Runnable() {
            @Override
            public void run() {
                HashMap<String, Boolean> started = VareliaKOTH.getInstance().getStarted();
                started.put(name, false);
                VareliaKOTH.getInstance().setStarted(started);
            }
        }, time * 20 * 60);
    }

    public void update(String name) {
        new BukkitRunnable() {
            @Override
            public void run() {
                HashMap<String, Boolean> started = VareliaKOTH.getInstance().getStarted();
                HashMap<Integer, String> placeholder = VareliaKOTH.getInstance().getPlaceholder();
                if (started.get(name)) {
                    HashMap<Faction, Integer> hm = VareliaKOTH.getInstance().getFactionsPoints();
                    if (hm != null) {
                        ValueComparator bvc = new ValueComparator(hm);
                        TreeMap<Faction, Integer> sorted_map = new TreeMap<Faction, Integer>(bvc);
                        sorted_map.putAll(hm);
                        for (int i = 1; i < 6; i++) {
                            try {
                                Map.Entry<Faction, Integer> e = sorted_map.pollFirstEntry();
                                Faction faction = e.getKey();
                                int score = e.getValue();

                                placeholder.put(i, faction.getTag() + ":" + score);
                                if (score >= VareliaKOTH.getInstance().getConfig().getInt("koth.max-score")) {
                                    Bukkit.dispatchCommand(Bukkit.getConsoleSender(), VareliaKOTH.getInstance().getConfig().getString("reward-" + i).replace("{faction}", faction.getTag()));
                                    if (VareliaKOTH.getInstance().getStarted().containsKey(name)) {
                                        stopKoth(name);
                                    }
                                }
                            } catch (Exception ignored) {}
                        }
                        VareliaKOTH.getInstance().setPlaceholder(placeholder);
                    }
                }
            }
        }.runTaskTimer(VareliaKOTH.getInstance(), 0, 20);
    }

    public Boolean isInArea(Player player, Location min, Location max) {
        Location location = player.getLocation();
        if (min.getBlockX() <= location.getBlockX() && max.getBlockX() >= location.getBlockX()) {
            if (min.getBlockZ() <= location.getBlockZ() && max.getBlockZ() >= location.getBlockZ()) {
                return min.getBlockY() <= location.getBlockY() && max.getBlockY() >= location.getBlockY();
            }
        }
        return false;
    }

    public void registerPlaceholder(String name, Integer number) {
        PlaceholderAPI.registerPlaceholder(VareliaKOTH.getInstance(), "koth" + number + "_points", new PlaceholderReplacer() {
            @Override
            public String onPlaceholderReplace(PlaceholderReplaceEvent placeholderReplaceEvent) {
                HashMap<String, Boolean> started = VareliaKOTH.getInstance().getStarted();
                HashMap<Integer, String> placeholder = VareliaKOTH.getInstance().getPlaceholder();
                if (placeholder.get(number) != null) {
                    return placeholder.get(number).split(":")[1];
                }
                return "Aucun";
            }
        });
        PlaceholderAPI.registerPlaceholder(VareliaKOTH.getInstance(), "koth" + number + "_faction", new PlaceholderReplacer() {
            @Override
            public String onPlaceholderReplace(PlaceholderReplaceEvent placeholderReplaceEvent) {
                HashMap<String, Boolean> started = VareliaKOTH.getInstance().getStarted();
                HashMap<Integer, String> placeholder = VareliaKOTH.getInstance().getPlaceholder();
                if (placeholder.get(number) != null) {
                    return placeholder.get(number).split(":")[0];
                }
                return "Aucun";
            }
        });
    }

    public void registerOthersPlaceholders(String name, Integer x, Integer z) {
        PlaceholderAPI.registerPlaceholder(VareliaKOTH.getInstance(), "kothX", new PlaceholderReplacer() {
            @Override
            public String onPlaceholderReplace(PlaceholderReplaceEvent placeholderReplaceEvent) {
                HashMap<String, Boolean> started = VareliaKOTH.getInstance().getStarted();
                if (started.get(name)) {
                    return String.valueOf(x);
                }
                return "Aucun";
            }
        });

        PlaceholderAPI.registerPlaceholder(VareliaKOTH.getInstance(), "kothZ", new PlaceholderReplacer() {
            @Override
            public String onPlaceholderReplace(PlaceholderReplaceEvent placeholderReplaceEvent) {
                HashMap<String, Boolean> started = VareliaKOTH.getInstance().getStarted();
                if (started.get(name)) {
                    return String.valueOf(z);
                }
                return "Aucun";
            }
        });

        PlaceholderAPI.registerPlaceholder(VareliaKOTH.getInstance(), "kothmy_points", new PlaceholderReplacer() {
            @Override
            public String onPlaceholderReplace(PlaceholderReplaceEvent placeholderReplaceEvent) {
                HashMap<String, Boolean> started = VareliaKOTH.getInstance().getStarted();
                if (started.get(name)) {
                    FPlayer fPlayer = FPlayers.getInstance().getByPlayer(placeholderReplaceEvent.getPlayer());
                    Faction faction = fPlayer.getFaction();
                    if (!faction.isWilderness() && VareliaKOTH.getInstance().getFactionsPoints().containsKey(faction)) {
                        return String.valueOf(VareliaKOTH.getInstance().getFactionsPoints().get(faction));
                    }
                }
                return "0";
            }
        });
    }



    private Location getMinimum(Location loc1, Location loc2) {
        return new Location(loc1.getWorld(), Math.min(loc1.getX(), loc2.getX()), Math.min(loc1.getY(), loc2.getY()), Math.min(loc1.getZ(), loc2.getZ()));
    }

    private Location getMaximum(Location loc1, Location loc2) {
        return new Location(loc1.getWorld(), Math.max(loc1.getX(), loc2.getX()), Math.max(loc1.getY(), loc2.getY()), Math.max(loc1.getZ(), loc2.getZ()));
    }

    public void startKoth(String name) {
        System.out.println(name);
        HashMap<String, Boolean> started = VareliaKOTH.getInstance().getStarted();
        started.put(name, true);
        VareliaKOTH.getInstance().setStarted(started);
        startGame(name);
    }

    public void stopKoth(String name) {
        HashMap<String, Boolean> started = VareliaKOTH.getInstance().getStarted();
        started.put(name, false);
    }
}
