package mg.mahatoky.domy.view.swing.panel;

import mg.mahatoky.domy.model.Domino;

import javax.swing.*;
import java.awt.*;

/**
 * @author mtk_ext
 */
public class DominoPanel extends JPanel {

    private Domino domino;
    private HalfDominoPanel[] twoHalfDominoPanels;

    public DominoPanel(Domino domino, int width, int height) {
        this.domino = domino;
        this.setSize(new Dimension(width, height));
        this.setPreferredSize(new Dimension(width, height));
        setLayout(null);
        setHalfDominoPanels();
        setBackground(Color.GRAY);
    }

    private void setHalfDominoPanels() {
        twoHalfDominoPanels = new HalfDominoPanel[2];
        int minSideSize = Math.min(getWidth(), getHeight());
        boolean isVert = getWidth() < getHeight();
        twoHalfDominoPanels[0] = new HalfDominoPanel(minSideSize, domino.getFace1());
        twoHalfDominoPanels[1] = new HalfDominoPanel(minSideSize, domino.getFace2());

        twoHalfDominoPanels[0].setBounds(1, 1, minSideSize - 2, minSideSize - 2); // -2 to make border like style
        twoHalfDominoPanels[1].setBounds(isVert ? 1 : minSideSize+1, isVert ? minSideSize+1 : 1, minSideSize - 2, minSideSize - 2);// -2 to make border like style

        // SEPARATOR
        JPanel separatorPanel = new JPanel();
        separatorPanel.setBackground(Color.white);
        separatorPanel.setBounds(isVert ? 1 : minSideSize-1, isVert ? minSideSize-1 : 1, isVert?minSideSize-2:2, isVert?2:minSideSize-2);
        add(separatorPanel);

        add(twoHalfDominoPanels[0]);
        add(twoHalfDominoPanels[1]);
    }

    public static class HalfDominoPanel extends JPanel {

        private int eyes;

        public HalfDominoPanel(int size, int eyes) {
            setSize(new Dimension(size, size));
            setPreferredSize(new Dimension(size, size));
            this.eyes = eyes;
        }

        @Override
        protected void paintComponent(Graphics g) {
            super.paintComponent(g);
            int diameter = getWidth() / 7;
            g.setColor(Color.BLUE);

            if (eyes == 0)
                return;

            if (eyes == 1)
                g.fillRect(diameter * 3, diameter * 3, diameter, diameter);
            else if (eyes > 1) {
                g.fillRect(diameter, diameter, diameter, diameter);
                g.fillRect(diameter * 5, diameter * 5, diameter, diameter);
                if (eyes == 3 || eyes == 5) {
                    g.fillRect(diameter * 3, diameter * 3, diameter, diameter);
                }
                if (eyes > 3) {
                    g.fillRect(diameter * 5, diameter, diameter, diameter);
                    g.fillRect(diameter, diameter * 5, diameter, diameter);
                }
                if (eyes == 6) {
                    g.fillRect(diameter, diameter * 3, diameter, diameter);
                    g.fillRect(diameter * 5, diameter * 3, diameter, diameter);
                }
            }
        }
    }

}
