package fr.altrix.koth.languages;

import fr.altrix.koth.*;

public class French implements ILanguages {
    @Override
    public String reloadedSuccessfully() {
        return "§6PointsKoth » §7le plugin a été rechargé avecc succès !";
    }

    @Override
    public String reloadUtility() {
        return "recharger le plugin";
    }

    @Override
    public String kothAlreadyStarted() {
        return "§6PointsKoth » §7Un koth est déja en cours ({koth})";
    }

    @Override
    public String kothStarted() {
        return "§6PointsKoth » §7Le koth §6{koth} §7a été démarré";
    }

    @Override
    public String startUtility() {
        return "vous permet de démarrer un koth";
    }

    @Override
    public String startParameters() {
        return "<nom>";
    }

    @Override
    public String noKothIsStarted() {
        return "§6PointsKoth » §7Aucun koth n'est démarré";
    }

    @Override
    public String kothIsStarted() {
        return "§6PointsKoth » §7Le koth §6{koth} §7est démarré";
    }

    @Override
    public String statusUtility() {
        return "avoir le koth actuel";
    }

    @Override
    public String kothStopped() {
        return "§6PointsKoth » §7Le koth §6{koth} §7a été stoppé";
    }

    @Override
    public String stopParameters() {
        return "<nom>";
    }

    @Override
    public String stopUtility() {
        return "vous permet d'arreter un koth";
    }

    @Override
    public String kothNotFound() {
        return "§6PointsKoth » §7Le koth §6{koth} §7n'a pas été trouvé";
    }
}
