package Nodes;

import main.JottTree;
import main.Token;
import main.TokenType;

import java.util.ArrayList;

/**
 * @author Ben Froment
 *
 * Node representing an integer value
 */
public class IntegerNode implements JottTree {

    private final SignNode sign;
    private final DigitNode number;

    // < sign > < digit > < digit > >*
    public IntegerNode(SignNode sign, DigitNode number) {
        this.sign = sign;
        this.number = number;
    }

    // Function called by its parent node to parse the list of tokens
    public static IntegerNode parseIntegerNode (ArrayList<Token> tokens) throws Exception {
        SignNode sign = SignNode.parseSignNode(tokens);

        if (sign != null) {
            if (tokens.get(0).getTokenType() != TokenType.NUMBER){
                throw new Exception("Error: <sign> not followed by <digit>");
            }
            DigitNode number = DigitNode.parseDigitNode(tokens);
            if (number != null) {
                return new IntegerNode(sign, number);
            }
            throw new Exception("Error: <sign> not followed by <digit>");
        }
        if (tokens.get(0).getTokenType() != TokenType.NUMBER) {
            return null;
        }
        DigitNode number = DigitNode.parseDigitNode(tokens);
        if (number != null) {
            return new IntegerNode(null, number);
        }
        return null;
    }

    @Override
    public String convertToJott() {
        if (sign != null) {
            return sign.convertToJott() + number.convertToJott();
        }
        return number.convertToJott();
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
