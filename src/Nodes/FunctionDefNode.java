package Nodes;

import main.InvalidParseException;
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
        Token token;
        IdNode idNode = IdNode.parseIdNode(tokens);
        if (idNode != null) {
            token = tokens.get(0);
            if (token.getTokenType() == TokenType.L_BRACKET) {
                tokens.remove(0);
                FunctionDefParamsNode functionDefParamsNode = FunctionDefParamsNode.parseFunctionDefParamsNode(tokens);
                token = tokens.get(0);
                if (token.getTokenType() == TokenType.R_BRACKET) {
                    tokens.remove(0);
                    token = tokens.get(0);
                    if (token.getTokenType() == TokenType.COLON) {
                        tokens.remove(0);
                        FunctionReturnNode functionReturnNode = FunctionReturnNode.parseFunctionReturnNode(tokens);
                        if (functionReturnNode != null) {
                            token = tokens.get(0);
                            if (token.getTokenType() == TokenType.L_BRACE) {
                                tokens.remove(0);
                                BodyNode bodyNode = BodyNode.parseBodyNode(tokens);
                                if (bodyNode != null) {
                                    if (tokens.size() != 0) {
                                        token = tokens.get(0);
                                        if (token.getTokenType() == TokenType.R_BRACE) {
                                            tokens.remove(0);
                                            return new FunctionDefNode(idNode, functionDefParamsNode,
                                                    functionReturnNode, bodyNode);
                                        }
                                    }
                                    throw new InvalidParseException("Error: expected \"}\"", token.getFilename(),
                                            token.getLineNum());
                                }
                            }
                            throw new InvalidParseException("Error: expected \"{\"", token.getFilename(),
                                    token.getLineNum());
                        }
                    }
                    throw new InvalidParseException("Error: expected \":\"", token.getFilename(),
                            token.getLineNum());
                }
                throw new InvalidParseException("Error: expected \"]\"", token.getFilename(),
                        token.getLineNum());
            }
            throw new InvalidParseException("Error: expected \"{\"", token.getFilename(),
                    token.getLineNum());
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
