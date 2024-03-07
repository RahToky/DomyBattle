package mg.mahatoky.domy;

import mg.mahatoky.domy.view.swing.frame.DominoFrame;

/**
 * @author mtk_ext
 */
public class Main {
    public static void main(String[] args) {
        try {
            String[] playerNames = {"Bot1","MtkBot","Bot2"};
            new DominoFrame(800,playerNames);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
