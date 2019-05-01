import model.Choice;
import model.Result;

public class Test {
    // 시행할 횟수 정의
    private static final int RUN_COUNT = 100;

    private static int winCount = 0;
    private static int loseCount = 0;
    private static int drawCount = 0;

    public static void main(String... args) {
        MarkovGame markovGame = new MarkovGame();

        for (int i = 0; i < RUN_COUNT; i++) {
            single(markovGame);
        }

        markovGame.print();

        float winningProbability = (float) winCount / RUN_COUNT;

        StringBuilder builder = new StringBuilder();
        builder.append("Estimated Game")
                .append(System.lineSeparator())
                .append("Running count: ").append(RUN_COUNT)
                .append(System.lineSeparator())
                .append(String.format("winCount: %s, loseCount: %s, drawCount: %s", winCount, loseCount, drawCount))
                .append(System.lineSeparator())
                .append(String.format("winningProbability: %.2f", winningProbability));

        System.out.println(builder.toString());
    }

    public static void single(MarkovGame markovGame) {
        Player player = new Player();

        Choice playerChoice = player.decide();
        Choice robotChoice = markovGame.move();

        Result result = Choice.getResult(playerChoice, robotChoice);
        System.out.println(String.format("Result: %s, %s vs %s", result.name(), playerChoice.name(), robotChoice.name()));

        if (result == Result.WIN) winCount++;
        if (result == Result.LOSE) loseCount++;
        if (result == Result.DRAW) drawCount++;

        markovGame.update(playerChoice);
    }
}
