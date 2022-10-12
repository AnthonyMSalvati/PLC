package Nodes;

import main.JottTree;
import main.Token;
import main.TokenType;
import java.util.ArrayList;

/**
 * @author Ben Froment
 */
public class DoubleNode implements JottTree {

    private final SignNode sign;
    private final double number;

    public DoubleNode(SignNode sign, double number) {

        this.sign = sign;
        this.number = number;
    }

    public static DoubleNode parseDoubleNode (ArrayList<Token> tokens) throws Exception {
        SignNode sign = SignNode.parseSignNode(tokens);

        if (sign != null) {
            if (tokens.get(0).getTokenType() != TokenType.NUMBER){
                throw new Exception("Error: <sign> not followed by <digit>");
            }
            if (isDouble(tokens.get(0).getToken())) {
                double number = Double.parseDouble(tokens.get(0).getToken());
                tokens.remove(0);
                return new DoubleNode(sign, number);
            }
            return null;
        }
        if (tokens.get(0).getTokenType() != TokenType.NUMBER){
            return null;
        }
        if (isDouble(tokens.get(0).getToken())) {
            double number = Double.parseDouble(tokens.get(0).getToken());
            tokens.remove(0);
            return new DoubleNode(null, number);
        }
        return null;

    }

    private static boolean isDouble (String s) {
        try {
            Integer.parseInt(s);
            return false;
        } catch (NumberFormatException ignored) {
        }
        try {
            Double.parseDouble(s);
        } catch (NumberFormatException e) {
            return false;
        }
        return true;
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
