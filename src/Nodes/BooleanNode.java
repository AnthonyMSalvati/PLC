package Nodes;

import main.JottTree;
import main.Token;
import main.TokenType;
import main.SymbolTable;

import java.util.ArrayList;

/**
 * @author Ben Froment
 *
 * Node represeting the boolean values true and false
 */
public class BooleanNode implements JottTree {

    private final String value;

    // True | False
    public BooleanNode(String value) {

        this.value = value;
    }

    // Function called by its parent node to parse the list of tokens
    public static BooleanNode parseBooleanNode (ArrayList<Token> tokens) {
        if (tokens.get(0).getTokenType() == TokenType.ID_KEYWORD) {
            String value = tokens.get(0).getToken();
            if (value.equals("True") || value.equals("False")) {
                tokens.remove(0);
                return new BooleanNode(value);
            }
        }
        return null;
    }

    @Override
    public String convertToJott() {
        return value;
    }

    @Override
    public String convertToJava() {
        if (this.value.equals("True")) {
            return "true";
        } else {
            return "false";
        }
    }

    @Override
    public String convertToC() {
        if (this.value.equals("True")) {
            return "true";
        } else {
            return "false";
        }
    }

    @Override
    public String convertToPython(int nestLevel) { //Ian
        return this.value; //python uses "True" and "False" same as Jott
    }

    @Override
    public boolean validateTree(SymbolTable symbolTable) throws Exception {
        return true;
    }
}
