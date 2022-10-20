package Nodes;

import main.JottTree;
import main.Token;
import main.TokenType;

import java.util.ArrayList;

public class TypeNode implements JottTree {

    private final String type;

    public TypeNode(String type) {
        this.type = type;
    }

    public static TypeNode parseTypeNode(ArrayList<Token> tokens) throws Exception {
        if (tokens.get(0).getTokenType() == TokenType.ID_KEYWORD) {
            String value = tokens.get(0).getToken();
            if (value.equals("Integer") || value.equals("Double")
            || value.equals("String") || value.equals("Boolean")) {
                tokens.remove(0);
                return new TypeNode(value);
            }
        }
        return null;
    }    

    @Override
    public String convertToJott() {
        return type;
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
