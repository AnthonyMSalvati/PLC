package Nodes;

import main.JottTree;
import main.Token;
import main.TokenType;

import java.util.ArrayList;

/**
 * @author Ben Froment
 */
public class FunctionDefNode implements JottTree {

    private final IdNode idNode;
    private final FunctionDefParamsNode functionDefParamsNode;
    private final FunctionReturnNode functionReturnNode;
    private final BodyNode bodyNode;

    public FunctionDefNode(IdNode idNode, FunctionDefParamsNode functionDefParamsNode,
                           FunctionReturnNode functionReturnNode, BodyNode bodyNode) {
        this.idNode = idNode;
        this.functionDefParamsNode = functionDefParamsNode;
        this.functionReturnNode = functionReturnNode;
        this.bodyNode = bodyNode;
    }

    public static FunctionDefNode parseFunctionDefNode(ArrayList<Token> tokens) throws Exception {
        IdNode idNode = IdNode.parseIdNode(tokens);
        if (idNode != null) {
            if (tokens.get(0).getTokenType() == TokenType.L_BRACKET) {
                tokens.remove(0);
                FunctionDefParamsNode functionDefParamsNode = FunctionDefParamsNode.parseFunctionDefParamsNode(tokens);
                //if (functionDefParamsNode != null) {
                    if (tokens.get(0).getTokenType() == TokenType.R_BRACKET) {
                        tokens.remove(0);
                        if (tokens.get(0).getTokenType() == TokenType.COLON) {
                            tokens.remove(0);
                            FunctionReturnNode functionReturnNode = FunctionReturnNode.parseFunctionReturnNode(tokens);
                            if (functionReturnNode != null) {
                                if (tokens.get(0).getTokenType() == TokenType.L_BRACE) {
                                    tokens.remove(0);
                                    BodyNode bodyNode = BodyNode.parseBodyNode(tokens);
                                    if (bodyNode != null) {
                                        if (tokens.get(0).getTokenType() == TokenType.R_BRACE) {
                                            tokens.remove(0);
                                            return new FunctionDefNode(idNode, functionDefParamsNode,
                                                    functionReturnNode, bodyNode);
                                        }
                                        throw new Exception("Error: expected R_BRACE");
                                    }
                                }
                                throw new Exception("Error: expected L_BRACE");
                            }
                        }
                        throw new Exception("Error: expected COLON");
                    }
                    throw new Exception("Error: expected R_BRACKET");
                //}
            }
            throw new Exception("Error: expected L_BRACKET");
        }
        return null;
    }

    @Override
    public String convertToJott() {
        if (idNode != null) {
            if (functionDefParamsNode != null) {
                if (functionReturnNode != null) {
                    if (bodyNode != null) {
                        return idNode.convertToJott() + "[" + functionDefParamsNode.convertToJott() +
                                "]:" + functionReturnNode.convertToJott() + "{" +
                                bodyNode.convertToJott() + "}";
                    }
                    return idNode.convertToJott() + "[" + functionDefParamsNode.convertToJott() +
                            "]:" + functionReturnNode.convertToJott() + "{}";
                }
            }
            if (functionReturnNode != null) {
                if (bodyNode != null) {
                    return idNode.convertToJott() + "[]:" + functionReturnNode.convertToJott() + "{" +
                            bodyNode.convertToJott() + "}";
                }
                return idNode.convertToJott() + "[]:" + functionReturnNode.convertToJott() + "{}";
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
