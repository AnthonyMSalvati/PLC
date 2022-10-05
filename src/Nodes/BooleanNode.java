package Nodes;

import main.JottTree;
import main.Token;
import main.TokenType;

import java.util.ArrayList;

/**
 * @author Ben Froment
 */
public class BooleanNode implements JottTree {

    private final String value;

    public BooleanNode(String value) {

        this.value = value;
    }

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
