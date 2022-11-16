package Nodes;

import main.JottTree;
import main.Token;
import main.TokenType;
import java.util.ArrayList;
import main.SymbolTable;

/**
 * @author  Ben Froment
 *
 * Node that represents a string value
 */
public class StringLiteralNode implements JottTree {

    private final String value;

    // " < str > "
    public StringLiteralNode(String value) {

        this.value = value;
    }

    // Function called by its parent node to parse the list of tokens
    public static StringLiteralNode parseStringLiteralNode (ArrayList<Token> tokens) {
        if (tokens.get(0).getTokenType() == TokenType.STRING) {
            String value = tokens.get(0).getToken();
            tokens.remove(0);
            return new StringLiteralNode(value);
        }
        return null;
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
		return "\"" + this.value + "\""; //assumed strings need quotes
    }

    @Override
    public boolean validateTree(SymbolTable symbolTable) throws Exception {
        return true;
    }
}
