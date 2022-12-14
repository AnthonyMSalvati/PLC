package Nodes;

import main.JottTree;
import main.Token;

import java.util.ArrayList;

/**
 * @author Ben Froment
 *
 * Node representing a list of functions
 */
public class FunctionListNode implements JottTree {

    private final FunctionDefNode functionDefNode;
    private final FunctionListNode functionListNode;

    //  < function_def > < function_list >
    public FunctionListNode(FunctionDefNode functionDefNode, FunctionListNode functionListNode) {
        this.functionDefNode = functionDefNode;
        this.functionListNode = functionListNode;
    }

    // Function called by its parent node to parse the list of tokens
    public static FunctionListNode parseFunctionListNode(ArrayList<Token> tokens) throws Exception {
        FunctionDefNode functionDefNode = FunctionDefNode.parseFunctionDefNode(tokens);
        if (functionDefNode != null) {
            FunctionListNode functionListNode = FunctionListNode.parseFunctionListNode(tokens);
            if (functionListNode != null) {
                return new FunctionListNode(functionDefNode, functionListNode);
            }
            return new FunctionListNode(functionDefNode, null);
        }
        return null;
    }

    @Override
    public String convertToJott() {
        if (functionDefNode != null) {
            if (functionListNode != null) {
                return functionDefNode.convertToJott() + functionListNode.convertToJott();
            }
            return functionDefNode.convertToJott();
        }
        return "";
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
