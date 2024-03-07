package mg.mahatoky.domy.view.swing.model;

import mg.mahatoky.domy.model.*;

/**
 * @author mtk_ext
 */
public class DominoFrameModel {

    private String player1Name;
    private String player2Name;
    private String player3Name;
    private Dominoes player1Dominoes;
    private Dominoes player2Dominoes;
    private Dominoes player3Dominoes;
    private int[] scores = new int[3];
    private PlacedDominoes placedDominoes = new PlacedDominoes();
    private Domino lastDomino;
    private PlayerResponse.PLACE lastPlace;

    private Player mainPlayer;
    private Player winner;

    public Dominoes getPlayer1Dominoes() {
        return player1Dominoes;
    }

    public void setPlayer1Dominoes(Dominoes player1Dominoes) {
        this.player1Dominoes = player1Dominoes;
    }

    public Dominoes getPlayer2Dominoes() {
        return player2Dominoes;
    }

    public void setPlayer2Dominoes(Dominoes player2Dominoes) {
        this.player2Dominoes = player2Dominoes;
    }

    public Dominoes getPlayer3Dominoes() {
        return player3Dominoes;
    }

    public void setPlayer3Dominoes(Dominoes player3Dominoes) {
        this.player3Dominoes = player3Dominoes;
    }

    public int[] getScores() {
        return scores;
    }

    public void setScores(int[] scores) {
        this.scores = scores;
    }

    public PlacedDominoes getPlacedDominoes() {
        return placedDominoes;
    }

    public void setPlacedDominoes(PlacedDominoes placedDominoes) {
        this.placedDominoes = placedDominoes;
    }

    public Player getMainPlayer() {
        return mainPlayer;
    }

    public void setMainPlayer(Player mainPlayer) {
        this.mainPlayer = mainPlayer;
    }

    public String getPlayer1Name() {
        return player1Name;
    }

    public void setPlayer1Name(String player1Name) {
        this.player1Name = player1Name;
    }

    public String getPlayer2Name() {
        return player2Name;
    }

    public void setPlayer2Name(String player2Name) {
        this.player2Name = player2Name;
    }

    public String getPlayer3Name() {
        return player3Name;
    }

    public void setPlayer3Name(String player3Name) {
        this.player3Name = player3Name;
    }

    public Domino getLastDomino() {
        return lastDomino;
    }

    public void setLastDomino(Domino lastDomino) {
        this.lastDomino = lastDomino;
    }

    public PlayerResponse.PLACE getLastPlace() {
        return lastPlace;
    }

    public Player getWinner() {
        return winner;
    }

    public void setWinner(Player winner) {
        this.winner = winner;
    }

    public void setLastPlace(PlayerResponse.PLACE lastPlace) {
        this.lastPlace = lastPlace;
    }
}
