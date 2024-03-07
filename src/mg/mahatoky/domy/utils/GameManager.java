package mg.mahatoky.domy.utils;

import mg.mahatoky.domy.model.*;
import mg.mahatoky.domy.view.swing.model.DominoFrameModel;

import java.util.*;
import java.util.function.Consumer;

/**
 * Manage the main game logics
 * @author mtk_ext
 */
public class GameManager {

    private Game game;
    private PlacedDominoes placedDominoes;
    private Player mainPlayer;
    private Player currentPlayer;
    private Map<Player, Dominoes> playerDominoesMap;
    private Map<Player, Integer> scores;

    private DominoFrameModel dominoFrameModel = new DominoFrameModel();


    public GameManager(Game game) {
        setGame(game);
    }

    /**
     * while {@link #getWinner()} is null
     * Call  {@link Player#play(Dominoes, Dominoes, PlacedDominoes)} while players have dominoes and can play
     * Notify displayableGameConsumer every step finished
     * Notify displayableGameConsumer when have game winner and stop game
     * During game, if Player don't give the right {@link PlayerResponse.PLACE}, switch to good place or just skip player turn if it can't be switched
     * Set some useful properties to {@link #dominoFrameModel} during step to display on {@link mg.mahatoky.domy.view.swing.frame.DominoFrame}
     * @param displayableGameConsumer
     * @throws Exception
     */
    public void start(Consumer<DominoFrameModel> displayableGameConsumer) throws Exception {
        this.mainPlayer = game.getPlayer1();
        this.currentPlayer = mainPlayer;
        initScores();
        while (getWinner() == null) {
            distribute();
            currentPlayer = mainPlayer;
            dominoFrameModel.setStepWinner(null);
            //TODO change to observer/observable
            notifyConsumer(displayableGameConsumer);

            int iDontHave = 0;
            boolean emptyDominoes = false;
            while (iDontHave < 3 && !emptyDominoes) {
                Dominoes playerPlayableDominoes = getPlayableDominoes(currentPlayer);
                if (playerPlayableDominoes.isEmpty()) {
                    dominoFrameModel.setLastDomino(null);
                    iDontHave++;
                } else {
                    PlayerResponse playerResponse = currentPlayer.play(playerDominoesMap.get(currentPlayer), playerPlayableDominoes, placedDominoes);
                    if (playerResponse == null || !playerResponse.isValid()) {
                        dominoFrameModel.setLastDomino(null);
                        iDontHave++;
                    } else {
                        iDontHave = 0;
                        try {
                            if (PlayerResponse.PLACE.HEAD.equals(playerResponse.getPlace())) {
                                if(!placedDominoes.matchHead(playerResponse.getDomino())){
                                    dominoFrameModel.setLastPlace(PlayerResponse.PLACE.TAIL);
                                    placedDominoes.addToTail(playerResponse.getDomino());
                                }else {
                                    dominoFrameModel.setLastPlace(PlayerResponse.PLACE.HEAD);
                                    placedDominoes.addToHead(playerResponse.getDomino());
                                }
                            } else {
                                if(!placedDominoes.matchTail(playerResponse.getDomino())){
                                    dominoFrameModel.setLastPlace(PlayerResponse.PLACE.HEAD);
                                    placedDominoes.addToHead(playerResponse.getDomino());
                                }else {
                                    dominoFrameModel.setLastPlace(PlayerResponse.PLACE.TAIL);
                                    placedDominoes.addToTail(playerResponse.getDomino());
                                }
                            }
                            playerDominoesMap.get(currentPlayer).remove(playerResponse.getDomino());
                            dominoFrameModel.setLastDomino(playerResponse.getDomino());
                            emptyDominoes = playerDominoesMap.get(currentPlayer).isEmpty();
                        } catch (Exception e) {
                            e.printStackTrace();
                            dominoFrameModel.setLastDomino(null);
                            iDontHave++;
                        }
                    }
                }
                notifyConsumer(displayableGameConsumer);
                //ConsolePrinter.display(placedDominoes); DISPLAY ON CONSOLE
                currentPlayer = whoIsNextPlayer(currentPlayer);
                Thread.sleep(game.getMilliSecondStepPause());
            }
            dominoFrameModel.setStepWinner(calculateStepScores());
            notifyConsumer(displayableGameConsumer);
            changeMainPlayer();
            Thread.sleep(game.getMilliSecondStepPause());
        }
        dominoFrameModel.setWinner(getWinner());
        notifyConsumer(displayableGameConsumer);
    }

