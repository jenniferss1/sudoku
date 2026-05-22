import ui.screen.MainScreen;

import javax.swing.*;
import java.awt.*;

import static java.util.stream.Collectors.toMap;

void main(String[] args) {

    final var gameConfig = Stream.of(args)
            .collect(toMap(k -> k.split(";")[0], v -> v.split(";")[1]));
    var mainScreen = new MainScreen(gameConfig);
    mainScreen.buildMainScreen();


}
