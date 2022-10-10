package Nodes;

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
        if (!(tokens.get(0).getToken() == "elseif"))
        {
            return new ElseIfStatementNode();
        }
        else
        {
            tokens.remove(0);
            if (!(tokens.get(0).getTokenType() == TokenType.L_BRACKET))
            {
                throw new Exception("Error: expected \"[\"");
            }
            else
            {
                tokens.remove(0);
                BooleanExpressionNode booleanExpressionNode = BooleanExpressionNode.parseBooleanExpressionNode(tokens);

                if (booleanExpressionNode == null)
                {
                    throw new Exception("Error: unhandled in BooleanExpressionNode"); //should be handled in BooleanExpressionNode
                }
                else
                {
                    if (!(tokens.get(0).getTokenType() == TokenType.R_BRACKET))
                    {
                        throw new Exception("Error: expected \"]\"");
                    }
                    else
                    {
                        tokens.remove(0);
                        if (!(tokens.get(0).getTokenType() == TokenType.L_BRACE))
                        {
                            throw new Exception("Error: expected \"{\"");
                        }
                        else
                        {
                            tokens.remove(0);
                            BodyNode bodyNode = BodyNode.parseBodyNode(tokens);

                            if (bodyNode == null)
                            {
                                throw new Exception("Error: unhandled in BodyNode"); //should be handled in BodyNode
                            }
                            else
                            {
                                if (!(tokens.get(0).getTokenType() == TokenType.R_BRACE))
                                {
                                    throw new Exception("Error: expected \"}\"");
                                }
                                else
                                {
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
    }
    @Override
    public String convertToJott() {

        if (bodyNode == null)
        {
            return "";
        }
        else
        {
            return "elseif[" + this.booleanExpressionNode.convertToJott() + "]{" + this.bodyNode.convertToJott() + "}" + this.elseIfStatementNode.convertToJott();
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
