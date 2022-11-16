package Nodes;

import main.JottTree;
import main.Token;
import main.SymbolTable;

import java.util.ArrayList;

/**
 * @author Ben Froment
 *
 * Node that represents the sign + or -
 */
public class SignNode implements JottTree {

    private final String value;

    // + | -
    public SignNode (String value) {
        this.value = value;
    }

    // Function called by its parent node to parse the list of tokens
    public static SignNode parseSignNode (ArrayList<Token> tokens) {
        switch (tokens.get(0).getToken()) {
            case "-":
                tokens.remove(0);
                return new SignNode("-");
            case "+" :
                tokens.remove(0);
                return new SignNode("+");
            default:
                return null;
        }
    }

    @Override
    public String convertToJott() {
        return value;
    }

    @Override
    public String convertToJava() {
        return null;
    }

    @Override
    public String convertToC() {
        return null;
    }

    @Override
    public String convertToPython(int nestLevel) { //Ian
        return this.value;
    }

    @Override
    public boolean validateTree(SymbolTable symbolTable) throws Exception {
        return true;
    }
}
