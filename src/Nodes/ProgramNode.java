package Nodes;

import main.JottTree;
import main.SymbolTable;
import main.Token;

import java.util.ArrayList;

/**
 * @author Ben Froment
 *
 * Node that represents the entire Program as a tree
 */
public class ProgramNode implements JottTree {

    private final FunctionListNode functionListNode;

    // < function_list >
    public ProgramNode(FunctionListNode functionListNode) {
        this.functionListNode = functionListNode;
    }

    // Function that calls its child nodes to parse the list of tokens
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
    public boolean validateTree(SymbolTable symbolTable) throws Exception {
        if (functionListNode != null) {
            return functionListNode.validateTree(symbolTable);
        }
        return false;
    }
}
