package Nodes;

import main.InvalidParseException;
import main.JottTree;
import main.Token;
import main.TokenType;
import java.util.ArrayList;
import main.SymbolTable;

/**
 * @author Ben Froment
 *
 * Node representing a double value
 */
public class DoubleNode implements JottTree {

    private final SignNode sign;
    private final double number;

    //  < sign > < digit >* . < digit > < digit > >*
    public DoubleNode(SignNode sign, double number) {

        this.sign = sign;
        this.number = number;
    }

    // Function called by its parent node to parse the list of tokens
    public static DoubleNode parseDoubleNode (ArrayList<Token> tokens) throws Exception {
        SignNode sign = SignNode.parseSignNode(tokens);

        if (sign != null) {
            Token token;
            token = tokens.get(0);
            if (token.getTokenType() != TokenType.NUMBER){
                throw new InvalidParseException("Error: <sign> not followed by <digit>", token.getFilename(),
                        token.getLineNum());
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
            return sign.convertToJott() + String.valueOf(number);
        }
        return String.valueOf(number);
    }

    @Override
    public String convertToJava() {
        if (this.sign != null) {
            return this.sign.convertToJava()
				+ String.valueOf(this.number);
        }
        return String.valueOf(this.number);
    }

    @Override
    public String convertToC() {
        if (this.sign != null) {
            return this.sign.convertToC()
				+ String.valueOf(this.number);
        }
        return String.valueOf(this.number);
    }

    @Override
    public String convertToPython(int nestLevel) { //Ian
        if (this.sign != null) {
            return this.sign.convertToPython(nestLevel)
				+ String.valueOf(this.number);
        }
        return String.valueOf(this.number);
    }

    @Override
    public boolean validateTree(SymbolTable symbolTable) throws Exception {
        if (sign != null) {
            return sign.validateTree(symbolTable);
        }
        return true;
    }
}
