package compilador.controller;

import java.awt.event.*;

import compilador.model.Parser;
import compilador.view.Screen;
import java.awt.Color;

public class CompilerController implements ActionListener {
    private Screen screen;
    private Parser parser;

    public CompilerController(Screen screen) {
        this.screen = screen;
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if(e.getSource() == screen.getCompileButton()) {
            parser = new Parser(screen.getCodigo());
            try {
                parser.p();
                Object[] identifiers = parser.getIdentifiers().toArray();
                Object[] reservedWords = parser.getReservedWords().toArray();
                Object[] operators = parser.getOperators().toArray();
                screen.updateAnalysisResults(identifiers, reservedWords, operators, "Analisis exitoso", Color.GREEN);
            } catch (Exception ex) {
                Object[] identifiers = parser.getIdentifiers().toArray();
                Object[] reservedWords = parser.getReservedWords().toArray();
                Object[] operators = parser.getOperators().toArray();
                screen.updateAnalysisResults(identifiers, reservedWords, operators, ex.getMessage(), Color.RED);
            }
        }   
    }
    
}
