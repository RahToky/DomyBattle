package mg.mahatoky.domy.view.swing.dialog;

import javax.swing.*;

/**
 * @author mtk_ext
 */
public class WinnerDialog extends JDialog {

    private JButton yesButton;
    private JButton noButton;

    public WinnerDialog(JFrame parent, String title, String message) {
        super(parent, title, true); // true to make the dialog modal

        JPanel panel = new JPanel();
        JLabel label = new JLabel(message);
        panel.add(label);

        yesButton = new JButton("Yes");
        noButton = new JButton("No");

        yesButton.addActionListener(e -> {
            // Handle Yes button action here
            dispose(); // Close the dialog
        });

        noButton.addActionListener(e -> {
            // Handle No button action here
            dispose(); // Close the dialog
        });

        panel.add(yesButton);
        panel.add(noButton);

        getContentPane().add(panel);
        pack(); // Size the dialog to fit its contents
        setLocationRelativeTo(parent);
    }

}
