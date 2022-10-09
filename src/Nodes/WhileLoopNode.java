package Nodes;

import main.JottTree;
import main.Token;
import main.TokenType;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Arrays;

public class WhileLoopNode implements JottTree {

    private final String lBracket = "[";
    private final String rBracket = "]";
    private final BooleanExpressionNode booleanExpressionNode;
    private final String lBrace = "{";
    private final String rBrace = "}";
    private final BodyNode bodyNode;

    public WhileLoopNode(BooleanExpressionNode booleanExpressionNode, BodyNode bodyNode)
    {
        this.booleanExpressionNode = booleanExpressionNode;
        this.bodyNode = bodyNode;
    }

    public static WhileLoopNode parseWhileLoopNode(ArrayList<Token> tokens) throws Exception {
        int currentToken = 0;

        if (!(tokens.get(0).getToken() == "while"))
        {
            throw new Exception("Error message"); //@TODO correct error messages
        }
        else
        {
            tokens.remove(0);
            if (!(tokens.get(0).getTokenType() == TokenType.L_BRACKET))
            {
                throw new Exception("Error message");
            }
            else
            {
                tokens.remove(0);
                BooleanExpressionNode booleanExpressionNode = BooleanExpressionNode.parseBooleanExpressionNode(tokens);
                if (!(booleanExpressionNode == null))
                {
                    if (!(tokens.get(0).getTokenType() == TokenType.R_BRACKET))
                    {
                        throw new Exception("Error message");
                    }
                    else
                    {
                        tokens.remove(0);
                        if (!(tokens.get(currentToken).getTokenType() == TokenType.L_BRACE))
                        {
                            throw new Exception("Error message");
                        }
                        else
                        {
                            tokens.remove(0);
                            BodyNode bodyNode = BodyNode.parseBodyNode(tokens);
                            if (!(bodyNode == null))
                            {
                                if (!(tokens.get(0).getTokenType() == TokenType.R_BRACE))
                                {
                                    throw new Exception("Error message");
                                }
                            }
                            else
                            {
                                tokens.remove(0);
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

        return "while[" + this.booleanExpressionNode.convertToJott() + "]{" + bodyNode.convertToJott() +"}";
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
