package compilador.model;

import java.util.HashSet;

public class Parser {
    Lexer lexer;
    String currentToken;
    HashSet<String> reservedWords;
    HashSet<String> operators;
    HashSet<String> identifiers;
    boolean isIdentifier;

    public Parser(String code) {
        lexer = new Lexer(code);
        currentToken = lexer.getToken();
        isIdentifier = currentToken.matches("[a-z|A-Z][a-z|A-Z|0-9]*");
        reservedWords = new HashSet<String>();
        operators = new HashSet<String>();
        identifiers = new HashSet<String>();
        if (isIdentifier) {
            identifiers.add(currentToken);
        }else  {
            reservedWords.add(currentToken);
        }
    }

    public void p() throws Exception {
        d();
        s();
        match("<eof>");
        if (currentToken != null) {
            throw new RuntimeException("No se pueden definir más estatutos después de <eof>");
        }
    }

    private void d() throws Exception {
        if (isIdentifier) {
            try {
                match(currentToken);
                if (currentToken.equals("=")) {
                    currentToken = lexer.goBack();
                    isIdentifier = lexer.isIdentifier();
                    return;
                }
                match("int");
                match(";");
                d();
            } catch (Exception e) {
                    match("String");
                    match(";");
                    d();
                } 
            }
         else {
            return;
        }
    }

    private void s() throws Exception {
        if (currentToken.equals("while")) {
            match("while");
            e();
            match("do");
            s();
        } else if (currentToken.equals("print")) {
            match("print");
            e();
        } else if (isIdentifier) {
            match(currentToken);
            match("=");
            e();
        } else {
            throw new RuntimeException("Error de sintaxis" + currentToken);
        }
    }

    private void e() throws Exception {
        if (isIdentifier) {
            match(currentToken);
            if (currentToken.equals("+")) {
                match("+");
                if (!isIdentifier) {
                    throw new RuntimeException("Se espera un identificadro después de token +" + currentToken);
                }
                match(currentToken); 
            }
        }
    }

    private void match(String expectedToken) throws Exception {
        if(currentToken == null) {
            throw new RuntimeException("Fin de archivo inesperado");
        }
        if (currentToken.equals(expectedToken)) {
            currentToken = lexer.getToken();
            isIdentifier = lexer.isIdentifier();
            boolean isReservedWord = lexer.isReservedWord();
            if (isIdentifier) {
                identifiers.add(currentToken);
            }else  if(isReservedWord){
                reservedWords.add(currentToken);
            }else{
                operators.add(currentToken);
            }
        } else {
            throw new RuntimeException("Error de sintaxis en el token: " + currentToken);
        }
    }
    public HashSet<String> getIdentifiers() {
        return identifiers;
    }
    public HashSet<String> getReservedWords() {
        return reservedWords;
    }
    public HashSet<String> getOperators() {
        return operators;
    }

}
