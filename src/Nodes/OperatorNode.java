package Nodes;

import main.JottTree;
import main.Token;
import main.TokenType;
import java.util.ArrayList;
import main.SymbolTable;

/**
 * @author Ben Froment
 *
 * Node representing a math operation
 */
public class OperatorNode implements JottTree {

    private final String value;

    // + | * | / | | -
    public OperatorNode(String value) {

        this.value = value;
    }

    // Function called by its parent node to parse the list of tokens
    public static OperatorNode parseOperatorNode (ArrayList<Token> tokens) {
        if (tokens.get(0).getTokenType() == TokenType.MATH_OP) {
            String value = tokens.get(0).getToken();

            tokens.remove(0);
            return new OperatorNode(value);
        } else {
            return null;
        }
    }

    @Override
    public String convertToJott() {
        return value;
    }

    @Override
    public String convertToJava() {
        return this.value;
    }

    @Override
    public String convertToC() {
        return this.value;
    }

    @Override
    public String convertToPython(int nestLevel) { //Ian
        return this.value;
    }

    @Override
    public boolean validateTree(SymbolTable symbolTable) throws Exception {
        return true;
    }
}
