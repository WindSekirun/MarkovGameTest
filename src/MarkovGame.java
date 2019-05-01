import model.Choice;

import java.util.Random;

/**
 * MarkovChain(마르코프 체인)을 이용하여 플레이어의 확률을 모델링하는 클래스
 * <p>
 * 마르코프 체인은 마코프 성질을 가진 discrete-time stochastic process 을 가리켜,
 * '한 상태의 확률은 단지 그 이전의 상태에만 의존하는 것'을 주 목적으로 합니다.
 * <p>
 * 따라서, 해당 클래스에서는 P(qn | qn-1)에 따라 다른 상태로의 이동을 목적으로 합니다.
 *
 * @author Pyxis (pyxis@uzuki.live)
 */
public class MarkovGame {
    // 마크로스 체인, 3개의 상태를 가지고 있음
    private float[][] markovChain;
    // 플레이어가 지금까지 낸 모든 경우에 대한 카운트 (계산 목적)
    private int[] playedCount;

    // 사용자의 마지막 선택지
    private int lastMove;

    public MarkovGame() {
        // 랜덤 값을 이용하여 마크로스 체인을 구성해도 문제는 없지만, 처음부터 데이터를 구성하는 역할을 테스트하기 위해
        // 1에 수렴하도록 초기화
        markovChain = new float[][]{{0.33f, 0.33f, 0.33f}, {0.33f, 0.33f, 0.33f}, {0.33f, 0.33f, 0.33f}};
        playedCount = new int[]{0, 0, 0};
    }

    /**
     * 플레이어가 다음에 낼 값 추측
     * <p>
     * (A) 랜덤한 값과 makrovChain이 잠재적으로 이동한 값을 사용하여 결정된 이동을 생성하거나,
     * (B) 만일 생성하지 못한 경우 다음 결과부터 잠재적으로 이동할 수 있도록 모델을 정의하거나,
     * (C) 이도저도 아닐 경우에는 ROCK를 반환합니다.
     * <p>
     * (A) 의 경우, 마크로스 체인으로 인해 예측된 결과로 볼 수 있습니다.
     * (B) 의 경우, 마크로스 체인으로 인해 예측하지는 못했지만 모델링을 통해 결과를 예측할 수 있도록 합니다.
     * (C) 의 경우, 마크로스 체인이 3가지의 상태를 나타내기 때문에 굳이 이 케이스에 대한 처리를 직접 진행할 필요는 없습니다.
     *
     * @return {@link Choice}
     */
    public Choice move() {
        Random random = new Random();
        float v = random.nextFloat();
        if (v <= markovChain[lastMove][1]) return Choice.PAPER;
        else if (v <= markovChain[lastMove][2] + markovChain[lastMove][1]) return Choice.SESSIORS;
        else return Choice.ROCK;
    }

    /**
     * 플레이어가 낸 결과를 통하여 값을 기록
     * <p>
     * 이 메서드에서는 4개의 과정을 통해 마크로스 체인을 업데이트 합니다.
     * 1. playedCount[lastValue] 의 값으로 마크로스 체인의 각 컬럼에 대해 업데이트를 진행합니다.
     * 2. 이번에 나온 결과에 대해 마크로스 체인에 1을 더해 결과를 반영합니다.
     * 3. playedCount 값을 마지막 값으로 반영합니다.
     * 4. 마지막 값에 대한 마크로스 체인에 대해 playedCount로 나눕니다.
     *
     * @param choice
     */
    public void update(Choice choice) {
        int lastValue = lastMove;
        int nowMove = choice.getId();

        for (int i = 0; i < 3; i++) {
            markovChain[lastValue][i] *= playedCount[lastValue];
        }

        markovChain[lastValue][nowMove] += 1;
        playedCount[lastValue]++;

        for (int i = 0; i < 3; i++) {
            markovChain[lastValue][i] /= playedCount[lastValue];
        }

        lastMove = nowMove;
    }

    /**
     * 지금까지 생성된 마크로스 체인을 출력합니다.
     */
    public void print() {
        StringBuilder builder = new StringBuilder();
        builder.append("Markov Chain")
                .append(System.lineSeparator());

        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 3; j++) {
                builder.append(String.format("| %s to %s: %s ", Choice.getChoice(i).name(), Choice.getChoice(j).name(),
                        markovChain[i][j]));
            }
            builder.append(System.lineSeparator());
        }

        System.out.println(builder.toString());
    }
}