package ui.screen;

import service.BoardService;
import ui.button.FinishedButton;
import ui.button.ResetButton;
import ui.frame.MainFrame;
import ui.panel.MainPanel;

import javax.swing.*;
import java.awt.*;
import java.util.Map;

import static javax.swing.JOptionPane.QUESTION_MESSAGE;
import static javax.swing.JOptionPane.YES_NO_OPTION;

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
        resetButton = new ResetButton(e -> {
           var dialogResult = JOptionPane.showConfirmDialog(
                   null,
                   "Deseja reiniciar o jogo?",
                   "limpar Jogo",
                    YES_NO_OPTION,
                    QUESTION_MESSAGE
           );
           if (dialogResult == 0){
               boardService.reset();
           }
        });
        mainPanel.add(resetButton);
    }

    private void addStatusButton(final JPanel mainPanel) {
        statusButton = new FinishedButton(e -> {
            var hasErros = boardService.hasErrors();
            var gameStatus = boardService.getStatus();
            var message = switch (gameStatus){
                case NOT_STARTED -> "Jogo não iniciado";
                case INCOMPLETE -> "Jogo incompleto";
                case COMPLETE -> "Jogo finalizado";
            };
            message += hasErros ? " e contem erros" : " e não contem erros";
            JOptionPane.showMessageDialog(null, message);
        });
        mainPanel.add(statusButton);
    }

    private void addFinishButton(final JPanel mainPanel) {
        finishButton = new FinishedButton(e -> {
            if (boardService.gameIsFinished()){
                JOptionPane.showMessageDialog(null, "Jogo Concluído");
                resetButton.setEnabled(false);
                statusButton.setEnabled(false);
                finishButton.setEnabled(true);
            } else {
                JOptionPane.showMessageDialog(null, "Jogo com erros, ajuste-os e tente novamente");
            }
        });
        mainPanel.add(finishButton);
    }
}
