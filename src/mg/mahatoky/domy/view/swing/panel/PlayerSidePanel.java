package mg.mahatoky.domy.view.swing.panel;

import mg.mahatoky.domy.model.Domino;
import mg.mahatoky.domy.model.Dominoes;

import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;
import java.util.Collections;

/**
 * @author mtk_ext
 */
public class PlayerSidePanel extends JPanel {

    private java.util.List<Domino> dominoes;
    private String playerName;
    private JPanel dominoesPanel = new JPanel();
    private JPanel profilePanel;
    private JPanel scorePanel;
    private JLabel scoreLabel = new JLabel();

    public PlayerSidePanel(String playerName, int width, int height) {
        setLayout(null);
        setSize(new Dimension(width, height));
        setPreferredSize(new Dimension(width, height));
        this.playerName = playerName;
        addComponents();
    }

    public void addComponents() {
        addProfilePanel();
        displayName();
        displayScore(0);
        addDominoesPanel();
    }

    private void addProfilePanel(){
        int minWidth = Math.min(getWidth(), getHeight());
        profilePanel = new JPanel();
        profilePanel.setBounds(0, 0, minWidth, minWidth);
        profilePanel.setLayout(null);
        add(profilePanel);

        scorePanel = new JPanel();
        scorePanel.setBackground(Color.ORANGE);
        scorePanel.setBounds(5,profilePanel.getWidth()/2,profilePanel.getWidth()-10,profilePanel.getWidth()/3);
        scorePanel.add(scoreLabel);
        profilePanel.add(scorePanel);
    }

    private void displayName() {
        JPanel panelName = new JPanel();
        panelName.add(new JLabel(playerName));
        panelName.setBounds(5,5,profilePanel.getWidth()-10,profilePanel.getWidth()/2);
        profilePanel.add(panelName);
    }

    public void displayScore(int score) {
        scoreLabel.setText("Pt: "+score);
        profilePanel.repaint();
    }

    private void addDominoesPanel() {
        if (getWidth() < getHeight()) {
            dominoesPanel.setBounds(0, getWidth(), getWidth(), getHeight() - getWidth());
        } else {
            dominoesPanel.setBounds(getHeight(), 0, getWidth() - getHeight(), getWidth());
        }
        dominoesPanel.setBackground(Color.WHITE);
        dominoesPanel.setLayout(null);
        add(dominoesPanel);
    }

    public void displayDominoes(Dominoes dominoes, Color backColor) {
        try {
            this.dominoes = new ArrayList<>(dominoes);
            if (backColor != null) {
                profilePanel.setBackground(backColor);
            }
            Collections.sort(this.dominoes);
            dominoesPanel.removeAll();

            int dominoWidth = isVertical() ? getWidth() : getHeight() / 2;
            int dominoHeight = isVertical() ? getWidth() / 2 : getHeight();

            int lastX = isVertical() ? 0 : getHeight() + 50;
            int lastY = isVertical() ? getWidth() + 50 : 0;
            for (Domino domino : dominoes) {
                DominoPanel dominoPanel = new DominoPanel(domino, dominoWidth, dominoHeight);
                dominoPanel.setBounds(lastX, lastY, dominoWidth, dominoHeight);
                dominoesPanel.add(dominoPanel);
                if (isVertical()) {
                    lastY += dominoPanel.getHeight() + 10;
                } else {
                    lastX += dominoPanel.getWidth() + 10;
                }
            }
            this.repaint();
        }catch (Exception e){
            e.printStackTrace();
        }
    }

    public boolean isVertical() {
        return getWidth() < getHeight();
    }

}
