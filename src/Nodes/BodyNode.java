package Nodes;

import main.JottTree;
import main.Token;
import main.TokenType;

import java.lang.reflect.Array;
import java.util.ArrayList;

public class BodyNode implements JottTree {

    private final BodyStatementNode bodyStmNode;
    private final BodyNode bodyNode;
    private final ReturnStatementNode returnStmNode;
    private final String empty = "E";

    public BodyNode(BodyStatementNode bodyStmNode, BodyNode bodyNode, ReturnStatementNode returnStmNode) {
        this.bodyStmNode = bodyStmNode;
        this.bodyNode = bodyNode;
        this.returnStmNode = returnStmNode;
    }

    public static BodyNode parseBodyNode(ArrayList<Token> tokens) throws Exception {
        BodyStatementNode bodyStm = BodyStatementNode.parseBodyStatementNode(tokens);
        if (bodyStm != null) {
            BodyNode bodyNode = BodyNode.parseBodyNode(tokens);
            if (bodyNode != null) {
                return new BodyNode(bodyStm, bodyNode, null);
            } else {
                throw new Exception("Error: expected <body>");
            }
        } else {
            ReturnStatementNode returnStmNode = ReturnStatementNode.parseReturnStatementNode(tokens);
            if (returnStmNode != null) {
                return new BodyNode(bodyStm, null, returnStmNode);
            } else {
                return null; 
            }
        }
    }

    @Override
    public String convertToJott() {
        if (bodyStmNode != null && bodyNode != null) {
            return bodyStmNode.convertToJott() + bodyNode.convertToJott();
        } else if (returnStmNode != null) {
            return returnStmNode.convertToJott();
        } else {
            return empty;
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
    public String convertToPython() {
        return null;
    }

    @Override
    public boolean validateTree() {
        return false;
    }
}
