package fr.altrix.koth.languages;

public class English implements ILanguages {
    @Override
    public String reloadedSuccessfully() {
        return "§6PointsKoth » §7The plugin has been reloaded successfully !";
    }

    @Override
    public String reloadUtility() {
        return "reload the plugin";
    }

    @Override
    public String kothIsStartedPleaseStop() {
        return "§6PointsKoth » §7A koth is in progress. Please stop it before the reload";
    }

    @Override
    public String kothAlreadyStarted() {
        return "§6PointsKoth » §7A koth is already started ({koth})";
    }

    @Override
    public String kothStarted() {
        return "§6PointsKoth » §7The koth §6{koth} §7has been started";
    }

    @Override
    public String startUtility() {
        return "allows to start a koth";
    }

    @Override
    public String startParameters() {
        return "<name>";
    }

    @Override
    public String noKothIsStarted() {
        return "§6PointsKoth » §7No koth is started";
    }

    @Override
    public String kothIsStarted() {
        return "§6PointsKoth » §7The koth §6{koth} §7is started";
    }

    @Override
    public String statusUtility() {
        return "get the current koth";
    }

    @Override
    public String kothStopped() {
        return "§6PointsKoth » §7The koth §6{koth} §7has been stopped";
    }

    @Override
    public String stopParameters() {
        return "<name>";
    }

    @Override
    public String stopUtility() {
        return "allows to stop a koth";
    }

    @Override
    public String kothNotFound() {
        return "§6PointsKoth » §7The koth §6{koth} §7was not found";
    }
}
