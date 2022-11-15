package Nodes;

import main.JottTree;
import main.Token;
import main.TokenType;
import main.SymbolTable;

import java.util.ArrayList;

public class DigitNode implements JottTree {

    private final int number;

    public DigitNode(int number) {
        this.number = number;
    }

    public static DigitNode parseDigitNode (ArrayList<Token> tokens) {
        if (tokens.get(0).getTokenType() != TokenType.NUMBER){
            return null;
        }
        else {
            try {
                int number = Integer.parseInt(tokens.get(0).getToken());
                tokens.remove(0);
                return new DigitNode(number);
            } catch (NumberFormatException ignored) {
                return null;
            }
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
    public String convertToPython() { //Ian
        return String.valueOf(this.number);
    }

    @Override
    public boolean validateTree(SymbolTable symbolTable) throws Exception {return true;}
}
