package Nodes;

import main.JottTree;
import main.Token;
import main.SymbolTable;

import java.util.ArrayList;

public class BodyStatementNode implements JottTree {

    private final IfStatementNode ifStmNode;
    private final WhileLoopNode whileNode;
    private final StatementNode stmNode;

    public BodyStatementNode(IfStatementNode ifStmNode, WhileLoopNode whileNode, StatementNode stmNode) {
        this.ifStmNode = ifStmNode;
        this.whileNode = whileNode;
        this.stmNode = stmNode;
    }

    public static BodyStatementNode parseBodyStatementNode(ArrayList<Token> tokens) throws Exception {
        IfStatementNode ifStmNode = IfStatementNode.parseIfStatementNode(tokens);
        if (ifStmNode != null) {
            return new BodyStatementNode(ifStmNode, null, null);
        }
        WhileLoopNode whileNode = WhileLoopNode.parseWhileLoopNode(tokens);
        if (whileNode != null) {
            return new BodyStatementNode(null, whileNode, null);
        }
        StatementNode stmNode = StatementNode.parseStatementNode(tokens);
        if (stmNode != null) {
            return new BodyStatementNode(null, null, stmNode);
        }
        return null;
    }    

    @Override
    public String convertToJott() {
        if (ifStmNode != null) {
            return ifStmNode.convertToJott();
        } else if (whileNode != null) {
            return whileNode.convertToJott();
        } else {
            return stmNode.convertToJott();
        }
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
    public String convertToPython(int nestLevel) {
		if (this.stmNode != null) {return this.stmNode.convertToPython();}
		if (this.ifStmNode != null) {return this.ifStmNode.convertToPython(nestLevel);}
		if (this.whileNode != null) {return this.whileNode.convertToPython(nestLevel);}
        return "";
    }

    @Override
    public boolean validateTree(SymbolTable symbolTable) throws Exception {
        if (ifStmNode != null && whileNode == null && stmNode == null) {
            return ifStmNode.validateTree();
        } else if (ifStmNode == null && whileNode != null && stmNode == null) {
            return whileNode.validateTree();
        } else if (ifStmNode == null && whileNode == null && stmNode != null) {
            return stmNode.validateTree();
        }
        return false;
    }
}
