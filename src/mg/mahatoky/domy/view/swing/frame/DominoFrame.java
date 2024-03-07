package mg.mahatoky.domy.view.swing.frame;

import mg.mahatoky.domy.bot.DefaultBot;
import mg.mahatoky.domy.bot.MtkBot;
import mg.mahatoky.domy.model.Game;
import mg.mahatoky.domy.utils.GameManager;
import mg.mahatoky.domy.utils.Shuffler;
import mg.mahatoky.domy.view.swing.dialog.StartDialog;
import mg.mahatoky.domy.view.swing.panel.MainContentPanel;
import mg.mahatoky.domy.view.swing.model.DominoFrameModel;

import javax.swing.*;

/**
 * @author mtk_ext
 */
public class DominoFrame extends JFrame {

    private MainContentPanel mainContentPanel;
    private StartDialog startDialog;
    private Game game;
    private GameManager gameManager;

    public DominoFrame(int size, String... playerNames) throws Exception {
        try {
            if (playerNames == null || playerNames.length < 3)
                throw new Exception("Must give 3 players names");
            setDefaultCloseOperation(EXIT_ON_CLOSE);
            setResizable(false);
            setLocation(300, 100);
            mainContentPanel = new MainContentPanel(size, size, playerNames);
            setContentPane(mainContentPanel);
            setContents();
            this.setVisible(true);
            this.pack();
            buildGame(playerNames);
            displayStartDialog();
        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, e.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void buildGame(String... playerNames) {
        game = new Game.GameBuilder()
                .setMaxScore(60)
                .setPlayer1(new DefaultBot(playerNames[0]))
                .setPlayer2(new MtkBot(playerNames[1]))
                .setPlayer3(new DefaultBot(playerNames[2]))
                .setRemainingDominoes(Shuffler.generateDominoes())
                .build();
    }

    private void startGame() {
        try {
            gameManager = new GameManager(game);
            gameManager.start(this::refresh);
        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, e.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void setContents() {
        startDialog = new StartDialog(this);
    }

    private void displayStartDialog() {
        startDialog.setYesListener(e -> {
            startDialog.dispose();
            startGame();
        });
        startDialog.setVisible(true);
    }

    public void refresh(DominoFrameModel dominoFrameModel) {
        System.out.println("refresh");
        this.mainContentPanel.refresh(dominoFrameModel);
    }

}
