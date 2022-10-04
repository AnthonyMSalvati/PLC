package Nodes;

import main.JottTree;
import main.Token;
import main.TokenType;

import java.util.ArrayList;

public class IntegerNode implements JottTree {

    private final SignNode sign;
    private final int number;

    public IntegerNode(SignNode sign, int number) {

        this.sign = sign;
        this.number = number;
    }

    public static IntegerNode parseIntegerNode (ArrayList<Token> tokens) throws Exception {
        SignNode sign = SignNode.parseSignNode(tokens);

        if (sign != null) {
            if (tokens.get(0).getTokenType() != TokenType.NUMBER){
                throw new Exception("Error: <sign> not followed by <digit>");
            }
            int number = Integer.parseInt(tokens.get(0).getToken());

            tokens.remove(0);
            return new IntegerNode(sign, number);
        }
        if (tokens.get(0).getTokenType() != TokenType.NUMBER){
            return null;
        }
        int number = Integer.parseInt(tokens.get(0).getToken());

        tokens.remove(0);
        return new IntegerNode(null, number);
    }

    @Override
    public String convertToJott() {
        if (sign != null) {
            return sign.convertToJott() + number;
        }
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
