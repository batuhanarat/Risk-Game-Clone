package ConKUeror.domain.controller;

import ConKUeror.domain.model.Board.Board;
import ConKUeror.domain.model.Modes.GameLogic;
import ConKUeror.domain.model.Modes.StartMode;

public class StartHandler {
    private StartMode startMode;
    private Board board;
    private GameLogic gamelogic;

    private static StartHandler instance;

    private  StartHandler(StartMode sMode) {
        this.startMode = sMode;
    }

    public static StartHandler getInstance(StartMode smode) {
        if (instance == null) {
            instance = new StartHandler(smode);
        }
        return instance;
    }

    public void registerAsListener(StartModeListener listener) {
        // startMode.addBuildModeListener(listener);
    }

    public void occupyTerritory(int id) {
     //    startMode
    }

    public String enterMessageString() {


        return startMode.getInitialMessage();

    }
    public void setStartMode() {
        startMode.setStartMode();
    }

    public Board getBoard() {
        return null;
    }


}
