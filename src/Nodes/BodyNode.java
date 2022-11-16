package Nodes;

import main.InvalidValidateException;
import main.JottTree;
import main.Token;
import main.SymbolTable;

import java.util.ArrayList;

public class BodyNode implements JottTree {

    private final BodyStatementNode bodyStmNode;
    private final BodyNode bodyNode;
    private final ReturnStatementNode returnStmNode;

    private final Token lastToken;



    public BodyNode(BodyStatementNode bodyStmNode, BodyNode bodyNode, ReturnStatementNode returnStmNode, Token token) {
        this.bodyStmNode = bodyStmNode;
        this.bodyNode = bodyNode;
        this.returnStmNode = returnStmNode;

        this.lastToken = token;
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
                    return new BodyNode(bodyStm, bodyNode, null, tokens.get(0));
                }
                return new BodyNode(bodyStm, null, null, tokens.get(0));
            }
        }
        ReturnStatementNode returnStmNode = ReturnStatementNode.parseReturnStatementNode(tokens);
        if (returnStmNode != null) {
            return new BodyNode(null, null, returnStmNode, tokens.get(0));
        } else {
            return null;
        }

    }

    public String getType(SymbolTable symbolTable) throws Exception {
        if (returnStmNode != null) {
            return this.returnStmNode.getType(symbolTable);
        }
        if (bodyNode != null) {
            return bodyNode.getType(symbolTable);
        }
        return null;
    }

    public boolean hasReturn () {
        if (returnStmNode != null) {
            return true;
        }
        if (bodyNode != null) {
            return bodyNode.hasReturn();
        }
        if (bodyStmNode != null) {
            return bodyStmNode.hasReturn();
        }
        return false;
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
        if (bodyStmNode != null) {
            if (bodyNode != null) {
                return bodyStmNode.convertToJava() + bodyNode.convertToJava();
            }
            return bodyStmNode.convertToJava();
        }
        if (returnStmNode != null) {
            return returnStmNode.convertToJava();
        }
        return "";
    }

    @Override
    public String convertToC() {
        if (bodyStmNode != null) {
            if (bodyNode != null) {
                return bodyStmNode.convertToC() + bodyNode.convertToC();
            }
            return bodyStmNode.convertToC();
        }
        if (returnStmNode != null) {
            return returnStmNode.convertToC();
        }
        return "";
    }

    @Override
    public String convertToPython(int nestLevel) { //Ian
		String nestIndent = "";
		for (int i=0;i<nestLevel;i++) {
			nestIndent = nestIndent + "\t";
		}
		if (this.returnStmNode != null) {
			return nestIndent + this.returnStmNode.convertToPython(nestLevel);
			}
		return nestIndent + this.bodyStmNode.convertToPython(nestLevel)
			+ this.bodyNode.convertToPython(nestLevel);
    }

    @Override
    public boolean validateTree(SymbolTable symbolTable) throws Exception  {
        if (hasReturn() || symbolTable.getCurrentFunctionReturnType().equals("Void")) {
            if (bodyStmNode != null) {
                if (bodyNode != null) {
                    return bodyStmNode.validateTree(symbolTable) && bodyNode.validateTree(symbolTable);
                }
                return bodyStmNode.validateTree(symbolTable);
            }
            if (returnStmNode != null) {
                return returnStmNode.validateTree(symbolTable);
            }
            return true;
        }
        throw new InvalidValidateException("Missing return statement", lastToken.getFilename(), lastToken.getLineNum());
    }
}
