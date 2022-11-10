package Nodes;

import main.JottTree;
import main.Token;
import main.SymbolTable;

import java.util.ArrayList;

public class BodyNode implements JottTree {

    private final BodyStatementNode bodyStmNode;
    private final BodyNode bodyNode;
    private final ReturnStatementNode returnStmNode;

    public BodyNode(BodyStatementNode bodyStmNode, BodyNode bodyNode, ReturnStatementNode returnStmNode) {
        this.bodyStmNode = bodyStmNode;
        this.bodyNode = bodyNode;
        this.returnStmNode = returnStmNode;
    }

    public static BodyNode parseBodyNode(ArrayList<Token> tokens) throws Exception {
        if (tokens.size() == 0) {
            return null;
        }
        if (!tokens.get(0).getToken().equals("return")) {
            BodyStatementNode bodyStm = BodyStatementNode.parseBodyStatementNode(tokens);
            if (bodyStm != null) {
                BodyNode bodyNode = BodyNode.parseBodyNode(tokens);
                if (bodyNode != null) {
                    return new BodyNode(bodyStm, bodyNode, null);
                }
                return new BodyNode(bodyStm, null, null);
            }
        }
        ReturnStatementNode returnStmNode = ReturnStatementNode.parseReturnStatementNode(tokens);
        if (returnStmNode != null) {
            return new BodyNode(null, null, returnStmNode);
        } else {
            return null;
        }

    }


    @Override
    public String convertToJott() {
        if (bodyStmNode != null) {
            if (bodyNode != null) {
                return bodyStmNode.convertToJott() + bodyNode.convertToJott();
            }
            return bodyStmNode.convertToJott();
        }
        if (returnStmNode != null) {
            return returnStmNode.convertToJott();
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
    public boolean validateTree(SymbolTable symbolTable) throws Exception  {
        if (bodyStmNode != null && bodyNode != null && returnStmNode == null) {
            return bodyStmNode.validateTree(symbolTable) && bodyNode.validateTree(symbolTable);
        } else if (bodyStmNode == null && bodyNode == null && returnStmNode != null) {
            return returnStmNode.validateTree(symbolTable);
        } else if (bodyStmNode == null && bodyNode == null && returnStmNode == null) {
            return true;
        } else return false;
    }
}
