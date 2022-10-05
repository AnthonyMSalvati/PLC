package Nodes;

import main.JottTree;
import main.Token;
import main.TokenType;
import java.util.ArrayList;

/**
 * @author  Ben Froment
 */
public class StringLiteralNode implements JottTree {

    private final String value;

    public StringLiteralNode(String value) {

        this.value = value;
    }

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
    public String convertToPython() {
        return null;
    }

    @Override
    public boolean validateTree() {
        return false;
    }
}
