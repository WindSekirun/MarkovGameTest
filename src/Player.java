import model.Choice;

import java.util.Random;

public class Player {
    private Random random;

    public Player() {
        this.random = new Random();
    }

    public Player(long seed) {
        this.random = new Random(seed);
    }

    public Choice decide() {
        int index = random.nextInt(Choice.values().length);
        return Choice.values()[index];
    }
}
