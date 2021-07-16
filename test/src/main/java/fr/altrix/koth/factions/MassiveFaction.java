package fr.altrix.koth.factions;

import com.massivecraft.factions.entity.*;
import fr.altrix.koth.*;
import org.bukkit.entity.*;

public class MassiveFaction implements IFactions {
    @Override
    public String getFactionTagByPlayer(Player playerarg) {
        return MPlayer.get(playerarg).getFaction().getName();
    }
}
