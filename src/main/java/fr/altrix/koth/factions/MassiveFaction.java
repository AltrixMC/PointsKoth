package fr.altrix.koth.factions;

import com.massivecraft.factions.entity.*;
import org.bukkit.entity.*;

public class MassiveFaction implements IFactions {
    @Override
    public String getFactionTagByPlayer(Player playerArg) {
        return MPlayer.get(playerArg).getFaction().getName();
    }
}
