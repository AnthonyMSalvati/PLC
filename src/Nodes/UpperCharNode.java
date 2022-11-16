package Nodes;

import main.JottTree;
import main.Token;
import main.TokenType;
import main.SymbolTable;

import java.util.ArrayList;

public class UpperCharNode implements JottTree {

    private final String character;

    public UpperCharNode(String c) {
        this.character = c;
    }

    public static UpperCharNode parseUpperCharNode (ArrayList<Token> tokens) throws Exception {
        if (tokens.get(0).getTokenType() != TokenType.ID_KEYWORD){
            return null;
        }
        else {
            String value = tokens.get(0).getToken();
            if(value.length() == 1) {
                char c = value.charAt(0);
                if (Character.isUpperCase(c)) {
                    tokens.remove(0);
                    return new UpperCharNode(value);
                }
            }
        }
        return null;
    }

    @Override
    public String convertToJott() {
        return character;
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
    public String convertToPython(int nestLevel) { //Ian
        return this.character;
    }

    @Override
    public boolean validateTree(SymbolTable symbolTable) throws Exception {
        return true;
    }
}
