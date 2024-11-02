package compilador;

import compilador.controller.CompilerController;
import compilador.view.Screen;

public class Main {
    public static void main(String[] args) {
        Screen screen = new Screen();
        CompilerController controller = new CompilerController(screen);
        screen.setController(controller);
    }
}