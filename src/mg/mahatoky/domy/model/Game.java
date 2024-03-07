package mg.mahatoky.domy.model;

import java.util.Set;

/**
 * @author mtk_ext
 */
public class Game {

    private Player player1;
    private Player player2;
    private Player player3;
    private Dominoes remainingDominoes;
    private int maxScore;
    private int milliSecondStepPause = 700;

    public Game() {
    }

    public Player getPlayer1() {
        return player1;
    }

    public void setPlayer1(Player player1) {
        this.player1 = player1;
    }

    public Player getPlayer2() {
        return player2;
    }

    public void setPlayer2(Player player2) {
        this.player2 = player2;
    }

    public Player getPlayer3() {
        return player3;
    }

    public void setPlayer3(Player player3) {
        this.player3 = player3;
    }

    public Dominoes getRemainingDominoes() {
        return remainingDominoes;
    }

    public void setRemainingDominoes(Dominoes remainingDominoes) {
        this.remainingDominoes = remainingDominoes;
    }

    public int getMaxScore() {
        return maxScore;
    }

    public int getMilliSecondStepPause() {
        return milliSecondStepPause;
    }

    public void setMilliSecondStepPause(int milliSecondStepPause) {
        this.milliSecondStepPause = milliSecondStepPause;
    }

    public void setMaxScore(int maxScore) {
        this.maxScore = maxScore;
    }

    public static class GameBuilder {

        private Player player1;
        private Player player2;
        private Player player3;
        private Dominoes remainingDominoes;
        private int maxScore;
        private int milliSecondStepPause = 700;

        public GameBuilder setRemainingDominoes(Dominoes remainingDominoes) {
            this.remainingDominoes = remainingDominoes;
            return this;
        }

        public GameBuilder setMaxScore(int maxScore) {
            this.maxScore = maxScore;
            return this;
        }

        public GameBuilder setPlayer1(Player player1) {
            this.player1 = player1;
            return this;
        }

        public GameBuilder setPlayer2(Player player2) {
            this.player2 = player2;
            return this;
        }

        public GameBuilder setPlayer3(Player player3) {
            this.player3 = player3;
            return this;
        }

        public GameBuilder setMilliSecondStepPause(int milliSecondStepPause) {
            this.milliSecondStepPause = milliSecondStepPause;
            return this;
        }

        public Game build(){
            Game game = new Game();
            game.setPlayer1(this.player1);
            game.setPlayer2(this.player2);
            game.setPlayer3(this.player3);
            game.setMaxScore(this.maxScore);
            game.setRemainingDominoes(this.remainingDominoes);
            game.setMilliSecondStepPause(this.milliSecondStepPause);
            return game;
        }
    }
}
