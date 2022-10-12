package Nodes;

import main.JottTree;
import main.Token;

import java.util.ArrayList;

/**
 * @author Ben Froment
 */
public class ProgramNode implements JottTree {

    private final FunctionListNode functionListNode;

    public ProgramNode(FunctionListNode functionListNode) {
        this.functionListNode = functionListNode;
    }

    public static ProgramNode parseProgramNode(ArrayList<Token> tokens) throws Exception {
        FunctionListNode functionListNode = FunctionListNode.parseFunctionListNode(tokens);
        if (functionListNode != null) {
            return new ProgramNode(functionListNode);
        }
        return null;
    }

    @Override
    public String convertToJott() {
        if (functionListNode != null) {
            return functionListNode.convertToJott();
        }
        return null;
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
