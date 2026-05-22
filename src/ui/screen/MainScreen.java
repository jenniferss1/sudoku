package ui.screen;

import model.Space;
import service.BoardService;
import ui.frame.MainFrame;
import ui.input.NumberText;
import ui.panel.MainPanel;

import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;
import java.util.Map;

public class MainScreen {

    private final static Dimension dimension = new Dimension(600, 600);

    private final BoardService boardService;

    private JButton finishButton;
    private JButton statusButton;
    private JButton resetButton;

    public MainScreen(final Map<String, String> gameConfig) {
        this.boardService = new BoardService(gameConfig);
    }

    public void buildMainScreen() {
        JPanel mainPanel = new MainPanel(dimension);
        JFrame mainFrame = new MainFrame(dimension, mainPanel);

        addFinishButton(mainPanel);
        addStatusButton(mainPanel);
        addResetButton(mainPanel);

        mainFrame.revalidate();
        mainFrame.repaint();
    }


    private void addResetButton(final JPanel mainPanel) {
        mainPanel.add(resetButton);
    }

    private void addStatusButton(final JPanel mainPanel) {
        mainPanel.add(statusButton);
    }

    private void addFinishButton(final JPanel mainPanel) {
        mainPanel.add(finishButton);
    }
}
