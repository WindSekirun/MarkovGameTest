package model;

public enum Result {
    WIN, LOSE, DRAW;

    public Result inverse() {
        if (this == DRAW) return DRAW;

        if (this == WIN) return LOSE;
        else return WIN;
    }
}
