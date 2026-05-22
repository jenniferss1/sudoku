package ui.screen;

import model.Space;
import service.BoardService;
import service.NotifierService;
import ui.button.ButtonStatus;
import ui.button.FinishedButton;
import ui.button.ResetButton;
import ui.frame.MainFrame;
import ui.input.NumberText;
import ui.panel.MainPanel;
import ui.panel.SudokuSector;

import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import static javax.swing.JOptionPane.*;
import static service.EventEnum.CLEAR_SPACE;

public class MainScreen {

    private final static Dimension dimension = new Dimension(600, 640);

    private final BoardService boardService;
    private final NotifierService  notifierService;

    private JButton finishButton;
    private JButton statusButton;
    private JButton resetButton;

    public MainScreen(final Map<String, String> gameConfig) {
        this.boardService = new BoardService(gameConfig);
        this.notifierService = new NotifierService();
    }

    public void buildMainScreen() {
        JPanel mainPanel = new MainPanel(dimension);
        JFrame mainFrame = new MainFrame(dimension, mainPanel);

        for (int r = 0; r < 9; r += 3){
            var endRow = r + 2;
            for (int c = 0; c < 9; c += 3){
                var endCol = c + 2;
                var spaces = getSectorSpace(boardService.getSpaces(), c, endCol, r, endRow);
                mainPanel.add(generateSection(spaces));
            }
        }

        addFinishButton(mainPanel);
        addStatusButton(mainPanel);
        addResetButton(mainPanel);

        mainFrame.revalidate();
        mainFrame.repaint();
    }

    private List<Space> getSectorSpace(
            final List<List<Space>> spaces,  // Changed to 2D list
            final int initCol, final int endCol,
            final int initRow, final int endRow) {
        List<Space> sectorSpaces = new ArrayList<>();
        for (int r = initRow; r <= endRow; r++) {
            for (int c = initCol; c <= endCol; c++) {
                sectorSpaces.add(spaces.get(r).get(c));  // row first, then column
            }
        }
        return sectorSpaces;
    }

    private JPanel generateSection(final List<Space> spaces){
        List<NumberText> fields = new ArrayList<>(spaces.stream().map(NumberText::new).toList());
        fields.forEach(t -> notifierService.subscribe(CLEAR_SPACE, t));
        return new SudokuSector(fields);
    }

    private void addResetButton(final JPanel mainPanel) {
        resetButton = new ResetButton(e -> {
           var dialogResult = showConfirmDialog(
                   null,
                   "Deseja reiniciar o jogo?",
                   "limpar Jogo",
                    YES_NO_OPTION,
                    QUESTION_MESSAGE
           );
           if (dialogResult == 0){
               boardService.reset();
               notifierService.notify(CLEAR_SPACE);
           }
        });
        mainPanel.add(resetButton);
    }

    private void addStatusButton(final JPanel mainPanel) {
        statusButton = new ButtonStatus(e -> {
            var hasErros = boardService.hasErrors();
            var gameStatus = boardService.getStatus();
            var message = switch (gameStatus){
                case NOT_STARTED -> "Jogo não iniciado";
                case INCOMPLETE -> "Jogo incompleto";
                case COMPLETE -> "Jogo finalizado";
            };
            message += hasErros ? " e contem erros" : " e não contem erros";
            showMessageDialog(null, message);
        });
        mainPanel.add(statusButton);
    }

    private void addFinishButton(final JPanel mainPanel) {
        finishButton = new FinishedButton(e -> {
            if (boardService.gameIsFinished()){
                showMessageDialog(null, "Jogo Concluído");
                resetButton.setEnabled(false);
                statusButton.setEnabled(false);
                finishButton.setEnabled(true);
            } else {
                showMessageDialog(null, "Jogo com erros, ajuste-os e tente novamente");
            }
        });
        mainPanel.add(finishButton);
    }
}
