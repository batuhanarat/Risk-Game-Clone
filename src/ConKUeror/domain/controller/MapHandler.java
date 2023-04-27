package src.ConKUeror.domain.controller;

import src.ConKUeror.domain.model.Board.Board;
import src.ConKUeror.domain.model.Modes.GameLogic;

public class MapHandler {
    GameLogic game;

    public MapHandler(GameLogic _game, Board board) {
        this.game = _game; 
        
    }


    public void registerAsListener(MapListener listener) {
        game.addMapListener(listener);


    }

    
}