package fr.altrix.koth.languages;

public interface ILanguages {

    //Reload command
    String reloadedSuccessfully();
    String reloadUtility();
    String kothIsStartedPleaseStop();

    //Start command
    String kothAlreadyStarted();
    String kothStarted();

    String startUtility();
    String startParameters();

    //Status command
    String noKothIsStarted();
    String kothIsStarted();

    String statusUtility();

    //Stop command
    String kothStopped();

    String stopParameters();
    String stopUtility();

    //All
    String kothNotFound();
}
