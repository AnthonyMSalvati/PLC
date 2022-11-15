package Nodes;

import main.InvalidParseException;
import main.JottTree;
import main.Token;
import main.TokenType;

import java.util.ArrayList;

/**
* @author Ian Paap-Gray
*/
public class FunctionCallNode implements JottTree {

	private final IdNode id;
	private final ParameterNode params;
	
	public FunctionCallNode(IdNode id) {
		this.id = id;
		this.params = null;
	}

    public FunctionCallNode(IdNode id, ParameterNode params) {
		this.id = id;
		this.params = params;
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
                                return new FunctionCallNode(id, params);
                            }
                            throw new InvalidParseException("Error: expected \"]\"", token.getFilename(),
                                    token.getLineNum());
                        }
                        token = tokens.get(0);
                        if (token.getTokenType() == TokenType.R_BRACKET) {
                            tokens.remove(0);
                            return new FunctionCallNode(id);
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
    public String convertToPython() { //Ian
		return this.id.convertToPython()+"("+(this.params==null?"":this.params.convertToPython())+")";
    }

    @Override
    public boolean validateTree(SymbolTable symbolTable) throws Exception {
		// make sure function exists
		// TODO is this right? Is this even meant to be here? I do not know.
		if (symbolTable.getType(id.getName()) == null) {
			throw new InvalidValidateException("Function not found", this.id.getName());
			// TODO check correctness of above line
		}
		
		// unsure how scope handling/changing should be implemented here -Ian
		
		
		if (this.params == null) {
			return this.id.validateTree(symbolTable) && this.params.validateTree(symbolTable);
		}
        return this.id.validateTree(symbolTable);
    }
}
