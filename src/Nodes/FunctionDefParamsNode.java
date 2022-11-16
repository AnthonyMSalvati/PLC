package Nodes;

import main.*;

import java.util.ArrayList;

/**
 * @author Ben Froment
 *
 * Node representing the parameters to a function
 */
public class FunctionDefParamsNode implements JottTree {

    private final IdNode idNode;
    private final TypeNode typeNode;
    private final FunctionDefParamsTailNode functionDefParamsTailNode;

    // < id >: < type > < function_def_params_t >
    public FunctionDefParamsNode(IdNode idNode, TypeNode typeNode,
                                 FunctionDefParamsTailNode functionDefParamsTailNode) {
        this.idNode = idNode;
        this.typeNode = typeNode;
        this.functionDefParamsTailNode = functionDefParamsTailNode;
    }

    // Function called by its parent node to parse the list of tokens
    public static FunctionDefParamsNode parseFunctionDefParamsNode(ArrayList<Token> tokens) throws Exception {
        Token token;
        IdNode idNode = IdNode.parseIdNode(tokens);
        if (idNode != null) {
            token = tokens.get(0);
            if (token.getTokenType() == TokenType.COLON) {
                tokens.remove(0);
                TypeNode typeNode = TypeNode.parseTypeNode(tokens);
                if (typeNode != null) {
                    FunctionDefParamsTailNode functionDefParamsTailNode =
                            FunctionDefParamsTailNode.parseFunctionDefParamsTailNode(tokens);
                    if (functionDefParamsTailNode != null) {
                        return new FunctionDefParamsNode(idNode, typeNode, functionDefParamsTailNode);
                    }
                    return new FunctionDefParamsNode(idNode, typeNode, null);
                }
                throw new InvalidParseException("Error: expected <type>", token.getFilename(),
                        token.getLineNum());
            }
            throw new InvalidParseException("Error: expected \":\"", token.getFilename(),
                    token.getLineNum());
        }
        return null;
    }

    @Override
    public String convertToJott() {
        if (idNode != null) {
            if (typeNode != null) {
                if (functionDefParamsTailNode != null) {
                    return idNode.convertToJott() + ":" + typeNode.convertToJott() +
                            functionDefParamsTailNode.convertToJott();
                }
                return idNode.convertToJott() + ":" + typeNode.convertToJott();
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
    public String convertToPython(int nestLevel) { //Ian
		return this.idNode.convertToPython(nestLevel)
			+ (this.functionDefParamsTailNode != null ? ", " + this.functionDefParamsTailNode.convertToPython(nestLevel) : "");
    }

    @Override
    public boolean validateTree(SymbolTable symbolTable) throws Exception {
        symbolTable.addParam(idNode.getName(), typeNode.getType());
        if (functionDefParamsTailNode != null) {
            return functionDefParamsTailNode.validateTree(symbolTable);
        }
        return true;
    }
}
