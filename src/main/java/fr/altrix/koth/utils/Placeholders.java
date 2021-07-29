package fr.altrix.koth.utils;

import fr.altrix.koth.*;
import fr.altrix.koth.area.*;
import me.clip.placeholderapi.expansion.*;
import org.bukkit.entity.*;

import java.util.*;

public class Placeholders extends PlaceholderExpansion{

        private KothPlugin plugin;
        private Koth koth;

        public Placeholders(KothPlugin plugin, Koth koth){
            this.plugin = plugin;
            this.koth = koth;
        }

        @Override
        public boolean persist(){
            return true;
        }

        @Override
        public boolean canRegister(){
            return true;
        }

        @Override
        public String getAuthor(){
            return plugin.getDescription().getAuthors().toString();
        }

        @Override
        public String getIdentifier(){
            return "koth";
        }

        @Override
        public String getVersion(){
            return plugin.getDescription().getVersion();
        }

        @Override
        public String onPlaceholderRequest(Player player, String identifier) {

            if (player == null) return null;

            for (int i = 1; i < 6; i++) {
                if (identifier.equals(i + "_points")) {
                    List<String> list = plugin.getKothManager().actualKoth.getTop();
                    if (list != null && list.size() >= i && list.get(i - 1) != null)
                        return String.valueOf(plugin.getKothManager().actualKoth.getPoints().get(list.get(i - 1)));
                    return "0";
                } else if (identifier.equals(i + "_faction")) {
                    List<String> list = plugin.getKothManager().actualKoth.getTop();
                    if (list != null && list.size() >= i && list.get(i - 1) != null)
                        return list.get(i - 1);
                    return "None";
                }
            }

            if (identifier.equalsIgnoreCase("x")) {
                if (plugin.getKothManager().actualKoth.getStarted())
                    return String.valueOf(plugin.getKothManager().actualKoth.getMiddle().getBlockX());
                return "None";
            }

            if (identifier.equalsIgnoreCase("z")) {
                if (plugin.getKothManager().actualKoth.getStarted())
                    return String.valueOf(plugin.getKothManager().actualKoth.getMiddle().getBlockZ());
                return "None";
            }

            if (identifier.equalsIgnoreCase("time")) {
                int restTime = plugin.getKothManager().actualKoth.getMaxTime() - plugin.getKothManager().actualKoth.getTime();
                if (plugin.getKothManager().actualKoth.getStarted())
                    return String.valueOf(restTime);
                return "None";
            }

            if (identifier.equalsIgnoreCase("my")) {
                Map<String, Integer> map = plugin.getKothManager().actualKoth.getPoints();
                String factionName = plugin.getInterfacesManager().iFactions.getFactionTagByPlayer(player);
                if (map.get(factionName) != null)
                    return String.valueOf(map.get(factionName));
                return "0";
            }

            return null;
        }
}
