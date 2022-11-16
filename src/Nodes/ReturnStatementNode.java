package Nodes;

import main.JottTree;
import main.SymbolTable;
import main.Token;
import main.TokenType;
import main.InvalidParseException;

import java.util.ArrayList;

public class ReturnStatementNode implements JottTree {

    private final String value;
    private final ExpressionNode expNode;
    private final EndStatementNode endStmNode;

    public ReturnStatementNode(String value, ExpressionNode expNode, EndStatementNode endStmNode) {
        this.value = value;
        this.expNode = expNode;
        this.endStmNode = endStmNode;
    }

    public static ReturnStatementNode parseReturnStatementNode(ArrayList<Token> tokens) throws Exception {
        if (tokens.get(0).getTokenType() == TokenType.ID_KEYWORD) {
            String value = tokens.get(0).getToken();
            if (value.equals("return")) {
                tokens.remove(0);
                ExpressionNode expNode = ExpressionNode.parseExpressionNode(tokens);
                if (expNode != null) {
                    EndStatementNode endStmNode = EndStatementNode.parseEndStatementNode(tokens);
                    return new ReturnStatementNode(value, expNode, endStmNode);
                } else {
                    throw new InvalidParseException("Error: expected <expr>", 
                    tokens.get(0).getFilename(), tokens.get(0).getLineNum());
                }
            }
        }    
        return null;
    }    

    public String getType(SymbolTable symbolTable){
        return this.expNode.getType(symbolTable);
    }
    @Override
    public String convertToJott() {
        return value + " " + expNode.convertToJott() + endStmNode.convertToJott();
    }

    @Override
    public String convertToJava() {
        return "return " + this.expNode.convertToJava()
			+ this.endStmNode.convertToJava();
    }

    @Override
    public String convertToC() {
        return "return " + this.expNode.convertToC()
			+ this.endStmNode.convertToC();
    }

    @Override
    public String convertToPython(int nestLevel) { //Ian
        return "return " + this.expNode.convertToPython(nestLevel)
			+ this.endStmNode.convertToPython(nestLevel);
    }

    @Override
    public boolean validateTree(SymbolTable symbolTable) throws Exception {
        if (value != null && expNode != null && endStmNode != null) {
            return expNode.validateTree(symbolTable) && endStmNode.validateTree(symbolTable);
        }
        return false;
    }
}
