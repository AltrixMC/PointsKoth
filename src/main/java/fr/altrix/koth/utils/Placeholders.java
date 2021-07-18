package fr.altrix.koth.utils;

import fr.altrix.koth.*;
import fr.altrix.koth.area.*;
import me.clip.placeholderapi.expansion.*;
import org.bukkit.entity.*;

import java.util.*;

public class Placeholders extends PlaceholderExpansion{

        private KothPlugin plugin;
        private Koth koth;

        /**
         * Since we register the expansion inside our own plugin, we
         * can simply use this method here to get an instance of our
         * plugin.
         *
         * @param plugin
         *        The instance of our plugin.
         */
        public Placeholders(KothPlugin plugin, Koth koth){
            this.plugin = plugin;
            this.koth = koth;
        }

        /**
         * Because this is an internal class,
         * you must override this method to let PlaceholderAPI know to not unregister your expansion class when
         * PlaceholderAPI is reloaded
         *
         * @return true to persist through reloads
         */
        @Override
        public boolean persist(){
            return true;
        }

        /**
         * Because this is a internal class, this check is not needed
         * and we can simply return {@code true}
         *
         * @return Always true since it's an internal class.
         */
        @Override
        public boolean canRegister(){
            return true;
        }

        /**
         * The name of the person who created this expansion should go here.
         * <br>For convienience do we return the author from the plugin.yml
         *
         * @return The name of the author as a String.
         */
        @Override
        public String getAuthor(){
            return plugin.getDescription().getAuthors().toString();
        }

        /**
         * The placeholder identifier should go here.
         * <br>This is what tells PlaceholderAPI to call our onRequest
         * method to obtain a value if a placeholder starts with our
         * identifier.
         * <br>The identifier has to be lowercase and can't contain _ or %
         *
         * @return The identifier in {@code %<identifier>_<value>%} as String.
         */
        @Override
        public String getIdentifier(){
            return "koth";
        }

        /**
         * This is the version of the expansion.
         * <br>You don't have to use numbers, since it is set as a String.
         *
         * For convienience do we return the version from the plugin.yml
         *
         * @return The version as a String.
         */
        @Override
        public String getVersion(){
            return plugin.getDescription().getVersion();
        }

        /**
         * This is the method called when a placeholder with our identifier
         * is found and needs a value.
         * <br>We specify the value identifier in this method.
         * <br>Since version 2.9.1 can you use OfflinePlayers in your requests.
         *
         * @param  player
         *         A {@link org.bukkit.Player Player}.
         * @param  identifier
         *         A String containing the identifier/value.
         *
         * @return possibly-null String of the requested identifier.
         */
        @Override
        public String onPlaceholderRequest(Player player, String identifier) {

            for (int i = 1; i < 6; i++) {
                if (identifier.equals(i + "_points")) {
                    List<String> list = KothPlugin.getInstance().actualKoth.getTop();
                    if (list != null && list.size() >= i && list.get(i - 1) != null)
                        return String.valueOf(KothPlugin.getInstance().actualKoth.getPoints().get(list.get(i - 1)));
                    return "0";
                } else if (identifier.equals(i + "_faction")) {
                    List<String> list = KothPlugin.getInstance().actualKoth.getTop();
                    if (list != null && list.size() >= i && list.get(i - 1) != null)
                        return list.get(i - 1);
                    return "None";
                }
            }

            if (identifier.equalsIgnoreCase("x")) {
                if (KothPlugin.getInstance().actualKoth.getStarted())
                    return String.valueOf(KothPlugin.getInstance().actualKoth.getMiddle().getBlockX());
                return "None";
            }

            if (identifier.equalsIgnoreCase("z")) {
                if (KothPlugin.getInstance().actualKoth.getStarted())
                    return String.valueOf(KothPlugin.getInstance().actualKoth.getMiddle().getBlockZ());
                return "None";
            }

            if (identifier.equalsIgnoreCase("time")) {
                int restTime = KothPlugin.getInstance().actualKoth.getMaxTime() - KothPlugin.getInstance().actualKoth.getTime();
                if (KothPlugin.getInstance().actualKoth.getStarted())
                    return String.valueOf(restTime);
                return "None";
            }

            if (identifier.equalsIgnoreCase("my")) {
                Map<String, Integer> map = KothPlugin.getInstance().actualKoth.getPoints();
                String factionName = KothPlugin.getInstance().iFactions.getFactionTagByPlayer(player);
                if (map.get(factionName) != null)
                    return String.valueOf(map.get(factionName));
                return "0";
            }

            return null;
        }
}
