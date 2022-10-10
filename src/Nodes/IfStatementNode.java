package Nodes;

import main.JottTree;
import main.Token;
import main.TokenType;

import java.util.ArrayList;

public class IfStatementNode implements JottTree {

    private final BooleanExpressionNode booleanExpressionNode;
    private final BodyNode bodyNode;
    private final ElseIfStatementNode elseIfStatementNode;
    private final ElseStatementNode elseStatementNode;

    public IfStatementNode(BooleanExpressionNode booleanExpressionNode, BodyNode bodyNode,  ElseIfStatementNode elseIfStatementNode, ElseStatementNode elseStatementNode)
    {
        this.booleanExpressionNode = booleanExpressionNode;
        this.bodyNode = bodyNode;
        this.elseIfStatementNode = elseIfStatementNode;
        this.elseStatementNode = elseStatementNode;
    }

    public static IfStatementNode parseIfStatementNode(ArrayList<Token> tokens) throws Exception {
        if (!(tokens.get(0).getToken() == "if"))
        {
            throw new Exception("Error: expected \"if\"");
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
                                throw new Exception("Error: unhandled in BodyNode"); //should be handled in body node
                            }
                            else
                            {
                                if (!(tokens.get(0).getTokenType() == TokenType.R_BRACE))
                                {
                                    throw new Exception("Error: expected \"}\"");
                                }
                                else {
                                    tokens.remove(0);
                                    ElseIfStatementNode elseIfStatementNode = ElseIfStatementNode.parseElseIfStatementNode(tokens);
                                    ElseStatementNode elseStatementNode = ElseStatementNode.parseElseStatementNode(tokens);

                                    return new IfStatementNode(booleanExpressionNode, bodyNode, elseIfStatementNode, elseStatementNode);
                                }
                            }
                        }
                    }
                }
            }
        }
    }

    @Override
    public String convertToJott()
    {
        return "if[" + booleanExpressionNode.convertToJott() + "]{" + bodyNode.convertToJott() + "}" + elseIfStatementNode.convertToJott() + elseStatementNode.convertToJott();
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
