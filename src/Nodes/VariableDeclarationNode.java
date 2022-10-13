package Nodes;

import main.JottTree;
import main.Token;

import java.util.ArrayList;

public class VariableDeclarationNode implements JottTree {

    private final TypeNode typeNode;
    private final IdNode idNode;
    private final EndStatementNode endStatementNode;

    public VariableDeclarationNode(TypeNode typeNode, IdNode idNode, EndStatementNode endStatementNode) {
        this.typeNode = typeNode;
        this.idNode = idNode;
        this.endStatementNode = endStatementNode;
    }

    public static VariableDeclarationNode parseVariableDeclarationNode(ArrayList<Token> tokens) throws Exception {
        TypeNode typeNode = TypeNode.parseTypeNode(tokens);
        if (typeNode != null) {
            IdNode idNode = IdNode.parseIdNode(tokens);
            if (idNode != null) {
                EndStatementNode endStatementNode = EndStatementNode.parseEndStatementNode(tokens);
                return new VariableDeclarationNode(typeNode, idNode, endStatementNode);
            }
        }
        return null;
    }

    @Override
    public String convertToJott() {
        if (typeNode != null) {
            if (idNode != null) {
                if (endStatementNode != null) {
                    return typeNode.convertToJott() + idNode.convertToJott() +
                            endStatementNode.convertToJott();
                }
            }
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
