package Nodes;

import main.JottTree;
import main.Token;

import java.util.ArrayList;

public class SignNode implements JottTree {

    private final String value;

    public SignNode (String value) {
        this.value = value;
    }

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
    public String convertToPython() {
        return null;
    }

    @Override
    public boolean validateTree() {
        return false;
    }
}
