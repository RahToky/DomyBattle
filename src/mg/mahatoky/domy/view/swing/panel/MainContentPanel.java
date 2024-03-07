package mg.mahatoky.domy.view.swing.panel;

import mg.mahatoky.domy.view.swing.model.DominoFrameModel;

import javax.swing.*;
import java.awt.*;

/**
 * Contain all others panel
 * The {@link mg.mahatoky.domy.view.swing.frame.DominoFrame}'s content pane
 * @author mtk_ext
 */
public class MainContentPanel extends JPanel {


    int playerPanelHeight;
    int playerPanelWidth;

    private PlayerSidePanel player1Panel;
    private PlayerSidePanel player2Panel;
    private PlayerSidePanel player3Panel;
    private PlacedDominoPanel placedDominoPanel;

    private String[] playerNames;


    public MainContentPanel(int width, int height, String... playerNames) {
        setLayout(null);
        setPreferredSize(new Dimension(width, height));
        setSize(new Dimension(width, height));
        this.playerNames = playerNames;
        calculatePlayerPanelSize();
        putAllPanels();
    }

    private void calculatePlayerPanelSize() {
        playerPanelWidth = (int) getPreferredSize().getWidth() / 10;
        playerPanelHeight = (int) getPreferredSize().getHeight();
    }

    private void putAllPanels() {
        player1Panel = new PlayerSidePanel(playerNames[0], playerPanelWidth, playerPanelHeight);
        player2Panel = new PlayerSidePanel(playerNames[1], playerPanelHeight, playerPanelWidth);
        player3Panel = new PlayerSidePanel(playerNames[2], playerPanelWidth, playerPanelHeight);
        placedDominoPanel = new PlacedDominoPanel(getWidth() - (playerPanelWidth * 2)+20, getHeight() - playerPanelWidth+20);

        player2Panel.setBounds(0, (int) getPreferredSize().getHeight() - playerPanelWidth, player2Panel.getWidth(), player2Panel.getHeight());
        add(player2Panel);

        player1Panel.setBounds(0, 0, player1Panel.getWidth(), player1Panel.getHeight());
        add(player1Panel);

        player3Panel.setBounds((int) getPreferredSize().getWidth() - playerPanelWidth, 0, player3Panel.getWidth(), player3Panel.getHeight());
        add(player3Panel);

        JScrollPane scrollPane = new JScrollPane(placedDominoPanel);
        scrollPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_NEVER);
        scrollPane.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);

        scrollPane.setBounds(playerPanelWidth, 0, placedDominoPanel.getWidth(), placedDominoPanel.getHeight());
        add(scrollPane);

    }

    public void refresh(DominoFrameModel dominoFrameModel) {
        if(dominoFrameModel.getStepWinner() != null){
            player1Panel.displayScore(dominoFrameModel.getScores()[0]);
            player2Panel.displayScore(dominoFrameModel.getScores()[1]);
            player3Panel.displayScore(dominoFrameModel.getScores()[2]);
        }
        player1Panel.displayDominoes(dominoFrameModel.getPlayer1Dominoes(), dominoFrameModel.getMainPlayer().getName().equals(dominoFrameModel.getPlayer3Name()) ? Color.lightGray : Color.WHITE);
        player2Panel.displayDominoes(dominoFrameModel.getPlayer2Dominoes(), dominoFrameModel.getMainPlayer().getName().equals(dominoFrameModel.getPlayer1Name()) ? Color.lightGray : Color.WHITE);
        player3Panel.displayDominoes(dominoFrameModel.getPlayer3Dominoes(), dominoFrameModel.getMainPlayer().getName().equals(dominoFrameModel.getPlayer2Name()) ? Color.lightGray : Color.WHITE);
        if (dominoFrameModel.getPlacedDominoes().isEmpty()) {
            placedDominoPanel.clear();
        } else {
            placedDominoPanel.displayDominoes(dominoFrameModel.getLastDomino(), dominoFrameModel.getLastPlace());
        }
        this.revalidate();
        this.repaint();
    }

}
