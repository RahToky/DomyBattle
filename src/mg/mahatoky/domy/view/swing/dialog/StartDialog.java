package mg.mahatoky.domy.view.swing.dialog;

import javax.swing.*;
import java.awt.event.ActionListener;

/**
 * @author mtk_ext
 */
public class StartDialog extends JDialog {

    private JButton yesButton;
    private JButton noButton;

    private ActionListener yesListener;
    private ActionListener noListener;

    public StartDialog(JFrame parent) {
        super(parent, "Start Battle", true);

        JPanel panel = new JPanel();
        JLabel label = new JLabel("Do you want to start?");
        panel.add(label);

        yesButton = new JButton("Yes");
        noButton = new JButton("No");

        yesButton.addActionListener(e -> {
            if (yesListener != null)
                yesListener.actionPerformed(e);
            else
                dispose();
        });

        noButton.addActionListener(e -> {
            if (noListener != null)
                noListener.actionPerformed(e);
            else
                dispose();
        });

        panel.add(yesButton);
        panel.add(noButton);

        getContentPane().add(panel);
        pack();
        setLocationRelativeTo(parent);
    }

    public void setYesListener(ActionListener yesListener) {
        this.yesListener = yesListener;
    }

    public void setNoListener(ActionListener noListener) {
        this.noListener = noListener;
    }
}
