package ui.button;

import javax.swing.*;
import java.awt.event.ActionListener;

public class FinishedButton extends JButton {
    public FinishedButton(final ActionListener actionListener) {
        this.setText("Concluir Jogo");
        this.addActionListener(actionListener);
    }
}
