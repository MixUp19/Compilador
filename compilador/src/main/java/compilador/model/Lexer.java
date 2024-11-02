package compilador.model;

import java.util.ArrayList;
import java.util.Collections;

public class Lexer {
    private int currentToken;
    private ArrayList<String> reservedWords;
    private ArrayList<String> operators;
    private String[] tokens;
    private boolean isIdentifier, isReservedWord, isOperator;

    public Lexer (String code) {
        this.reservedWords = new ArrayList<String>();
        this.operators = new ArrayList<String>();
        Collections.addAll(reservedWords,"while", 
        "do", "print","String", "int", ";", "<eof>");
        this.operators.add("=");
        this.operators.add("+");
        this.tokens = code.split("\\s+");
        currentToken = -1;
    }

    public String getToken(){
        currentToken++;
        boolean endOfCode = currentToken == tokens.length;
        if(endOfCode){
            isIdentifier = false;
            return null;
        }
        isReservedWord = reservedWords.contains(tokens[currentToken]);
        isOperator = operators.contains(tokens[currentToken]);
        isIdentifier = tokens[currentToken].matches("[a-z|A-Z][a-z|A-Z|0-9]*") && !isReservedWord;

        if(isIdentifier || isReservedWord || isOperator && !endOfCode) {
            return tokens[currentToken];
        }else{
            throw new RuntimeException("Error en el token: " + tokens[currentToken]);
        }
    }
    public String goBack(){
        currentToken--;
        isReservedWord = reservedWords.contains(tokens[currentToken]);
        isIdentifier = tokens[currentToken].matches("[a-z|A-Z][a-z|A-Z|0-9]*") && !isReservedWord;
        return tokens[currentToken];
    }
    public boolean isIdentifier(){
        return isIdentifier;
    }
    public boolean isReservedWord(){
        return isReservedWord;
    }
    public boolean isOperator(){
        return isOperator;
    }

    public String getNextToken(){
        int nextToken = currentToken+1;
        return tokens[nextToken];
    }

}
