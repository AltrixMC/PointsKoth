package fr.altrix.koth.factions;


import com.massivecraft.factions.*;
import fr.altrix.koth.*;
import net.prosavage.factionsx.*;
import org.bukkit.entity.*;

public class FactionsUUID implements IFactions {
    @Override
    public String getFactionTagByPlayer(Player playerarg) {
        FPlayer fPlayer = FPlayers.getInstance().getByPlayer(playerarg);
        if (!fPlayer.getFaction().isWilderness())
            return fPlayer.getFaction().getTag();
        return null;
    }
}
