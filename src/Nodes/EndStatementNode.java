package Nodes;

import main.JottTree;
import main.Token;

import java.util.ArrayList;

public class EndStatementNode implements JottTree {

    public EndStatementNode() {

    }

    public static EndStatementNode parseEndStatementNode(ArrayList<Token> tokens) throws Exception {
        if (!(tokens.get(0).getToken() == ";"))
        {
            throw new Exception("Statement not ended with \";\"");
        }
        else {
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
