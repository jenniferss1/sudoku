package model;

import java.util.Collection;
import java.util.List;

import static java.util.Objects.isNull;
import static java.util.Objects.nonNull;
import static model.StatusEnum.*;

public class Board {
    private final List<List<Space>> spaces;

    public Board(List<List<Space>> spaces) {
        this.spaces = spaces;
    }

    public List<List<Space>> getSpaces() {
        return spaces;
    }

    public StatusEnum getStatus() {
        if (spaces.stream().flatMap(Collection::stream).
                noneMatch(s -> !s.isFixedPosition() && nonNull(s.getActualValue()))){
            return NOT_STARTED;
        }

        return spaces.stream().flatMap(Collection::stream).
                anyMatch(s -> isNull(s.getActualValue())) ? INCOMPLETE : COMPLETE;
    }

    public boolean hasErrors(){
        if (getStatus() == NOT_STARTED){
            return false;
        }
        return spaces.stream().flatMap(Collection::stream).
                anyMatch(s -> nonNull(s.getActualValue()) && !s.getActualValue().equals(s.getExpectedValue()));
    }

    public boolean changeValue(final int col, final int row, final int value) {
        var space = spaces.get(col).get(row);
        if (space.isFixedPosition()) {
            return false;
        }

        space.setActualValue(value);
        return true;
    }

    public boolean clearSpaces(final int col, final int row) {
        var space = spaces.get(col).get(row);
        if (space.isFixedPosition()) {
            return false;
        }
        space.clearSpace();
        return true;
    }

    public void reset(){
        spaces.forEach(Collection::clear);
    }

    public boolean gameIsFinished(){
        return !hasErrors() && getStatus().equals(COMPLETE);
    }
}
