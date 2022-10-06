package Nodes;

import main.JottTree;
import main.Token;
import main.TokenType;

import java.util.ArrayList;

public class DigitNode implements JottTree {

    private final int number;

    public DigitNode(int number) {
        this.number = number;
    }

    public static DigitNode parseDigitNode (ArrayList<Token> tokens) throws Exception {
        if (tokens.get(0).getTokenType() != TokenType.NUMBER){
            return null;
        }
        else {
            int number = Integer.parseInt(tokens.get(0).getToken());
            tokens.remove(0);
            return new DigitNode(number); 
        }
    }

    @Override
    public String convertToJott() {
        return String.valueOf(number);
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
