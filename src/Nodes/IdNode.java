package Nodes;

import main.JottTree;
import main.Token;
import main.TokenType;
import java.util.ArrayList;

/**
 * @author Ben Froment
 *
 * Node that represents a string id value
 */
public class IdNode implements JottTree {

    private final String value;

    //  < l_char > < char >*
    public IdNode(String value) {

        this.value = value;
    }

    // Function called by its parent node to parse the list of tokens
    public static IdNode parseIdNode (ArrayList<Token> tokens) {
        if (tokens.size() == 0) {
            return null;
        }
        if (tokens.get(0).getTokenType() == TokenType.ID_KEYWORD) {
            String value = tokens.get(0).getToken();
            if (Character.isLowerCase(value.charAt(0))) {

                tokens.remove(0);
                return new IdNode(value);
            }
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
        String regex = "^[a-z]\\w*";
        if (value.matches(regex))
        {
            return true;
        }
        return false;
    }
}
