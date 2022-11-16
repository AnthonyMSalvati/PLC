package Nodes;

import main.*;

import java.util.ArrayList;

/**
* @author Ian Paap-Gray
*/
public class FunctionCallNode implements JottTree {

	private final IdNode id;
	private final ParameterNode params;
    private final Token lastToken;
	
	public FunctionCallNode(IdNode id, Token token) {
		this.id = id;
		this.params = null;
        this.lastToken = token;
	}

    public FunctionCallNode(IdNode id, ParameterNode params, Token token) {
		this.id = id;
		this.params = params;
        this.lastToken = token;
    }

    public static FunctionCallNode parseFunctionCallNode(ArrayList<Token> tokens) throws Exception {
	    Token token;
        if (tokens.size() > 1) {
            token = tokens.get(1);
            if (token.getTokenType() == TokenType.L_BRACKET) {
                IdNode id = IdNode.parseIdNode(tokens);
                if (id != null) {
                    token = tokens.get(0);
                    if (token.getTokenType() == TokenType.L_BRACKET) {
                        tokens.remove(0);
                        ParameterNode params = ParameterNode.parseParameterNode(tokens);
                        if (params != null) {
                            token = tokens.get(0);
                            if (token.getTokenType() == TokenType.R_BRACKET) {
                                tokens.remove(0);
                                return new FunctionCallNode(id, params, token);
                            }
                            throw new InvalidParseException("Error: expected \"]\"", token.getFilename(),
                                    token.getLineNum());
                        }
                        token = tokens.get(0);
                        if (token.getTokenType() == TokenType.R_BRACKET) {
                            tokens.remove(0);
                            return new FunctionCallNode(id, token);
                        }
                        throw new InvalidParseException("Error: expected \"]\"", token.getFilename(),
                                token.getLineNum());
                    }
                    throw new InvalidParseException("Error: expected \"[\"", token.getFilename(),
                            token.getLineNum());
                }
                return null;
            }
        }
        return null;
    }

    public String getType(SymbolTable symbolTable) throws Exception {
        if (symbolTable.getFunctionReturnType(id.getName()) == null) {
            throw new InvalidValidateException("Function has not yet been defined", this.lastToken.getFilename(), this.lastToken.getLineNum());
        }
        return symbolTable.getFunctionReturnType(id.getName());
    }

    @Override
    public String convertToJott() {
		return this.id.convertToJott() + "[" + (this.params == null ? "" : this.params.convertToJott()) + "]";
		/*
		old version (I don't think it works correctly if params == null)
		if (this.id != null) {
			if (this.params != null) {
				return this.id.convertToJott() + "[" +
                        this.params.convertToJott() + "]";
			}
		}
        return ""; //*/
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
		return this.id.convertToPython(nestLevel) + "("
			+ (this.params==null?"":this.params.convertToPython(nestLevel)) + ")";
    }

    @Override
    public boolean validateTree(SymbolTable symbolTable) throws Exception {
		// make sure function exists
		if (symbolTable.getFunctionReturnType(id.getName()) == null) {
			throw new InvalidValidateException("Function has not yet been defined", this.lastToken.getFilename(), this.lastToken.getLineNum());
		}

        // Check parameter length matches
        if (symbolTable.getParamLength(id.getName()) > 0) {
            if (this.params != null) {
                params.setFunctionName(id.getName());
                if (symbolTable.getParamLength(id.getName()) == this.params.getParamLength()) {
                    return this.id.validateTree(symbolTable) && this.params.validateTree(symbolTable);
                }
            }
            throw new InvalidValidateException("Number of parameters does not match function", this.lastToken.getFilename(), this.lastToken.getLineNum());
        }

        return this.id.validateTree(symbolTable);
    }
}
