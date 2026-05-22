package service;

import model.Board;
import model.Space;
import model.StatusEnum;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class BoardService {
    private final static int BOARD_LIMIT = 9;
    private final Board board;


    public BoardService(final Map<String, String> gameConfig) {
        this.board = new Board(initBoard(gameConfig));
    }

    public List<List<Space>> getSpaces(){
        return this.board.getSpaces();
    }

    public void reset(){
        this.board.reset();
    }

    public boolean hasErrors(){
        return this.board.hasErrors();
    }

    public StatusEnum getStatus(){
        return this.board.getStatus();
    }

    public boolean gameIsFinished(){
        return this.board.gameIsFinished();
    }

    private List<List<Space>> initBoard(Map<String, String> gameConfig) {
        List<List<Space>> spaces = new ArrayList<>();
        for (int i = 0; i < BOARD_LIMIT; i++){
            spaces.add(new ArrayList<>());
            for (int j = 0; j < BOARD_LIMIT; j++){
                var positionConfig = gameConfig.get("%s,%s".formatted(i,j));
                var valueExpected = Integer.parseInt(positionConfig.split(",")[0]);
                var fixedPosition = Boolean.parseBoolean(positionConfig.split(",")[1]);
                var currentSpace = new Space(valueExpected, fixedPosition);
                spaces.get(i).add(currentSpace);
            }
        }
        return spaces;
    }
}
