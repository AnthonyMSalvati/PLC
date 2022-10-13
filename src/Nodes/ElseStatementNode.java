package Nodes;

import main.InvalidParseException;
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
        Token token;
        token = tokens.get(0);
        if (!(token.getToken().equals("else")))
        {
            return new ElseStatementNode();
        }
        else
        {
            tokens.remove(0);
            token = tokens.get(0);
            if (!(token.getTokenType() == TokenType.L_BRACE))
            {
                throw new InvalidParseException("Error: expected \"{\"", token.getFilename(),
                        token.getLineNum());
            }
            else
            {
                tokens.remove(0);
                BodyNode bodyNode = BodyNode.parseBodyNode(tokens);

                if (bodyNode != null)
                {
                    token = tokens.get(0);
                    if (!(token.getTokenType() == TokenType.R_BRACE))
                    {
                        throw new InvalidParseException("Error: expected \"}\"", token.getFilename(),
                                token.getLineNum());
                    }
                    else
                    {
                        tokens.remove(0);
                        return new ElseStatementNode(bodyNode);
                    }
                }
            }
        }
        return null;
    }
    @Override
    public String convertToJott()
    {
        if (this.bodyNode != null)
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
