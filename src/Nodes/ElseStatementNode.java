package Nodes;

import main.JottTree;
import main.Token;
import main.TokenType;

import java.util.ArrayList;

public class ElseStatementNode implements JottTree {

    private final BodyNode bodyNode;
    public ElseStatementNode(BodyNode bodyNode)
    {
        this.bodyNode = bodyNode;
    }

    public ElseStatementNode()
    {
        this.bodyNode = null;
    }

    public static ElseStatementNode parseElseStatementNode(ArrayList<Token> tokens) throws Exception
    {
        if (!(tokens.get(0).getToken() == "else"))
        {
            return new ElseStatementNode();
        }
        else
        {
            tokens.remove(0);
            if (!(tokens.get(0).getTokenType() == TokenType.L_BRACE))
            {
                throw new Exception("Error: expected \"[\"");
            }
            else
            {
                tokens.remove(0);
                BodyNode bodyNode = BodyNode.parseBodyNode(tokens);

                if (!(bodyNode == null))
                {
                    if (!(tokens.get(0).getTokenType() == TokenType.R_BRACE))
                    {
                        throw new Exception("Error: expected \"]\"");
                    }
                    else
                    {
                        return new ElseStatementNode(bodyNode);
                    }
                }
                else throw new Exception("Error: unhandled in BodyNode"); // should be handled in body node
            }
        }
    }
    @Override
    public String convertToJott()
    {
        if (!(this.bodyNode == null))
        {
            return "else{" + this.bodyNode.convertToJott() + "}";
        }
        else return "";
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
