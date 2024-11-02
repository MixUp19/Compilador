package compilador.view;

import javax.swing.*;

import compilador.controller.CompilerController;

import java.awt.*;

public class Screen extends JFrame {
    private JTextArea codeTextArea;
    private JButton compileButton;
    private JTable identifiersTable;
    private JLabel analysisMessage;

    public Screen() {
        super("Compilador");
        crearPantalla();
    }

    private void crearPantalla() {
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setSize(800, 600);
        this.setLayout(new BorderLayout());

        JPanel codeAndAnalysis = new JPanel();
        codeAndAnalysis.setLayout(new GridLayout(1, 2));
        codeTextArea = new JTextArea();
        codeAndAnalysis.add(codeTextArea);
        JPanel analysisPanel = new JPanel();
        analysisPanel.setLayout(new GridLayout(2,1));

         // Create table for identifiers
         String[] columnNames = {"Identificadores", "Palabras reservadas", "Operadores"};
         Object[][] data = {}; // Initially empty
         identifiersTable = new JTable(data, columnNames);
         JScrollPane scrollPane = new JScrollPane(identifiersTable);
         analysisPanel.add(scrollPane);
         // Create label for analysis message
         analysisMessage = new JLabel("Analysis result will be displayed here.");
         analysisPanel.add(analysisMessage);
         codeAndAnalysis.add(analysisPanel);
         this.add(codeAndAnalysis, BorderLayout.CENTER);
 

        compileButton = new JButton("Compilar");
        this.add(compileButton, BorderLayout.SOUTH);

       
        this.setVisible(true);
    }

    public String getCodigo() {
        return codeTextArea.getText();
    }

    public JButton getCompileButton() {
        return compileButton;
    }

    public void updateAnalysisResults(Object[] identifiersData, Object[] reservedWordsData, Object[] operators, String message, Color color) {
        // Update table data
        int length = Math.max(identifiersData.length, reservedWordsData.length);
        length = Math.max(length, operators.length);
        Object[][] table = new Object[length][3];
        for (int i = 0; i < length; i++) {
            table[i][0] = (identifiersData.length <= i) ? "" : identifiersData[i];
            table[i][1] = (reservedWordsData.length <= i) ? "" : reservedWordsData[i];
            table[i][2] = (operators.length <= i) ? "" : operators[i];
        }
        String[] columnNames = {"Identificadores", "Palabras reservadas", "Operadores"};
        identifiersTable.setModel(new javax.swing.table.DefaultTableModel(table, columnNames));

        // Update analysis message
        analysisMessage.setText(message);
        analysisMessage.setForeground(color);
    }
    public void setController(CompilerController controller) {
        compileButton.addActionListener(controller);
    }
}
