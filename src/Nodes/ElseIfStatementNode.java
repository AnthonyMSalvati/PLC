package Nodes;

import main.InvalidParseException;
import main.JottTree;
import main.Token;
import main.TokenType;

import java.util.ArrayList;

public class ElseIfStatementNode implements JottTree {

    private final BooleanExpressionNode booleanExpressionNode;
    private final BodyNode bodyNode;
    private final ElseIfStatementNode elseIfStatementNode;

    public ElseIfStatementNode(BooleanExpressionNode booleanExpressionNode, BodyNode bodyNode, ElseIfStatementNode elseIfStatementNode)
    {
        this.booleanExpressionNode = booleanExpressionNode;
        this.bodyNode = bodyNode;
        this.elseIfStatementNode = elseIfStatementNode;
    }

    public ElseIfStatementNode()
    {
        this.booleanExpressionNode = null;
        this.bodyNode = null;
        this.elseIfStatementNode = null;
    }

    public static ElseIfStatementNode parseElseIfStatementNode(ArrayList<Token> tokens) throws Exception {
        Token token;
        token = tokens.get(0);
        if (!(token.getToken().equals("elseif"))) {
            return new ElseIfStatementNode();
        } else {
            tokens.remove(0);
            token = tokens.get(0);
            if (!(token.getTokenType() == TokenType.L_BRACKET)) {
                throw new InvalidParseException("Error: expected \"[\"", token.getFilename(),
                        token.getLineNum());
            } else {
                tokens.remove(0);
                BooleanExpressionNode booleanExpressionNode = BooleanExpressionNode.parseBooleanExpressionNode(tokens);

                if (booleanExpressionNode != null) {
                    token = tokens.get(0);
                    if (!(token.getTokenType() == TokenType.R_BRACKET)) {
                        throw new InvalidParseException("Error: expected \"]\"", token.getFilename(),
                                token.getLineNum());
                    } else {
                        tokens.remove(0);
                        token = tokens.get(0);
                        if (!(token.getTokenType() == TokenType.L_BRACE)) {
                            throw new InvalidParseException("Error: expected \"{\"", token.getFilename(),
                                    token.getLineNum());
                        } else {
                            tokens.remove(0);
                            BodyNode bodyNode = BodyNode.parseBodyNode(tokens);

                            if (bodyNode != null) {
                                token = tokens.get(0);
                                if (!(token.getTokenType() == TokenType.R_BRACE)) {
                                    throw new InvalidParseException("Error: expected \"}\"", token.getFilename(),
                                            token.getLineNum());
                                } else {
                                    tokens.remove(0);
                                    ElseIfStatementNode elseIfStatementNode = ElseIfStatementNode.parseElseIfStatementNode(tokens);

                                    return new ElseIfStatementNode(booleanExpressionNode, bodyNode, elseIfStatementNode);
                                }
                            }
                        }
                    }
                }
            }
        }
        return null;
    }

    @Override
    public String convertToJott() {
        if (bodyNode != null) {
            if (booleanExpressionNode != null) {
                if (elseIfStatementNode != null) {
                    return "elseif[" + this.booleanExpressionNode.convertToJott() + "]{" +
                            this.bodyNode.convertToJott() + "}" + this.elseIfStatementNode.convertToJott();
                }
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
