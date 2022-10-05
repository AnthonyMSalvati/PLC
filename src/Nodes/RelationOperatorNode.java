package Nodes;

import main.JottTree;
import main.Token;
import main.TokenType;
import java.util.ArrayList;

/**
 * @author Ben Froment
 */
public class RelationOperatorNode implements JottTree {

    private final String value;

    public RelationOperatorNode(String value) {
        this.value = value;
    }

    public static RelationOperatorNode parseRelationOperatorNode (ArrayList<Token> tokens) {
        if (tokens.get(0).getTokenType() == TokenType.REL_OP) {
            String value = tokens.get(0).getToken();
            tokens.remove(0);
            return new RelationOperatorNode(value);
        }
        return null;
    }

    @Override
    public String convertToJott() {
        return value;
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
