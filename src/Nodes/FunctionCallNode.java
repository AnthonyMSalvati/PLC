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

    private String printType;
	
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

    }

    @Override
    public String convertToJava() { //Ian
        if (this.id.getName().equals("print")) {
            return "System.out.println" + "("
                    + (this.params!=null?this.params.convertToJava():"") + ")";
        }

		return this.id.convertToJava() + "("
			+ (this.params!=null?this.params.convertToJava():"") + ")";
    }

    @Override
    public String convertToC() { //Ian
        if (this.id.getName().equals("print")) {
            return "print" + printType + "("
                    + (this.params!=null?this.params.convertToC():"") + ")";
        }

		return this.id.convertToC() + "("
			+ (this.params!=null?this.params.convertToC():"") + ")";
    }

    @Override
    public String convertToPython(int nestLevel) { //Ian
        if (this.id.getName().equals("input")) {
            return "inputFunc" + "("
                    + (this.params!=null?this.params.convertToPython(nestLevel):"") + ")";
        }

		return this.id.convertToPython(nestLevel) + "("
			+ (this.params!=null?this.params.convertToPython(nestLevel):"") + ")";
    }

    @Override
    public boolean validateTree(SymbolTable symbolTable) throws Exception {
		// make sure function exists
		if (symbolTable.getFunctionReturnType(id.getName()) == null) {
			throw new InvalidValidateException("Function has not yet been defined", this.lastToken.getFilename(), this.lastToken.getLineNum());
		}

        if (this.id.getName().equals("print")) {
            if (params == null || this.params.getParamLength() != 1) {
                throw new InvalidValidateException("Number of parameters does not match function definition",
                        this.lastToken.getFilename(), this.lastToken.getLineNum());
            }
            printType = this.params.getExpr().getType(symbolTable);
        }

        // Check parameter length matches
        if (symbolTable.getParamLength(id.getName()) > 0) {
            if (this.params != null) {
                params.setFunctionName(id.getName());
                if (symbolTable.getParamLength(id.getName()) == this.params.getParamLength()) {
                    return this.id.validateTree(symbolTable) && this.params.validateTree(symbolTable);
                }
            }
            throw new InvalidValidateException("Number of parameters does not match function definition",
                    this.lastToken.getFilename(), this.lastToken.getLineNum());
        }

        return this.id.validateTree(symbolTable);
    }
}
