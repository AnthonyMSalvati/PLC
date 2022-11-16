package Nodes;

import main.JottTree;
import main.SymbolTable;
import main.Token;
import java.util.ArrayList;

/**
 * @author Ben Froment
 *
 * Node that represents a string expression
 */
public class StringExpressionNode implements JottTree {

    private final StringLiteralNode stringLiteralNode;
    private final IdNode idNode;
    private final FunctionCallNode functionCallNode;

    // < id >
    public StringExpressionNode(IdNode idNode) {
        this.stringLiteralNode = null;
        this.functionCallNode = null;

        this.idNode = idNode;
    }

    // < func_call >
    public StringExpressionNode(FunctionCallNode functionCallNode){
        this.idNode = null;
        this.stringLiteralNode = null;

        this.functionCallNode = functionCallNode;
    }

    // < str_literal >
    public StringExpressionNode(StringLiteralNode stringLiteralNode) {
        this.idNode = null;
        this.functionCallNode = null;

        this.stringLiteralNode = stringLiteralNode;
    }

    // Function called by its parent node to parse the list of tokens
    public static StringExpressionNode parseStringExpressionNode (ArrayList<Token> tokens) throws Exception {
        FunctionCallNode functionCallNode = FunctionCallNode.parseFunctionCallNode(tokens);
        if (functionCallNode != null) {
            return new StringExpressionNode(functionCallNode);
        }
        IdNode idNode = IdNode.parseIdNode(tokens);
        if (idNode != null) {
            return new StringExpressionNode(idNode);
        }
        StringLiteralNode stringLiteralNode = StringLiteralNode.parseStringLiteralNode(tokens);
        if (stringLiteralNode != null) {
            return new StringExpressionNode(stringLiteralNode);
        }
        return null;
    }

    public String getType(SymbolTable symbolTable) throws Exception{
        if (functionCallNode != null) {
            return functionCallNode.getType(symbolTable);
        }

        if (idNode != null) {
            return symbolTable.getType(idNode.getName());
        }

        if (stringLiteralNode != null) {
            return "String";
        }
        return null;
    }

    @Override
    public String convertToJott() {
        if (functionCallNode != null) {
            return functionCallNode.convertToJott();
        }  else if (idNode != null) {
            return idNode.convertToJott();
        } else if (stringLiteralNode != null) {
            return stringLiteralNode.convertToJott();
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
    public String convertToPython(int nestLevel) { //Ian
		if (this.stringLiteralNode != null) {
			return this.stringLiteralNode.convertToPython(nestLevel);
		}
		if (this.idNode != null) {
			return this.idNode.convertToPython(nestLevel);
		}
		if (this.functionCallNode != null) {
			return this.functionCallNode.convertToPython(nestLevel);
		}
        return "";
    }

    @Override
    public boolean validateTree(SymbolTable symbolTable) throws Exception {
        if (functionCallNode != null) {
            return functionCallNode.validateTree(symbolTable);
        }  else if (idNode != null) {
            return idNode.validateTree(symbolTable);
        } else if (stringLiteralNode != null) {
            return stringLiteralNode.validateTree(symbolTable);
        }
        return false;
    }
}
