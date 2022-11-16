package Nodes;

import main.*;

import java.util.ArrayList;

/**
 * @author Ben Froment
 *
 * Node representing a function definition
 */
public class FunctionDefNode implements JottTree {

    private final IdNode idNode;
    private final FunctionDefParamsNode functionDefParamsNode;
    private final FunctionReturnNode functionReturnNode;
    private final BodyNode bodyNode;
    private final Token lastToken;

    // < id >[ < func_def_params > ]: < function_return >{ < body >}
    public FunctionDefNode(IdNode idNode, FunctionDefParamsNode functionDefParamsNode,
                           FunctionReturnNode functionReturnNode, BodyNode bodyNode, Token token) {
        this.idNode = idNode;
        this.functionDefParamsNode = functionDefParamsNode;
        this.functionReturnNode = functionReturnNode;
        this.bodyNode = bodyNode;
        this.lastToken = token;
    }

    // Function called by its parent node to parse the list of tokens
    public static FunctionDefNode parseFunctionDefNode(ArrayList<Token> tokens) throws Exception {
        Token token;
        IdNode idNode = IdNode.parseIdNode(tokens);
        if (idNode != null) {
            token = tokens.get(0);
            if (token.getTokenType() == TokenType.L_BRACKET) {
                tokens.remove(0);
                FunctionDefParamsNode functionDefParamsNode = FunctionDefParamsNode.parseFunctionDefParamsNode(tokens);
                token = tokens.get(0);
                if (token.getTokenType() == TokenType.R_BRACKET) {
                    tokens.remove(0);
                    token = tokens.get(0);
                    if (token.getTokenType() == TokenType.COLON) {
                        tokens.remove(0);
                        FunctionReturnNode functionReturnNode = FunctionReturnNode.parseFunctionReturnNode(tokens);
                        if (functionReturnNode != null) {
                            token = tokens.get(0);
                            if (token.getTokenType() == TokenType.L_BRACE) {
                                tokens.remove(0);
                                BodyNode bodyNode = BodyNode.parseBodyNode(tokens);
                                if (tokens.size() != 0) {
                                    token = tokens.get(0);
                                    if (token.getTokenType() == TokenType.R_BRACE) {
                                        tokens.remove(0);
                                        return new FunctionDefNode(idNode, functionDefParamsNode,
                                                functionReturnNode, bodyNode, token);
                                    }
                                    throw new InvalidParseException("Error: expected \"}\"", token.getFilename(),
                                            token.getLineNum());
                                }
                                return new FunctionDefNode(idNode, functionDefParamsNode,
                                        functionReturnNode, bodyNode, token);
                            }
                            throw new InvalidParseException("Error: expected \"{\"", token.getFilename(),
                                    token.getLineNum());
                        }
                    }
                    throw new InvalidParseException("Error: expected \":\"", token.getFilename(),
                            token.getLineNum());
                }
                throw new InvalidParseException("Error: expected \"]\"", token.getFilename(),
                        token.getLineNum());
            }
            throw new InvalidParseException("Error: expected \"{\"", token.getFilename(),
                    token.getLineNum());
        }
        return null;
    }

    @Override
    public String convertToJott() {
        if (idNode != null) {
            if (functionDefParamsNode != null) {
                if (functionReturnNode != null) {
                    if (bodyNode != null) {
                        return idNode.convertToJott() + "[" + functionDefParamsNode.convertToJott() +
                                "]:" + functionReturnNode.convertToJott() + "{" +
                                bodyNode.convertToJott() + "}";
                    }
                    return idNode.convertToJott() + "[" + functionDefParamsNode.convertToJott() +
                            "]:" + functionReturnNode.convertToJott() + "{}";
                }
            }
            if (functionReturnNode != null) {
                if (bodyNode != null) {
                    return idNode.convertToJott() + "[]:" + functionReturnNode.convertToJott() + "{" +
                            bodyNode.convertToJott() + "}";
                }
                return idNode.convertToJott() + "[]:" + functionReturnNode.convertToJott() + "{}";
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
		String nestIndent = "";
		for (int i=0;i<nestLevel;i++) {
			nestIndent = nestIndent + "\t";
		}
		return nestIndent + "def " + this.idNode.convertToPython(nestLevel) + "("
			+ (this.functionDefParamsNode!=null?this.functionDefParamsNode.convertToPython(nestLevel):"") + "):"
			+ (this.bodyNode!=null?"\n"+this.bodyNode.convertToPython(nestLevel+1):" return False") + "\n";
    }

    @Override
    public boolean validateTree(SymbolTable symbolTable) throws Exception {

        // Add new scope
        if (!symbolTable.addScope(idNode.getName())) {
            throw new InvalidValidateException("Function is already defined in current scope", this.lastToken.getFilename(), this.lastToken.getLineNum());
        }

        // Add to function symbol table
        if (!symbolTable.addFunction(idNode.getName(), functionReturnNode.getType())) {
            throw new InvalidValidateException("Function is already defined in current scope", this.lastToken.getFilename(), this.lastToken.getLineNum());
        }

        // Change the scope to the newly created scope
        symbolTable.changeScope(idNode.getName());

        if (functionDefParamsNode != null) {
            if (bodyNode != null) {
                return idNode.validateTree(symbolTable) && functionDefParamsNode.validateTree(symbolTable) &&
                        functionReturnNode.validateTree(symbolTable) && bodyNode.validateTree(symbolTable);
            }
            return idNode.validateTree(symbolTable) && functionDefParamsNode.validateTree(symbolTable) &&
                    functionReturnNode.validateTree(symbolTable);
        }
        if (bodyNode != null) {
            return idNode.validateTree(symbolTable) && functionReturnNode.validateTree(symbolTable) &&
                    bodyNode.validateTree(symbolTable);
        }
        return idNode.validateTree(symbolTable) && functionReturnNode.validateTree(symbolTable);

    }
}
