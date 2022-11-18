package Nodes;

import main.InvalidValidateException;
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
    private final Token token;

    // < function_list >
    public ProgramNode(FunctionListNode functionListNode, Token token) {
        this.functionListNode = functionListNode;
        this.token = token;
    }

    // Function that calls its child nodes to parse the list of tokens
    public static ProgramNode parseProgramNode(ArrayList<Token> tokens) throws Exception {
        Token token = null;
        if (tokens.size() > 0){
            token = tokens.get(0);
        }
        FunctionListNode functionListNode = FunctionListNode.parseFunctionListNode(tokens);
        if (functionListNode != null) {
            return new ProgramNode(functionListNode, token);
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

        String fileName = token.getFilename();
        String className = fileName.split("\\.")[0];
        String header = "public class " + className + " {";
        if (functionListNode != null) {
            return header + functionListNode.convertToJava() + " }";
        }
        return "";
    }

    @Override
    public String convertToC() {
        String header = "\n"
                + "#include <stdio.h>\n"
                + "#include <string.h>\n"
                + "#include <stdlib.h>\n"
                ;
        if (functionListNode != null) {
            return header + functionListNode.convertToJava();
        }
        return "";
    }

    @Override
    public String convertToPython(int nestLevel) { //Ian
        if (functionListNode != null) {
			// nestLevel should be zero
			return functionListNode.convertToPython(0) + "main()";
		}
		return "";
    }

    @Override
    public boolean validateTree(SymbolTable symbolTable) throws Exception {
        if (functionListNode != null) {
            if (functionListNode.validateTree(symbolTable)) {
                if (!symbolTable.hasFunction("main")) {
                    if (token != null) {
                        throw new InvalidValidateException("Missing Void function main", token.getFilename(), token.getLineNum());
                    }
                    return false;
                }
                if (!symbolTable.getFunctionReturnType("main").equals("Void")){
                    if (token != null) {
                        throw new InvalidValidateException("main function is not type Void", token.getFilename(), token.getLineNum());
                    }
                    return false;
                }
                if (symbolTable.hasKeywords()){
                    if (token != null) {
                        throw new InvalidValidateException("Cannot use keyword as variable name or function name",
                                token.getFilename(), token.getLineNum());
                    }
                    return false;
                }
                return true;
            }
        }
        return false;
    }
}
