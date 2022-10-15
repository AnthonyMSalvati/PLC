package Nodes;

import main.JottTree;
import main.Token;
import main.TokenType;
import main.InvalidParseException;

import java.util.ArrayList;

public class WhileLoopNode implements JottTree {

    private final BooleanExpressionNode booleanExpressionNode;
    private final BodyNode bodyNode;

    public WhileLoopNode(BooleanExpressionNode booleanExpressionNode, BodyNode bodyNode)
    {
        this.booleanExpressionNode = booleanExpressionNode;
        this.bodyNode = bodyNode;
    }

    public static WhileLoopNode parseWhileLoopNode(ArrayList<Token> tokens) throws Exception {

        if (!(tokens.get(0).getToken().equals("while")))
        {
            return null;
        }
        else
        {
            tokens.remove(0);
            if (!(tokens.get(0).getTokenType() == TokenType.L_BRACKET))
            {
                throw new InvalidParseException("Error: expected \"[\"", 
                tokens.get(0).getFilename(), tokens.get(0).getLineNum());
            }
            else
            {
                tokens.remove(0);
                BooleanExpressionNode booleanExpressionNode = BooleanExpressionNode.parseBooleanExpressionNode(tokens);
                if (!(booleanExpressionNode == null))
                {
                    if (!(tokens.get(0).getTokenType() == TokenType.R_BRACKET))
                    {
                        throw new InvalidParseException("Error: expected \"]\"", 
                        tokens.get(0).getFilename(), tokens.get(0).getLineNum());
                    }
                    else
                    {
                        tokens.remove(0);
                        if (!(tokens.get(0).getTokenType() == TokenType.L_BRACE))
                        {
                            throw new InvalidParseException("Error: expected \"{\"", 
                            tokens.get(0).getFilename(), tokens.get(0).getLineNum());
                        }
                        else
                        {
                            tokens.remove(0);
                            BodyNode bodyNode = BodyNode.parseBodyNode(tokens);
                            if (!(bodyNode == null))
                            {
                                if (!(tokens.get(0).getTokenType() == TokenType.R_BRACE))
                                {
                                    throw new InvalidParseException("Error: expected \"}\"", 
                                    tokens.get(0).getFilename(), tokens.get(0).getLineNum());
                                }
                                else
                                {
                                    tokens.remove(0);
                                    return new WhileLoopNode(booleanExpressionNode, bodyNode);
                                }
                            }
                            else throw new InvalidParseException("Error: unhandled in BodyNode", 
                            tokens.get(0).getFilename(), tokens.get(0).getLineNum());
                        }
                    }
                }
                else
                {
                    throw new InvalidParseException("Error: unhandled in BooleanExpressionNode", 
                    tokens.get(0).getFilename(), tokens.get(0).getLineNum()); //should be handled in BooleanExpressionNode
                }
            }
        }
    }
    @Override
    public String convertToJott() {

        return "while[" + this.booleanExpressionNode.convertToJott() + "]{" + this.bodyNode.convertToJott() +"}";
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
