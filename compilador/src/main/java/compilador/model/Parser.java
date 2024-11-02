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
        } else {
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
            match(currentToken);
            if (currentToken == null) {
                throw new RuntimeException("Fin de archivo inesperado");
            }
            if (currentToken.equals("=")) {
                currentToken = lexer.goBack();
                isIdentifier = lexer.isIdentifier();
                return;
            }
            try {
                match("int");
            } catch (Exception e) {
                match("String");
            }
            try {
                match(";");
            } catch (Exception e) {
                throw new RuntimeException("Se esperaba ; después de la declaración de la variable " + e.getMessage());
            }
            d();
        } else {
            return;
        }
    }

    private void s() throws Exception {
        if (currentToken.equals("while")) {
            match("while");
            e();
            try {
                match("do");
            } catch (Exception e) {
                throw new RuntimeException("Se esperaba la palabra clave do después de while " + e.getMessage());
            }
            s();
        } else if (currentToken.equals("print")) {
            match("print");
            e();
        } else if (isIdentifier) {
            match(currentToken);
            try {
                match("=");
            } catch (Exception e) {
                throw new RuntimeException("Se esperaba el operador = después del identificador " + e.getMessage());
            }
            e();
        } else {
            throw new RuntimeException("Se esperaba una palabra clave como while o print " + currentToken);
        }
    }

    private void e() throws Exception {
        if (isIdentifier) {
            try {
                match(currentToken);
            } catch (Exception e) {
                throw new RuntimeException("Se esperaba un identificador" + e.getMessage());
            }
            try {
                match("+");
            } catch (Exception e) {
                return;
            }
            if (!isIdentifier) {
                throw new RuntimeException("Se espera un identificador después de token + " + currentToken);
            }
            match(currentToken);
        } else {
            throw new RuntimeException("Se esperaba un identificador " + currentToken);
        }
    }

    private void match(String expectedToken) throws Exception {
        if (currentToken == null) {
            throw new RuntimeException("Fin de archivo inesperado");
        }
        if (currentToken.equals(expectedToken)) {
            currentToken = lexer.getToken();
            isIdentifier = lexer.isIdentifier();
            boolean isReservedWord = lexer.isReservedWord();
            if (isIdentifier) {
                identifiers.add(currentToken);
            } else if (isReservedWord) {
                reservedWords.add(currentToken);
            } else {
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
