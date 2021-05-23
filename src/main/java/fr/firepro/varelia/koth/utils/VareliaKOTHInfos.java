package fr.firepro.varelia.koth.utils;

import fr.firepro.varelia.koth.VareliaKOTH;
import lombok.Getter;
import org.bukkit.configuration.file.YamlConfiguration;

import java.io.File;

public class VareliaKOTHInfos {

    File configFile = new File(VareliaKOTH.getInstance().getDataFolder(), "config.yml");
    YamlConfiguration config = YamlConfiguration.loadConfiguration(configFile);

    public VareliaKOTHInfos(String kothName) {
        this.kothName = kothName;
        kothCoordsWorld = config.getString("koth." + kothName + ".location.world");

        kothCoords1X = config.getInt("koth." + kothName + ".location.1.X");
        kothCoords1Y = config.getInt("koth." + kothName + ".location.1.Y");
        kothCoords1Z = config.getInt("koth." + kothName + ".location.1.Z");

        kothCoords2X = config.getInt("koth." + kothName + ".location.2.X");
        kothCoords2Y = config.getInt("koth." + kothName + ".location.2.Y");
        kothCoords2Z = config.getInt("koth." + kothName + ".location.2.Z");

        time = config.getInt("koth." + kothName + ".time");
    }

    @Getter public String kothName;

    @Getter public String kothCoordsWorld;

    @Getter public Integer kothCoords1X;
    @Getter public Integer kothCoords1Y;
    @Getter public Integer kothCoords1Z;

    @Getter public Integer kothCoords2X;
    @Getter public Integer kothCoords2Y;
    @Getter public Integer kothCoords2Z;

    @Getter public Integer time;

    @Getter public String rewardCommand1 = config.getString("reward.1");
    @Getter public String rewardCommand2 = config.getString("reward.2");
    @Getter public String rewardCommand3 = config.getString("reward.3");
}
