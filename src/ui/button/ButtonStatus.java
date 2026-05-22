package ui.button;

import javax.swing.*;
import java.awt.event.ActionListener;

public class ButtonStatus extends JButton {

    public ButtonStatus(final ActionListener actionListener) {
        this.setText("Jogo Status");
        this.addActionListener(actionListener);
    }
}
