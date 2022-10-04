package Nodes;

import main.JottTree;
import main.Token;
import main.TokenType;

import java.util.ArrayList;

public class IdNode implements JottTree {

    private final String value;

    public IdNode(String value) {

        this.value = value;
    }

    public static IdNode parseIdNode (ArrayList<Token> tokens) throws Exception{
        if (tokens.get(0).getTokenType() == TokenType.ID_KEYWORD) {
            String value = tokens.get(0).getToken();

            tokens.remove(0);
            return new IdNode(value);
        } else {
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
    public String convertToPython() {
        return null;
    }

    @Override
    public boolean validateTree() {
        return false;
    }
}
