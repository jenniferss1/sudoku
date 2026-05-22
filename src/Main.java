import ui.frame.MainFrame;
import ui.panel.MainPanel;

import javax.swing.*;
import java.awt.*;

void main() {

    var dimension = new Dimension(500, 500);
    JPanel mainPanel = new MainPanel(dimension);
    JFrame mainFrame = new MainFrame(dimension, mainPanel);
    mainFrame.revalidate();
    mainFrame.repaint();


}
