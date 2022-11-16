package Nodes;

import main.*;

import java.util.ArrayList;

/**
* @author Ian Paap-Gray
*/
public class ParameterTailNode implements JottTree {

	private final ExpressionNode expr;
	private final ParameterTailNode params_t;
	private final Token lastToken;

	private int paramIndex = 0;
	private String functionName = "";

	// ,<expr><params_t>
	public ParameterTailNode(ExpressionNode expr, ParameterTailNode params_t, Token token) {
		this.expr = expr;
		this.params_t = params_t;
		this.lastToken = token;
	}


	
	public static ParameterTailNode parseParameterTailNode(ArrayList<Token> tokens) throws Exception {
		Token token;
		if (tokens.get(0).getTokenType() == TokenType.COMMA) {
			tokens.remove(0); // following StringLiteralNode for example
			ExpressionNode expr = ExpressionNode.parseExpressionNode(tokens);
			if (expr != null) {
				ParameterTailNode params_t = ParameterTailNode.parseParameterTailNode(tokens);
				if (params_t != null) {
					token = tokens.get(0);
					return new ParameterTailNode(expr, params_t, token);
				}
				throw new InvalidParseException("Error: expected <params_t>", 
				tokens.get(0).getFilename(), tokens.get(0).getLineNum());
			}
			throw new InvalidParseException("Error: expected <expr>", 
			tokens.get(0).getFilename(), tokens.get(0).getLineNum());
		}
		return null;
	}

	public int getParamLength (int length) {
		if (expr != null) {
			if (this.params_t != null) {
				return params_t.getParamLength(length + 1);
			}
			return length + 1;
		}
		return length;
	}

	public void setParamIndex (int paramIndex) {
		this.paramIndex = paramIndex;
	}

	public void setFunctionName (String functionName) {
		this.functionName = functionName;
	}

    @Override
    public String convertToJott() {
		if (this.expr != null) {
			if (this.params_t != null) {
				return this.expr.convertToJott() + this.params_t.convertToJott();
			}
			return this.expr.convertToJott();
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
		if (this.expr != null) {
			if (this.params_t != null) {
				return ", " + this.expr.convertToPython(nestLevel) + this.params_t.convertToPython(nestLevel);
			}
			return ", " + this.expr.convertToPython(nestLevel);
		}
        return "";
    }

    @Override
    public boolean validateTree(SymbolTable symbolTable) throws Exception {
		if (this.expr != null) {
			if (expr.getType(symbolTable).equals(symbolTable.getParamType(functionName, paramIndex))) {
				if (this.params_t != null) {
					params_t.setParamIndex(paramIndex + 1);
					params_t.setFunctionName(functionName);
					return this.expr.validateTree(symbolTable) && this.params_t.validateTree(symbolTable);
				}
				return this.expr.validateTree(symbolTable);
			}
			throw new InvalidValidateException("Parameter type does not match", this.lastToken.getFilename(), this.lastToken.getLineNum());
		}
		return true;
	}
}