    private void notifyConsumer(Consumer<DominoFrameModel> displayableGameConsumer) {
        dominoFrameModel.setMainPlayer(this.currentPlayer);
        dominoFrameModel.setScores(new int[]{scores.get(game.getPlayer1()), scores.get(game.getPlayer2()), scores.get(game.getPlayer3())});
        dominoFrameModel.setPlacedDominoes(placedDominoes);
        dominoFrameModel.setPlayer1Dominoes(playerDominoesMap.get(game.getPlayer1()));
        dominoFrameModel.setPlayer2Dominoes(playerDominoesMap.get(game.getPlayer2()));
        dominoFrameModel.setPlayer3Dominoes(playerDominoesMap.get(game.getPlayer3()));
        dominoFrameModel.setPlayer1Name(game.getPlayer1().getName());
        dominoFrameModel.setPlayer2Name(game.getPlayer2().getName());
        dominoFrameModel.setPlayer3Name(game.getPlayer3().getName());
        displayableGameConsumer.accept(dominoFrameModel);
    }

    private Player calculateStepScores() {
        Player winner = null;
        Map<Player, Integer> stepDominoCount = new HashMap<>(3);
        int p1Count = playerDominoesMap.get(game.getPlayer1()).countEyes();
        int p2Count = playerDominoesMap.get(game.getPlayer2()).countEyes();
        int p3Count = playerDominoesMap.get(game.getPlayer3()).countEyes();

        stepDominoCount.put(game.getPlayer1(), p1Count);
        stepDominoCount.put(game.getPlayer2(), p2Count);
        stepDominoCount.put(game.getPlayer3(), p3Count);

        int pLow = Math.min(Math.min(p1Count, p2Count), p3Count);

        int winnerCount = 0;
        int point = 0;
        for (Player player : stepDominoCount.keySet()) {
            if (stepDominoCount.get(player) != pLow) {
                point += stepDominoCount.get(player);
            } else {
                winner = player;
                winnerCount++;
            }
            if (winnerCount == 2)
                return null;
        }
        scores.put(winner, (scores.get(winner) + point));
        return winner;
    }

    private Player whoIsNextPlayer(Player currentPlayer) {
        if (currentPlayer.equals(game.getPlayer1())) {
            return game.getPlayer2();
        } else if (currentPlayer.equals(game.getPlayer2())) {
            return game.getPlayer3();
        } else {
            return game.getPlayer1();
        }
    }

    public void changeMainPlayer() {
        if (mainPlayer.equals(game.getPlayer1())) {
            mainPlayer = game.getPlayer2();
        } else if (mainPlayer.equals(game.getPlayer2())) {
            mainPlayer = game.getPlayer3();
        } else {
            mainPlayer = game.getPlayer1();
        }
    }

    /**
     * Init all scores to zero
     */
    private void initScores() {
        scores.put(game.getPlayer1(), 0);
        scores.put(game.getPlayer2(), 0);
        scores.put(game.getPlayer3(), 0);
    }

    /**
     * Distribute and clear placed dominoes
     *
     * @throws Exception if game.remainingDominoes length!= 28
     */
    private void distribute() throws Exception {
        Map<Integer, Dominoes> splitDominoes = Shuffler.mixIn4Parts(game.getRemainingDominoes());
        playerDominoesMap.put(game.getPlayer1(), splitDominoes.get(1));
        playerDominoesMap.put(game.getPlayer2(), splitDominoes.get(2));
        playerDominoesMap.put(game.getPlayer3(), splitDominoes.get(3));
        this.placedDominoes.clear();
    }

    /**
     * Calculate score and give the winner
     * @return winner {@link Player}
     */
    public Player getWinner() {
        if (scores.get(game.getPlayer1()) >= game.getMaxScore())
            return game.getPlayer1();
        else if (scores.get(game.getPlayer2()) >= game.getMaxScore())
            return game.getPlayer2();
        else if (scores.get(game.getPlayer3()) >= game.getMaxScore())
            return game.getPlayer3();
        return null;
    }


    private Dominoes getPlayableDominoes(Player player) {
        if (placedDominoes.getDominoes().isEmpty()) {
            return playerDominoesMap.get(player);
        }

        Dominoes playableDominoes = new Dominoes();
        Dominoes playerDominoes = playerDominoesMap.get(player);
        for (Domino d : playerDominoes) {
            if (d.getFace1() == placedDominoes.getHead() ||
                    d.getFace1() == placedDominoes.getTail() ||
                    d.getFace2() == placedDominoes.getHead() ||
                    d.getFace2() == placedDominoes.getTail())
                playableDominoes.add(d);
        }
        return playableDominoes;
    }


    public void setGame(Game game) {
        this.game = game;
        this.placedDominoes = new PlacedDominoes();
        this.playerDominoesMap = new HashMap<>();
        this.scores = new HashMap<>();
    }
}
