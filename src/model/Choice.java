package model;

import java.util.Arrays;

public enum Choice {
    ROCK(0), PAPER(1), SESSIORS(2);

    public int id;

    Choice(int id) {
        this.id = id;
    }

    public int getId() {
        return id;
    }

    public static Choice getChoice(int id) {
        return Arrays.stream(Choice.values())
                .filter(value -> value.id == id)
                .findFirst()
                .orElse(Choice.ROCK);
    }

    /**
     * 두 개의 Choice를 비교하여 수행 결과를 반환합니다.
     *
     * @param a
     * @param b
     * @return {@link Result}, a의 입장에서의 결과를 반환합니다.
     */
    public static Result getResult(Choice a, Choice b) {
        if (a == ROCK) {
            if (b == ROCK) return Result.DRAW;
            if (b == PAPER) return Result.LOSE;
            else return Result.WIN;
        } else if (a == PAPER) {
            if (b == ROCK) return Result.WIN;
            if (b == PAPER) return Result.DRAW;
            else return Result.LOSE;
        } else {
            if (b == ROCK) return Result.LOSE;
            if (b == PAPER) return Result.WIN;
            else return Result.DRAW;
        }
    }
}