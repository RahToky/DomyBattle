package mg.mahatoky.domy.utils;

import mg.mahatoky.domy.model.*;
import mg.mahatoky.domy.view.console.ConsolePrinter;
import mg.mahatoky.domy.view.swing.model.DominoFrameModel;

import java.util.*;
import java.util.function.Consumer;

/**
 * @author mtk_ext
 */
public class GameManager {

    private Game game;
    private PlacedDominoes placedDominoes;
    private GameHistory gameHistory;
    private Player mainPlayer;
    private Player currentPlayer;
    private Map<Player, Dominoes> playerDominoesMap;
    private Set<Domino> remainingDominoes;
    private Map<Player, Integer> scores;
    private int step;

    private DominoFrameModel dominoFrameModel = new DominoFrameModel();


    public GameManager(Game game) {
        setGame(game);
    }

    public void start(Consumer<DominoFrameModel> displayableGameConsumer) throws Exception {
        this.mainPlayer = game.getPlayer1();
        this.currentPlayer = mainPlayer;
        initScores();
        while (getWinner() == null) {
            distribute();

            currentPlayer = mainPlayer;
            //TODO change to observer/observable
            notifyConsumer(displayableGameConsumer);

            int iDontHave = 0;
            boolean emptyDominoes = false;
            System.out.println("---> STEP:" + step + ", main=> " + currentPlayer.getName());
            while (iDontHave < 3 && !emptyDominoes) {
                Dominoes playerPlayableDominoes = getPlayableDominoes(currentPlayer);
                if (playerPlayableDominoes.isEmpty()) {
                    System.out.println("---> tsy manana i " + currentPlayer.getName());
                    dominoFrameModel.setLastDomino(null);
                    iDontHave++;
                } else {
                    PlayerResponse playerResponse = currentPlayer.play(playerDominoesMap.get(currentPlayer), playerPlayableDominoes, placedDominoes);
                    if (playerResponse == null || !playerResponse.isValid()) {
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
                            System.out.println("--- ERROR: can't place " + currentPlayer.getName() + "'s domino " + playerResponse.getDomino() + ", head:" + placedDominoes.getHead() + ", tail:" + placedDominoes.getTail() + ". Cause:" + e.getMessage());
                            e.printStackTrace();
                            iDontHave++;
                        }
                    }
                }


                notifyConsumer(displayableGameConsumer);
                //ConsolePrinter.display(placedDominoes);
                currentPlayer = whoIsNextPlayer(currentPlayer);
                Thread.sleep(600);
            }
            Player stepWinner = calculateStepWinner();
            if (stepWinner != null) {
                System.out.println("-> STEP WINNER:" + stepWinner.getName() + ", " + playerDominoesMap.get(game.getPlayer1()).countEyes() + ", " + playerDominoesMap.get(game.getPlayer2()).countEyes() + ", " + playerDominoesMap.get(game.getPlayer3()).countEyes());
            } else {
                System.out.println("-> STEP NO WINNER" + playerDominoesMap.get(game.getPlayer1()).countEyes() + ", " + playerDominoesMap.get(game.getPlayer2()).countEyes() + ", " + playerDominoesMap.get(game.getPlayer3()).countEyes());
            }
            notifyConsumer(displayableGameConsumer);
            changeMainPlayer();
            step++;
            Thread.sleep(1500);
        }
        notifyConsumer(displayableGameConsumer);
        System.out.println("\n--------- E N D ----------");
        System.out.println("***** CONGRATULATION FOR WINNER " + getWinner().getName() + "  ****");
        scores.forEach((player, finalScore) -> System.out.println("-> " + player.getName() + "=" + finalScore + ".Points"));
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

    private Player calculateStepWinner() {
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
        this.remainingDominoes = splitDominoes.get(4);
        this.placedDominoes.clear();
    }

    /**
     * Calculate score and give the winner
     *
     * @return winner
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
        this.remainingDominoes = new HashSet<>();
        this.playerDominoesMap = new HashMap<>();
        this.scores = new HashMap<>();
    }
}
