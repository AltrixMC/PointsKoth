package fr.firepro.varelia.koth.utils;

import fr.firepro.varelia.koth.VareliaKOTH;
import lombok.Getter;
import lombok.Setter;
import org.bukkit.configuration.file.YamlConfiguration;

import java.io.File;

public class VareliaKOTHInfos {

    File configFile = new File(VareliaKOTH.getInstance().getDataFolder(), "config.yml");
    YamlConfiguration config = YamlConfiguration.loadConfiguration(configFile);

    @Getter public static VareliaKOTHInfos instance;

    public VareliaKOTHInfos(String kothName) {
        instance = this;

        this.kothName = kothName;
        kothCoordsWorld = config.getString("koth." + kothName + ".location.world");

        kothCoords1X = config.getInt("koth." + kothName + ".location.1.X");
        kothCoords1Y = config.getInt("koth." + kothName + ".location.1.Y");
        kothCoords1Z = config.getInt("koth." + kothName + ".location.1.Z");

        kothCoords2X = config.getInt("koth." + kothName + ".location.2.X");
        kothCoords2Y = config.getInt("koth." + kothName + ".location.2.Y");
        kothCoords2Z = config.getInt("koth." + kothName + ".location.2.Z");


        time = config.getInt("koth." + kothName + ".time");

        rewardCommand1 = config.getString("reward.1");
        rewardCommand2 = config.getString("reward.2");
        rewardCommand3 = config.getString("reward.3");
    }

    public String kothName;
    public String kothCoordsWorld;

    public int kothCoords1X;
    public int kothCoords1Y;
    public int kothCoords1Z;

    public int kothCoords2X;
    public int kothCoords2Y;
    public int kothCoords2Z;


    public Integer time;

    public String rewardCommand1 = config.getString("reward.1");
    public String rewardCommand2 = config.getString("reward.2");
    public String rewardCommand3 = config.getString("reward.3");
}
