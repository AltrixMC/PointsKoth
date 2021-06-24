package fr.altrix.koth.utils;

import com.massivecraft.factions.Faction;

import java.util.Comparator;
import java.util.Map;

public class ValueComparator implements Comparator<Faction> {

    private final Map<Faction, Integer> base;
    public ValueComparator(Map<Faction, Integer> map) {
        this.base = map;
    }

    public int compare(Faction a, Faction b) {
        if (base.get(a) >= base.get(b)) {
            return -1;
        } else {
            return 1;
        }
    }
}
