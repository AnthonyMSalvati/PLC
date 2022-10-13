package Nodes;

import main.InvalidParseException;
import main.JottTree;
import main.Token;

import java.util.ArrayList;

public class EndStatementNode implements JottTree {

    public EndStatementNode() {

    }

    public static EndStatementNode parseEndStatementNode(ArrayList<Token> tokens) throws Exception {
        Token token;
        token = tokens.get(0);
        if (!(token.getToken().equals(";")))
        {
            throw new InvalidParseException("Statement not ended with \";\"", token.getFilename(),
                    token.getLineNum());
        }
        else {
            tokens.remove(0);
            return new EndStatementNode();
        }
    }
    @Override
    public String convertToJott() {
        return ";";
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
