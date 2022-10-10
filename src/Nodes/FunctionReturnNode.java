package Nodes;

import main.JottTree;
import main.Token;
import main.TokenType;

import java.util.ArrayList;

public class FunctionReturnNode implements JottTree {

    private final TypeNode typeNode;
    private final String voidNode;

    public FunctionReturnNode(TypeNode typeNode, String voidNode) {
        this.typeNode = typeNode;
        this.voidNode = voidNode;
    }

    public static FunctionReturnNode parseFunctionReturnNode(ArrayList<Token> tokens) throws Exception {
        TypeNode type = TypeNode.parseTypeNode(tokens);

        if (type != null) {
            return new FunctionReturnNode(type, null);
        } else {    
            if (tokens.get(0).getTokenType() == TokenType.ID_KEYWORD) {
                String value = tokens.get(0).getToken();
                if (value.equals("Void")) {
                    tokens.remove(0);
                    return new FunctionReturnNode(null, value);
                }
            }
        }
        return null;
    }

    @Override
    public String convertToJott() {
        if (typeNode != null){
            return typeNode.convertToJott();
        } else {
            return voidNode;
        }
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
