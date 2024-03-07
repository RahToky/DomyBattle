package mg.mahatoky.domy.view.swing.panel;

import mg.mahatoky.domy.model.Domino;
import mg.mahatoky.domy.model.PlayerResponse;
import mg.mahatoky.domy.view.swing.model.PlacedDominoPanelModel;

import javax.swing.*;
import java.awt.*;

/**
 * @author mtk_ext
 */
public class PlacedDominoPanel extends JPanel {
    private PlacedDominoPanelModel placedDominoPanelModel;

    public PlacedDominoPanel(int width, int height) {
        setLayout(null);
        setPreferredSize(new Dimension(width, height));
        setSize(new Dimension(width, height));
        setBackground(Color.black);
        initModels();
    }

    private void initModels() {
        this.placedDominoPanelModel = new PlacedDominoPanelModel(getWidth(), getHeight());
        placedDominoPanelModel.setDominoShortSide(getWidth() / 20);
        placedDominoPanelModel.setDominoLongSide(getWidth() / 10);
    }

    public void displayDominoes(Domino domino, PlayerResponse.PLACE place) {
        if (domino == null)
            return;
        PlacedDominoPanelModel.DominoPosition position = placedDominoPanelModel.addDomino(domino, place);
        if(position != null) {
            DominoPanel dominoPanel = new DominoPanel(domino, position.isVertical() ? placedDominoPanelModel.getDominoShortSide() : placedDominoPanelModel.getDominoLongSide(), position.isVertical() ? placedDominoPanelModel.getDominoLongSide() : placedDominoPanelModel.getDominoShortSide());
            dominoPanel.setBounds(position.getPoint().x, position.getPoint().y, dominoPanel.getWidth(), dominoPanel.getHeight());
            add(dominoPanel);
            revalidate();
            repaint();
        }
    }

    public void clear() {
        this.placedDominoPanelModel.clear();
        this.removeAll();
        this.revalidate();
    }


    public boolean isHorizontal(Point firstPoint, Point secondPoint) {
        return firstPoint.x == secondPoint.x;
    }

}
