package Nodes;

import main.*;

import java.util.ArrayList;

/**
 * @author Ben Froment
 *
 * Node that represents a variable being declared
 */
public class VariableDeclarationNode implements JottTree {

    private final TypeNode typeNode;
    private final IdNode idNode;
    private final EndStatementNode endStatementNode;
    private final Token lastToken;

    // < type > <id > < end_statement >
    public VariableDeclarationNode(TypeNode typeNode, IdNode idNode, EndStatementNode endStatementNode, Token token) {
        this.typeNode = typeNode;
        this.idNode = idNode;
        this.endStatementNode = endStatementNode;
        this.lastToken = token;
    }

    // Function called by its parent node to parse the list of tokens
    public static VariableDeclarationNode parseVariableDeclarationNode(ArrayList<Token> tokens) throws Exception {
        Token token;
        TypeNode typeNode = TypeNode.parseTypeNode(tokens);
        if (typeNode != null) {
            IdNode idNode = IdNode.parseIdNode(tokens);
            if (idNode != null) {
                token = tokens.get(0);
                EndStatementNode endStatementNode = EndStatementNode.parseEndStatementNode(tokens);
                return new VariableDeclarationNode(typeNode, idNode, endStatementNode, token);
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
    public boolean validateTree(SymbolTable symbolTable) throws Exception {
        if (symbolTable.addSymbol(idNode.getName(), typeNode.getType())) {
            throw new InvalidValidateException("Variable is already defined in current scope", this.lastToken.getFilename(), this.lastToken.getLineNum());
        }
        return typeNode.validateTree(symbolTable) && idNode.validateTree() && endStatementNode.validateTree();
    }
}
