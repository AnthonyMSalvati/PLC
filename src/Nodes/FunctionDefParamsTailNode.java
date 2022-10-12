package Nodes;

import main.JottTree;
import main.Token;
import main.TokenType;

import java.util.ArrayList;

/**
 * @author Ben Froment
 */
public class FunctionDefParamsTailNode implements JottTree {

    private final IdNode idNode;
    private final TypeNode typeNode;
    private final FunctionDefParamsTailNode functionDefParamsTailNode;

    public FunctionDefParamsTailNode(IdNode idNode, TypeNode typeNode,
                                 FunctionDefParamsTailNode functionDefParamsTailNode) {
        this.idNode = idNode;
        this.typeNode = typeNode;
        this.functionDefParamsTailNode = functionDefParamsTailNode;
    }

    public static FunctionDefParamsTailNode parseFunctionDefParamsTailNode(ArrayList<Token> tokens) throws Exception {
        if (tokens.get(0).getTokenType() == TokenType.COMMA) {
            tokens.remove(0);
            IdNode idNode = IdNode.parseIdNode(tokens);
            if (idNode != null) {
                if (tokens.get(0).getTokenType() == TokenType.COLON) {
                    tokens.remove(0);
                    TypeNode typeNode = TypeNode.parseTypeNode(tokens);
                    if (typeNode != null) {
                        FunctionDefParamsTailNode functionDefParamsTailNode =
                                FunctionDefParamsTailNode.parseFunctionDefParamsTailNode(tokens);
                        if (functionDefParamsTailNode != null) {
                            return new FunctionDefParamsTailNode(idNode, typeNode, functionDefParamsTailNode);
                        }
                        return new FunctionDefParamsTailNode(idNode, typeNode, null);
                    }
                    throw new Exception("Error: expected <type>");
                }
                throw new Exception("Error: expected COLON");
            }
            throw new Exception("Error: expected <id>");
        }
        return null;
    }

    @Override
    public String convertToJott() {
        if (idNode != null) {
            if (typeNode != null) {
                if (functionDefParamsTailNode != null) {
                    return "," + idNode.convertToJott() + ":" + typeNode.convertToJott() +
                            functionDefParamsTailNode.convertToJott();
                }
                return "," + idNode.convertToJott() + ":" + typeNode.convertToJott();
            }
        }
        return "";
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
